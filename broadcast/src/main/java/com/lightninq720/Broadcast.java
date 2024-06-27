package com.lightninq720;

import org.bukkit.plugin.java.JavaPlugin;
import com.lightninq720.commands.*;
import lombok.Getter;

public class Broadcast extends JavaPlugin{

    @Getter
    private static Broadcast instance;

    public Broadcast(){
        instance = this;
    }

    @Override
    public void onEnable(){
        getCommand("broadcast").setExecutor(new broadcastCommand(this));
        getLogger().info("LaunchPad has been enabled!");
    }

    @Override
    public void onDisable(){
        getLogger().info("LaunchPad has been disabled!");
    }
}
