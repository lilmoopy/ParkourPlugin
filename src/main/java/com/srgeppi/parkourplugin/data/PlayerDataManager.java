package com.srgeppi.parkourplugin.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    private JavaPlugin plugin;
    private Map<UUID, PlayerProfile> playerProfiles;

    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerProfiles = new HashMap<>();
        loadPlayerProfiles();
    }

    public void savePlayerProfile(UUID playerId) {
        PlayerProfile profile = playerProfiles.get(playerId);
        if (profile == null) {
            return;
        }

        File playerFile = new File(plugin.getDataFolder(), "players" + File.separator + playerId + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        for (Map.Entry<String, Long> entry : profile.getCompletedParkours().entrySet()) {
            config.set("completedParkours." + entry.getKey(), entry.getValue());
        }

        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerProfiles() {
        File playerFolder = new File(plugin.getDataFolder(), "players");
        if (!playerFolder.exists()) {
            return;
        }

        File[] playerFiles = playerFolder.listFiles();
        if (playerFiles == null) {
            return;
        }

        for (File playerFile : playerFiles) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
            UUID playerId = UUID.fromString(playerFile.getName().replace(".yml", ""));
            PlayerProfile profile = new PlayerProfile();

            config.getConfigurationSection("completedParkours").getKeys(false).forEach(parkourName -> {
                long completionTime = config.getLong("completedParkours." + parkourName);
                profile.addCompletedParkour(parkourName, completionTime);
            });

            playerProfiles.put(playerId, profile);
        }
    }

    public PlayerProfile getPlayerProfile(UUID playerId) {
        return playerProfiles.getOrDefault(playerId, new PlayerProfile());
    }

    public void savePlayerProfile(UUID playerId, PlayerProfile profile) {
        playerProfiles.put(playerId, profile);
        savePlayerProfile(playerId);
    }

    // Other methods...
}
