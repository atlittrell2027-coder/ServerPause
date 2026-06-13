package org.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExemptCommand implements CommandExecutor {

    private final Main plugin;

    // Constructor to pass the main plugin instance
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

        // Grant the grim anticheat exemption permission
        player.addAttachment(plugin, "grim.exempt", true);
        player.sendMessage("§aYou are now exempt from Grim Anticheat!");

        return true;
    }
}
