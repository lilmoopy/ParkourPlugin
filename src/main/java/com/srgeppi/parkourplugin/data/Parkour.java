package com.srgeppi.parkourplugin.data;

import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;

public class Parkour {
    private String name;
    private String creator;
    private String difficulty;
    private Location startLocation;
    private List<Location> checkpoints;

    public Parkour(String name, String creator, String difficulty) {
        this.name = name;
        this.creator = creator;
        this.difficulty = difficulty;
        this.checkpoints = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public List<Location> getCheckpoints() {
        return checkpoints;
    }

    // Setters
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public void setCheckpoints(List<Location> checkpoints) {
        this.checkpoints = checkpoints;
    }

    // Other methods...
}
