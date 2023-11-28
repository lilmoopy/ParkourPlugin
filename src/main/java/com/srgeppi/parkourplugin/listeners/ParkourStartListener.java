package com.srgeppi.parkourplugin.listeners;

import com.srgeppi.parkourplugin.data.Parkour;
import com.srgeppi.parkourplugin.data.ParkourManager;
import com.srgeppi.parkourplugin.data.ParkourTimerManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ParkourStartListener implements Listener {

    private ParkourManager parkourManager;
    private ParkourTimerManager timerManager;


    public ParkourStartListener(ParkourManager parkourManager,  ParkourTimerManager timerManager) {
        this.parkourManager = parkourManager;
        this.timerManager = timerManager;
    }

    @EventHandler
    public void onPlayerStartParkour(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(event.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[Parkour]")) return;

        String parkourName = ChatColor.stripColor(sign.getLine(2));
        Parkour parkour = parkourManager.getParkour(parkourName);
        if (parkour == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "Parkour not found!");
            return;
        }

        timerManager.startTimer(event.getPlayer().getUniqueId());
        event.getPlayer().sendMessage(ChatColor.GREEN + "Parkour started!");

        // Start parkour logic here
        // ...
    }
}
