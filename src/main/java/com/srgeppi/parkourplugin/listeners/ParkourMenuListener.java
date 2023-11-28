package com.srgeppi.parkourplugin.listeners;

import com.srgeppi.parkourplugin.data.Parkour;
import com.srgeppi.parkourplugin.data.ParkourManager;
import com.srgeppi.parkourplugin.data.ParkourMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class ParkourMenuListener implements Listener {

    private ParkourManager parkourManager;
    private ParkourMenu parkourMenu;


    public ParkourMenuListener(ParkourManager parkourManager, ParkourMenu parkourMenu) {
        this.parkourManager = parkourManager;
        this.parkourMenu = parkourMenu;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.CHEST) return;
        String title = event.getView().getTitle();

        event.setCancelled(true);

        if (title.equals(ChatColor.BLUE + "Select Difficulty")) {
            handleDifficultySelection(event);
        } else if (title.equals(ChatColor.BLUE + "Select a Parkour")) {
            handleParkourSelection(event);
        }
    }
    private void handleDifficultySelection(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        String difficulty = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        openParkourListMenu(player, difficulty);
    }

    private void handleParkourSelection(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        String parkourName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        Parkour selectedParkour = parkourManager.getParkour(parkourName);

        if (selectedParkour != null) {
            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "Starting parkour: " + selectedParkour.getName());
            // Teleport the player to the start of the parkour and start the timer
            player.teleport(selectedParkour.getStartLocation());
            // Additional logic to start the parkour, such as initializing timers, etc.
        }
    }

    private void openParkourListMenu(Player player, String difficulty) {
        // Logic to open a menu listing parkours of the selected difficulty
        // This method should be similar to 'openParkourMenu' but filtered by difficulty
    }
}
