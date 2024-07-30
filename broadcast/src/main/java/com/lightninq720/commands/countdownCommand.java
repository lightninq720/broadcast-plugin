package com.lightninq720.commands;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import com.lightninq720.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        if (args.length < 1) {
            sender.sendMessage(plugin.getConfigManager().getMessage("wrongUsage").replace("%usage%", command.getUsage())); // No arguments were passed. Provide the user with the correct command usage
            return true;
        }

        int seconds = Integer.parseInt(args[0]);
        AtomicInteger atomicSeconds = new AtomicInteger(seconds); // Create an atomic integer to store the countdown value so it can be edited in the Thread

        if (seconds < 0) {
            sender.sendMessage(plugin.getConfigManager().getMessage("invalidInteger")); // The user provided an invalid integer
            return true;
        }
        Runnable countdownRunnable = new Runnable() {
            @Override
            public void run() {
                while (atomicSeconds.get() > 0) {
                    Bukkit.broadcastMessage(plugin.getConfigManager().getMessage("broadcastPrefix") + ChatColor.WHITE
                            + atomicSeconds.get() + " " + "seconds");
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
