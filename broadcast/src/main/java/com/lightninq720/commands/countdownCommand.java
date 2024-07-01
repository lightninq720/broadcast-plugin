package com.lightninq720.commands;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import com.lightninq720.Broadcast;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class countdownCommand implements CommandExecutor{

    private final Broadcast plugin;

    public countdownCommand(Broadcast plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(sender.isOp()){
            if (args.length < 1){
                sender.sendMessage(plugin.getConfigManager().getMessage("wrongUsage").replace("%usage%", command.getUsage()));
                return true;
            }

            int seconds = Integer.parseInt(args[0]);
            AtomicInteger atomicSeconds = new AtomicInteger(seconds);

            if (seconds < 0){
                sender.sendMessage(plugin.getConfigManager().getMessage("invalidInteger"));
                return true;
            }
            Runnable countdownRunnable = new Runnable(){
                @Override
                public void run(){
                    while (atomicSeconds.get() > 0){
                        Bukkit.broadcastMessage(plugin.getConfigManager().getMessage("broadcastPrefix") + ChatColor.WHITE + atomicSeconds.get() + " " + "seconds");
                        atomicSeconds.decrementAndGet();

                        try{
                            Thread.sleep(1000);
                        }catch(InterruptedException e){
                            System.err.println("Countdown Interrupted");
                            sender.sendMessage(plugin.getConfigManager().getMessage("generalError"));
                        }
                        }
                    }
                };
                Thread countdownThread = new Thread(countdownRunnable);
                countdownThread.start();
        }else{
            sender.sendMessage(plugin.getConfigManager().getMessage("noPermissions"));
        }
        return true;
    }
    
}
