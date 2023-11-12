package ink.magma.riatools;

import ink.magma.riatools.command.CommandManager;
import ink.magma.riatools.composter.ComposterEventHandler;
import ink.magma.riatools.dragonegg.DragonEggEventListener;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class RiaTools extends JavaPlugin implements Listener {
    public static JavaPlugin instance;
    public static Configuration config;
    public static Permission permission;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // load
        saveDefaultConfig();
        config = getConfig();

        // load Vault
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();

        // Load Listeners
        Listener[] listeners = {
                new ComposterEventHandler(),
                new DragonEggEventListener(),
                this
//                new DropEventListener()
        };
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }

        // Load Commands
        try {
            Bukkit.getPluginCommand("ria").setExecutor(new CommandManager());
//            Bukkit.getPluginCommand("drop").setExecutor(new DropCommand());
        } catch (NullPointerException e) {
            getLogger().warning("指令注册失败！" + e);
        }

        // load success
        getLogger().info("插件启动成功。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("插件已卸载。");
    }

    public void reloadPlugin() {
        reloadConfig();
        getLogger().info("插件已重载");
    }
}
