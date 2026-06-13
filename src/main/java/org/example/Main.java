package org.example;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private boolean isServerPaused = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        
        // Register your commands
        this.getCommand("getop").setExecutor(new GetCommand());
        
        // Register the new exemption command (passing 'this' so it can access the plugin instance)
        this.getCommand("grimexempt").setExecutor(new ExemptCommand(this));
        
        getLogger().info("ServerPause and Dev Tools initialized!");
        evaluateTickFreeze(Bukkit.getOnlinePlayers().size());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        evaluateTickFreeze(Bukkit.getOnlinePlayers().size());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        int headcount = Bukkit.getOnlinePlayers().size() - 1;
        evaluateTickFreeze(headcount);
    }

    private void evaluateTickFreeze(int totalPlayers) {
        Server server = getServer();
        
        if (totalPlayers <= 0) {
            if (!isServerPaused) {
                isServerPaused = true;
                server.dispatchCommand(server.getConsoleSender(), "tick freeze");
                getLogger().info("No players remaining. ServerPause has frozen the game loop.");
            }
        } else {
            if (isServerPaused) {
                isServerPaused = false;
                server.dispatchCommand(server.getConsoleSender(), "tick unfreeze");
                getLogger().info("Player detected! ServerPause has unfrozen the game loop.");
            }
        }
    }
}
