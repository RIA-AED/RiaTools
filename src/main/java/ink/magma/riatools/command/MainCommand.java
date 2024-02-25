package ink.magma.riatools.command;

import ink.magma.riatools.RiaTools;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.text.MessageFormat;

/**
 * 顶层指令的相关处理和补全
 */
@Command("ria")
@CommandPermission("riatools.admin")
public class MainCommand {
    @Subcommand("egg-message")
    public void eggMessageCmd(CommandSender sender) {
        boolean newValue = !RiaTools.config.getBoolean("dragon-egg.pickup-message");
        RiaTools.config.set("dragon-egg.pickup-message", newValue);
        RiaTools.instance.saveConfig();
        sender.sendMessage(newValue ? "已启用龙蛋拾取消息." : "已关闭龙蛋拾取消息.");
    }

    @Subcommand("setfreeze")
    public void setFreezeCmd(CommandSender sender, @Named("目标玩家") Player targetPlayer, @Named("冷冻 Tick(s)") int ticks) {
        targetPlayer.setFreezeTicks(ticks);
        sender.sendMessage(MessageFormat.format(
                "已设置 {0} setFreezeTicks({1})", targetPlayer.getName(), ticks
        ));
    }
}
