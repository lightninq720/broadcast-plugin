package com.lightninq720.commands;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.Arrays;

import org.bukkit.Bukkit;
import com.lightninq720.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class countdownCommand implements CommandExecutor {

    private final Broadcast plugin;

    public countdownCommand(Broadcast plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(plugin.getConfigManager().getMessage("noPermissions")); // The command sender does not have permission to run the command
            return true;
        }
        if (!(args.length > 0)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("noType").replace("%usage%", command.getUsage())); // The command sender did not provide a message to broadcast
            return true;
        }
        else if (!(args.length > 1)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("wrongUsage").replace("%usage%", command.getUsage())); // The command sender did not provide a time for the countdown
            return true;
        }

        String type = args[0];
        int seconds = Integer.parseInt(args[1]);
        AtomicInteger atomicSeconds = new AtomicInteger(seconds); // Create an atomic integer to store the countdown value so it can be edited in the Thread

        if (seconds < 0) {
            sender.sendMessage(plugin.getConfigManager().getMessage("invalidInteger")); // The user provided an invalid integer
            return true;
        }
        if (!Arrays.asList("text", "chat", "gui", "all").contains(type.toLowerCase())) {
            sender.sendMessage(plugin.getConfigManager().getMessage("noType").replace("%usage%", command.getUsage())); // The command sender provided an invalid type
            return true;
        }
        Runnable countdownRunnable = new Runnable() {
            @Override
            public void run() {
                while (atomicSeconds.get() > 0) {
                    if (type.equalsIgnoreCase("text") || type.equalsIgnoreCase("chat") || type.equalsIgnoreCase("all")){
                        Bukkit.broadcastMessage(plugin.getConfigManager().getMessage("broadcastPrefix") + ChatColor.WHITE
                                + atomicSeconds.get() + " " + "seconds");
                    } if (type.equalsIgnoreCase("gui") || type.equalsIgnoreCase("all")) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle(plugin.getConfigManager().getMessage("broadcastPrefix"), (atomicSeconds.get() + " " + "seconds")); // Show the message on the screen
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.sendTitle("", ""); // Clear the title after 1 second
                                }
                            }.runTaskLater(plugin, 19L); // 20 ticks = 1 second
                        }
                    }
                    atomicSeconds.decrementAndGet(); // Decrement the atomic integer by 1

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println("Countdown Interrupted");
                        sender.sendMessage(plugin.getConfigManager().getMessage("generalError"));
                    }
                }
            }
        };
        Thread countdownThread = new Thread(countdownRunnable); // Create a thread to run the countdown
        countdownThread.start();
        return true;
    }

}
