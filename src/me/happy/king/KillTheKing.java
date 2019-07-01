package me.happy.king;

import me.happy.king.command.KillTheKingCommand;
import me.happy.king.listener.KillTheKingListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class KillTheKing extends JavaPlugin {

    private static KillTheKing instance;
    private KillTheKingManager killTheKingManager;

    public void onEnable() {
        instance = this;
        killTheKingManager = new KillTheKingManager();
        getCommand("king").setExecutor(new KillTheKingCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new KillTheKingListener(this), this);

    }

    public void onDisable() {

        instance = null;
    }

    public static KillTheKing getInstance() {
        return instance;
    }

    public KillTheKingManager getKingManager() {
        return killTheKingManager;
    }
}
