package org.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

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

        // Create the attachment and grant both permissions
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.setPermission("grim.exempt", true);
        attachment.setPermission("paper.antixray.bypass", true); 
        
        // Force Bukkit to recalculate so the server sees the changes instantly
        player.recalculatePermissions();

        player.sendMessage("§aYou are now exempt from Grim Anticheat and Paper Anti-Xray!");
        player.sendMessage("§eNote: You may need to disconnect and reconnect for the server to fully register this.");

        return true;
    }
}
