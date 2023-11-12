package ink.magma.riatools.composter;

import ink.magma.riatools.RiaTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ComposterEventHandler implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!RiaTools.config.getBoolean("composter.custom")) return;
        if (event.getClickedBlock() == null || event.getItem() == null) return;
        if (event.getHand() != EquipmentSlot.HAND) { // 禁止副手操作
            return;
        }
        Player player = event.getPlayer();
        PlayerInventory playerInv = player.getInventory();
        ItemStack handItem = event.getItem();
        Block clicked = event.getClickedBlock();
        World world = clicked.getWorld();

        // 如果玩家主手对着堆肥桶使用腐肉或蜘蛛眼
        if (clicked.getType() == Material.COMPOSTER
                && event.getAction() == Action.RIGHT_CLICK_BLOCK
                && handItem != null) {
            if (handItem.getType() == Material.ROTTEN_FLESH || handItem.getType() == Material.SPIDER_EYE) {
                Levelled clickedBlockLevel = (Levelled) clicked.getBlockData(); // 堆肥桶有多少层
                int newClickBlockLevel = clickedBlockLevel.getLevel() + 1; // 下一层
                int maxLevel = clickedBlockLevel.getMaximumLevel(); // 最大层
                if (newClickBlockLevel < maxLevel && Math.random() <= 0.65) { // 如果桶没满且运气好
                    clickedBlockLevel.setLevel(newClickBlockLevel);
                    clicked.setBlockData(clickedBlockLevel);
                    world.playSound(clicked.getLocation(), Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1, 1);
                    if (player.getGameMode() != GameMode.CREATIVE) { // 如果玩家不是创造模式
                        handItem.setAmount(handItem.getAmount() - 1); // 拿走玩家手里的东西
                    }
                } else {
                    world.playSound(clicked.getLocation(), Sound.BLOCK_COMPOSTER_FILL, 1, 1);
                }
                world.spawnParticle(Particle.COMPOSTER, clicked.getLocation().add(0.5, 1, 0.5), 10, 0.2, 0.2, 0.2);
            }
        }
    }
}
