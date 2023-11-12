package ink.magma.riatools.dragonegg;

import ink.magma.riatools.RiaTools;
import org.bukkit.command.CommandSender;

public class DragonEggCommands {
    public void EggMessage(CommandSender sender) {
        boolean newValue = !RiaTools.config.getBoolean("dragon-egg.pickup-message");
        RiaTools.config.set("dragon-egg.pickup-message", newValue);
        RiaTools.instance.saveConfig();
        sender.sendMessage(newValue ? "已启用龙蛋拾取消息." : "已关闭龙蛋拾取消息.");
    }
}
