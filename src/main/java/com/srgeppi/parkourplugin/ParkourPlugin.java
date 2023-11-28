package com.srgeppi.parkourplugin;

import com.srgeppi.parkourplugin.commands.ParkourCommandExecutor;
import com.srgeppi.parkourplugin.data.ParkourManager;
import com.srgeppi.parkourplugin.data.ParkourMenu;
import com.srgeppi.parkourplugin.data.ParkourTimerManager;
import com.srgeppi.parkourplugin.data.PlayerDataManager;
import com.srgeppi.parkourplugin.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public class ParkourPlugin extends JavaPlugin {

    private ParkourManager parkourManager;
    private ParkourTimerManager timerManager;
    private PlayerDataManager playerDataManager;
    private ParkourMenu parkourMenu;

    @Override
    public void onEnable() {
        parkourManager = new ParkourManager(this);
        timerManager = new ParkourTimerManager();
        playerDataManager = new PlayerDataManager(this);
        parkourMenu = new ParkourMenu(parkourManager);

        this.getCommand("parkour").setExecutor(new ParkourCommandExecutor(parkourManager));

        getServer().getPluginManager().registerEvents(new ParkourStartListener(parkourManager, timerManager), this);
        getServer().getPluginManager().registerEvents(new ParkourEndListener(parkourManager, timerManager, playerDataManager), this);
        getServer().getPluginManager().registerEvents(new ParkourCheckpointListener(parkourManager), this);
        getServer().getPluginManager().registerEvents(new ParkourMenuListener(parkourManager, parkourMenu), this);
        // Remove the duplicate registration of ParkourMenuListener
        getServer().getPluginManager().registerEvents(new ParkourMenuInteractionListener(parkourMenu), this);

        loadConfigFiles();

        getLogger().info("ParkourPlugin has been enabled!");
    }

    @Override
    public void onDisable() {

        saveConfigFiles();
        getLogger().info("ParkourPlugin has been disabled!");
    }


    private void loadConfigFiles() {
        // Load the main configuration file (config.yml)
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Load the parkour data
        parkourManager.loadParkours();

        // Load player profiles
        playerDataManager.loadPlayerProfiles();
    }

    private void saveConfigFiles() {
        // Save the main configuration file (config.yml)
        saveConfig();

        // Save parkour data
        parkourManager.saveParkours();

        // Save player profiles
        //playerDataManager.savePlayerProfiles();
    }
}
