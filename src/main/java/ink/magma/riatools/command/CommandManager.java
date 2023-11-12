package ink.magma.riatools.command;

import ink.magma.riatools.dragonegg.DragonEggCommands;
import ink.magma.riatools.playerset.PlayerSetCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

/**
 * 顶层指令的相关处理和补全
 */
public class CommandManager implements TabExecutor {
    DragonEggCommands dragonEggCommands = new DragonEggCommands();
    PlayerSetCommands playerSetCommands = new PlayerSetCommands();

    @Override
    @ParametersAreNonnullByDefault

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "egg-message" -> dragonEggCommands.EggMessage(sender);
                case "setfreeze" -> playerSetCommands.freezenCommand(sender, args);
                default -> {
                    sender.sendMessage("指令未找到");
                }
            }
        }
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) { // root
            return Arrays.asList("egg-message", "setfreeze", "luckcmd");
        }
        switch (args[0]) { // /ria setplayer <Player> xxx
        }
        return null;
    }
}
