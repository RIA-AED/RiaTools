package ink.magma.riatools;

import ink.magma.riatools.command.MainCommand;
import ink.magma.riatools.composter.ComposterEventHandler;
import ink.magma.riatools.dragonegg.DragonEggEventListener;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class RiaTools extends JavaPlugin implements Listener {
    public static JavaPlugin instance;
    public static Configuration config;
    public static Permission permission;

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("插件已卸载。");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // load
        saveDefaultConfig();
        config = getConfig();

        // load Vault
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp != null) {
            permission = rsp.getProvider();
        } else {
            getLogger().warning("Vault API 获取失败, 请检查是否安装了 Vault. 插件正在关闭.");
            getServer().getPluginManager().disablePlugin(this);
        }

        // Load Listeners
        Bukkit.getPluginManager().registerEvents(new ComposterEventHandler(), this);
        Bukkit.getPluginManager().registerEvents(new DragonEggEventListener(), this);

        // Load Commands
        BukkitCommandHandler commandHandler = BukkitCommandHandler.create(this);
        commandHandler.register(new MainCommand());
        commandHandler.registerBrigadier();

        // load success
        getLogger().info("插件启动成功。");
    }

    public void reloadPlugin() {
        reloadConfig();
        getLogger().info("插件已重载");
    }
}
