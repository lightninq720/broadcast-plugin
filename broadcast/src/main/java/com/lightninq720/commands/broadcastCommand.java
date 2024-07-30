package com.lightninq720.commands;

import org.bukkit.Bukkit;
import com.lightninq720.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class broadcastCommand implements CommandExecutor {

    private final Broadcast plugin;

    public broadcastCommand(Broadcast plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(plugin.getConfigManager().getMessage("noPermissions")); // The command sender does not have permissions to run the command
            return true;
        }
        if (!(args.length > 0)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("noMessage")); // The command sender did not provide a message to broadcast
            return true;
        }
        String message = String.join(" ", args); // Since all args after the initial command will be the broadcasted message, create a string to send in the message instead of an array
        Bukkit.broadcastMessage(plugin.getConfigManager().getMessage("broadcastPrefix") + ChatColor.WHITE + message); // Actually send the message to the server
        return true;
    }
}
