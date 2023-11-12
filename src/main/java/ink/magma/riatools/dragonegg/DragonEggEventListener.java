package ink.magma.riatools.dragonegg;

import ink.magma.riatools.RiaTools;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class DragonEggEventListener implements Listener {

    @EventHandler
    public void onEggPickUp(EntityPickupItemEvent event) {
        if (!RiaTools.config.getBoolean("dragon-egg.pickup-message")) return;
        ItemStack pickupItem = event.getItem().getItemStack();
        LivingEntity picker = event.getEntity();
        if (pickupItem.getType() == Material.DRAGON_EGG
                && picker.getType() == EntityType.PLAYER
                && picker.getWorld().getEnvironment() == World.Environment.THE_END) {
            Player player = (Player) picker;
            Bukkit.getServer().broadcastMessage(player.getName() + " 拾起了龙蛋!");
        }
    }

    @EventHandler
    public void onEggFallVoid(EntitySpawnEvent event) {
        if (!RiaTools.config.getBoolean("dragon-egg.fall-into-void-message")) return;
        if (event.getLocation().getWorld().getEnvironment() != World.Environment.THE_END) return;
        if (event.getEntityType() != EntityType.FALLING_BLOCK) return;
        FallingBlock fallingBlock = (FallingBlock) event.getEntity();
        if (fallingBlock.getBlockData().getMaterial() == Material.DRAGON_EGG) {
            Location blockLocation = fallingBlock.getLocation();
            int x = blockLocation.getBlockX();
            int y = blockLocation.getBlockY();
            int z = blockLocation.getBlockZ();
            for (int i = y; i >= 0; i--) {
                Block thisBlock = new Location(event.getEntity().getWorld(), x, i, z).getBlock();
                if (!thisBlock.isEmpty()) {
                    return;
                }
            }
            Bukkit.broadcastMessage("§4[悲报] §f一颗龙蛋落入了虚空");
        }
    }
}

