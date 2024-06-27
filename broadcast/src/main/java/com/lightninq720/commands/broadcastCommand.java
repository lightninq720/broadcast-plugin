package com.lightninq720.commands;

import org.bukkit.Bukkit;
import com.lightninq720.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class broadcastCommand implements CommandExecutor {

    private final Broadcast plugin;

    public broadcastCommand(Broadcast plugin){
        this.plugin = plugin;
    }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender.isOp()) {
                if (args.length > 0) {
                    String message = String.join(" ", args);
                    Bukkit.broadcastMessage(ChatColor.RED + "[Broadcast] " + ChatColor.WHITE + message);
                } else {
                    sender.sendMessage(ChatColor.RED + "Please provide a message to broadcast.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
            return true;
        }
    }
