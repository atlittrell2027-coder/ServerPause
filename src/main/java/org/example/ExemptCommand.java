package org.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import java.util.List;

public class ExemptCommand implements CommandExecutor {

    private final Main plugin;

    public ExemptCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player player = (Player) sender;

        // 1. Grant the temporary permissions for this session
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.setPermission("grim.exempt", true);
        attachment.setPermission("paper.antixray.bypass", true);
        player.recalculatePermissions();

        // 2. Save the player's name to the config file so the plugin remembers them
        List<String> exemptPlayers = plugin.getConfig().getStringList("exempt-players");
        if (!exemptPlayers.contains(player.getName())) {
            exemptPlayers.add(player.getName());
            plugin.getConfig().set("exempt-players", exemptPlayers);
            plugin.saveConfig(); // Actually saves the file to the disk
        }

        player.sendMessage("Started!");

        return true;
    }
}
