package com.srgeppi.parkourplugin.listeners;

import com.srgeppi.parkourplugin.data.Parkour;
import com.srgeppi.parkourplugin.data.ParkourManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ParkourCheckpointListener implements Listener {

    private ParkourManager parkourManager;

    public ParkourCheckpointListener(ParkourManager parkourManager) {
        this.parkourManager = parkourManager;
    }

    @EventHandler
    public void onPlayerReachCheckpoint(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!(event.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase("[Checkpoint]")) return;

        String parkourName = ChatColor.stripColor(sign.getLine(2));
        Parkour parkour = parkourManager.getParkour(parkourName);
        if (parkour == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "Parkour not found!");
            return;
        }
        event.getPlayer().sendMessage(ChatColor.YELLOW + "Checkpoint reached!");

        // Checkpoint logic here
        // ...
    }
}

