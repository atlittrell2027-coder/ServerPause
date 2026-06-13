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

        // Grant the permission and force an immediate update
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.setPermission("grim.exempt", true);
        player.recalculatePermissions();

        player.sendMessage("§aYou are now exempt from Grim Anticheat!");
        player.sendMessage("§eNote: You may need to disconnect and reconnect for Grim to register this.");

        return true;
    }
}
