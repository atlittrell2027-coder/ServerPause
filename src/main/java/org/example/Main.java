package org.example;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener {

    private boolean isServerPaused = false;
    private BukkitTask freezeTask = null; // Tracks the grace period timer

    @Override
    public void onEnable() {
        // Generates config.yml in the plugin folder if it doesn't exist
        saveDefaultConfig(); 

        getServer().getPluginManager().registerEvents(this, this);
        
        // Register your commands
        this.getCommand("serverpausereload").setExecutor(new GetCommand());
        
        // Register the new exemption command (passing 'this' so it can access the plugin instance)
        this.getCommand("joinserver").setExecutor(new ExemptCommand(this));
        
        getLogger().info("ServerPause initialized!");
        
        // Delay the initial check by one tick so the server can finish loading first.
        // This ensures the "/tick freeze" command is actually processed by the server on startup/restart.
        getServer().getScheduler().runTask(this, () -> {
            evaluateTickFreeze(Bukkit.getOnlinePlayers().size());
        });
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
            // Only start the countdown if the server isn't already paused and a task isn't already running
            if (!isServerPaused && freezeTask == null) {
                
                // Pull the grace period from config (defaults to 5 seconds if not found)
                int gracePeriod = getConfig().getInt("grace-period-seconds", 5);
                
                freezeTask = getServer().getScheduler().runTaskLater(this, () -> {
                    // Double check that the server is STILL empty before freezing
                    if (Bukkit.getOnlinePlayers().size() <= 0) {
                        isServerPaused = true;
                        server.dispatchCommand(server.getConsoleSender(), "tick freeze");
                        getLogger().info("No players remaining. ServerPause has frozen the game loop.");
                    }
                    freezeTask = null; // Clear the task once it finishes
                }, gracePeriod * 20L); // Convert seconds to ticks (20 ticks = 1 second)
            }
        } else {
            // If players are online:
            
            // 1. Cancel the freeze countdown if they joined during the grace period
            if (freezeTask != null) {
                freezeTask.cancel();
                freezeTask = null;
            }
            
            // 2. Unfreeze the server if it is currently asleep
            if (isServerPaused) {
                isServerPaused = false;
                server.dispatchCommand(server.getConsoleSender(), "tick unfreeze");
                getLogger().info("Player detected! ServerPause has unfrozen the game loop.");
            }
        }
    }
}