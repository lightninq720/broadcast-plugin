package com.lightninq720.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import com.lightninq720.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
            sender.sendMessage(plugin.getConfigManager().getMessage("noType")); // The command sender did not provide a message to broadcast
            return true;
        }
        else if (!(args.length > 1)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("noMessage")); // The command sender did not provide a message to broadcast
            return true;
        }
        
        String type = args[0];
        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length)); // Join the message arguments

        if (type.equalsIgnoreCase("text") || type.equalsIgnoreCase("chat") || type.equalsIgnoreCase("all")) {
            Bukkit.broadcastMessage(plugin.getConfigManager().getMessage("broadcastPrefix") + ChatColor.WHITE + message); // Send the message to the server
        } if (type.equalsIgnoreCase("gui") || type.equalsIgnoreCase("all")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(plugin.getConfigManager().getMessage("broadcastPrefix"), message); // Show the message on the screen
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendTitle("", ""); // Clear the title after 3 seconds
                    }
                }.runTaskLater(plugin, 60L); // 60 ticks = 3 seconds
            }
        } else {
            sender.sendMessage(plugin.getConfigManager().getMessage("invalidType")); // The command sender provided an invalid type
        }
        return true;
    }
}
