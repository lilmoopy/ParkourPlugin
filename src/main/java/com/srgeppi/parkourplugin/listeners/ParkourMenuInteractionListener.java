package com.srgeppi.parkourplugin.listeners;

import com.srgeppi.parkourplugin.data.ParkourMenu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ParkourMenuInteractionListener implements Listener {

    private ParkourMenu parkourMenu;

    public ParkourMenuInteractionListener(ParkourMenu parkourMenu) {
        this.parkourMenu = parkourMenu;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.COMPASS) {
            parkourMenu.openParkourMenu(event.getPlayer());
        }
    }
}
