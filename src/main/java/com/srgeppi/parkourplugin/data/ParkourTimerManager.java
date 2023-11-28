package com.srgeppi.parkourplugin.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParkourTimerManager {
    private Map<UUID, Long> startTimes;

    public ParkourTimerManager() {
        this.startTimes = new HashMap<>();
    }

    public void startTimer(UUID playerId) {
        startTimes.put(playerId, System.currentTimeMillis());
    }

    public Long stopTimer(UUID playerId) {
        return System.currentTimeMillis() - startTimes.getOrDefault(playerId, System.currentTimeMillis());
    }
}
