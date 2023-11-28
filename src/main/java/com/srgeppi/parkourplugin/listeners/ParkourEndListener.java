package com.srgeppi.parkourplugin.listeners;

import com.srgeppi.parkourplugin.data.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ParkourEndListener implements Listener {

    private ParkourManager parkourManager;
    private ParkourTimerManager timerManager;
    private PlayerDataManager playerDataManager;


    public ParkourEndListener(ParkourManager parkourManager, ParkourTimerManager timerManager, PlayerDataManager playerDataManager) {
        this.parkourManager = parkourManager;
        this.timerManager = timerManager;
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerEndParkour(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(event.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[Complete]")) return;

        String parkourName = ChatColor.stripColor(sign.getLine(2));
        Parkour parkour = parkourManager.getParkour(parkourName);
        if (parkour == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "Parkour not found!");
            return;
        }

        long timeTaken = timerManager.stopTimer(event.getPlayer().getUniqueId());
        PlayerProfile profile = playerDataManager.getPlayerProfile(event.getPlayer().getUniqueId());
        profile.addCompletedParkour(parkour.getName(), timeTaken);
        playerDataManager.savePlayerProfile(event.getPlayer().getUniqueId(), profile);

        event.getPlayer().sendMessage(ChatColor.GREEN + "Parkour completed in " + timeTaken + "ms!");

        // Complete parkour logic here
        // ...
    }
}

