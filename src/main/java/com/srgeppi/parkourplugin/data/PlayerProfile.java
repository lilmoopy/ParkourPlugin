package com.srgeppi.parkourplugin.data;

import java.util.HashMap;
import java.util.Map;

public class PlayerProfile {
    private Map<String, Long> completedParkours; // Parkour name and completion time

    public PlayerProfile() {
        this.completedParkours = new HashMap<>();
    }

    public void addCompletedParkour(String parkourName, long completionTime) {
        completedParkours.put(parkourName, completionTime);
    }

    public Map<String, Long> getCompletedParkours() {
        return completedParkours;
    }

    public Long getCompletionTime(String parkourName) {
        return completedParkours.get(parkourName);
    }

    // Other relevant methods
}
