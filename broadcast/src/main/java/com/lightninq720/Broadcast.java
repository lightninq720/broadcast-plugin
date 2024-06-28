package com.lightninq720;

import org.bukkit.plugin.java.JavaPlugin;
import com.lightninq720.commands.*;
import com.lightninq720.managers.*;
import lombok.Getter;

public class Broadcast extends JavaPlugin{

    @Getter
    private static Broadcast instance;
    @Getter
    private ConfigManager configManager;

    public Broadcast(){
        instance = this;
    }

    @Override
    public void onEnable(){
        saveDefaultConfig();
        getCommand("broadcast").setExecutor(new broadcastCommand(this));
        getCommand("countdown").setExecutor(new countdownCommand(this));

        configManager = new ConfigManager(this);
        getLogger().info("Broadcast has been enabled!");
    }

    @Override
    public void onDisable(){
        getLogger().info("Broadcast has been disabled!");
    }
}
