package ink.magma.riatools.playerset;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerSetCommands {
    public void freezenCommand(CommandSender sender, String[] args) {
        if (args.length != 3 || !NumberUtils.isDigits(args[2])) {
            sender.sendMessage("参数错误");
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        int ticks = Integer.parseInt(args[2]);
        if (player != null) {
            player.setFreezeTicks(ticks);
            sender.sendMessage("设置成功");
        } else {
            sender.sendMessage("找不到此玩家");
        }
    }
}
