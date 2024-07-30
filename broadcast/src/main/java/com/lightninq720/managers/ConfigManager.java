package com.lightninq720.managers;

import com.lightninq720.Broadcast;

import org.bukkit.ChatColor;

public class ConfigManager {
    private final Broadcast plugin;

    public ConfigManager(Broadcast plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String messagepath){
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagepath)); // Get the message content from the constants file and add the correct colours
    }
}
