package com.srgeppi.parkourplugin.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ParkourManager {
    private JavaPlugin plugin;
    private Map<String, Parkour> parkours;

    public ParkourManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.parkours = new HashMap<>();
        loadParkours();
    }

    public void saveParkours() {
        File parkourFile = new File(plugin.getDataFolder(), "parkours.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(parkourFile);

        for (Parkour parkour : parkours.values()) {
            String path = "parkours." + parkour.getName() + ".";
            config.set(path + "creator", parkour.getCreator());
            config.set(path + "difficulty", parkour.getDifficulty());
            // Save location data for start point
            if (parkour.getStartLocation() != null) {
                saveLocation(config, path + "startLocation", parkour.getStartLocation());
            }
            // Save checkpoint locations
            List<String> checkpointList = new ArrayList<>();
            int index = 0;
            for (Location checkpoint : parkour.getCheckpoints()) {
                String checkpointPath = path + "checkpoints.checkpoint" + index++;
                saveLocation(config, checkpointPath, checkpoint);
                checkpointList.add(checkpointPath);
            }
            config.set(path + "checkpoints.list", checkpointList);
        }

        try {
            config.save(parkourFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadParkours() {
        File parkourFile = new File(plugin.getDataFolder(), "parkours.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(parkourFile);

        Set<String> parkourKeys = config.getConfigurationSection("parkours").getKeys(false);
        for (String key : parkourKeys) {
            String path = "parkours." + key + ".";
            String creator = config.getString(path + "creator");
            String difficulty = config.getString(path + "difficulty");
            Location startLocation = loadLocation(config, path + "startLocation");

            Parkour parkour = new Parkour(key, creator, difficulty);
            parkour.setStartLocation(startLocation);

            List<Location> checkpoints = new ArrayList<>();
            List<String> checkpointList = config.getStringList(path + "checkpoints.list");
            for (String checkpointPath : checkpointList) {
                Location checkpoint = loadLocation(config, checkpointPath);
                checkpoints.add(checkpoint);
            }
            parkour.setCheckpoints(checkpoints);

            parkours.put(key, parkour);
        }
    }

    private void saveLocation(FileConfiguration config, String path, Location location) {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
    }

    private Location loadLocation(FileConfiguration config, String path) {
        String worldName = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw");
        float pitch = (float) config.getDouble(path + ".pitch");
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    public void addParkour(Parkour parkour) {
        parkours.put(parkour.getName(), parkour);
        saveParkours(); // Assuming you want to save to file immediately
    }

    public Collection<Parkour> getParkoursByDifficulty(String difficulty) {
        return parkours.values().stream()
                .filter(parkour -> parkour.getDifficulty().equalsIgnoreCase(difficulty))
                .collect(Collectors.toList());
    }


    public void removeParkour(String name) {
        if (parkours.containsKey(name)) {
            parkours.remove(name);
            saveParkours(); // Save changes
        }
    }

    public Parkour getParkour(String name) {
        return parkours.get(name);
    }

    public Collection<Parkour> getAllParkours() {
        return parkours.values();
    }


    // Other methods...
}
