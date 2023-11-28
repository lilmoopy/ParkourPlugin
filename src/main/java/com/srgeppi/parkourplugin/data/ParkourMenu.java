package com.srgeppi.parkourplugin.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ParkourMenu {

    private ParkourManager parkourManager;

    public ParkourMenu(ParkourManager parkourManager) {
        this.parkourManager = parkourManager;
    }

    public void openParkourMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Select a Parkour"); // Size must be a multiple of 9

        for (Parkour parkour : parkourManager.getAllParkours()) {
            ItemStack item = new ItemStack(Material.PAPER, 1); // Choose an appropriate item
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + parkour.getName());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Difficulty: " + parkour.getDifficulty());
            meta.setLore(lore);

            item.setItemMeta(meta);
            inventory.addItem(item);
        }

        player.openInventory(inventory);
    }

    public void openParkourListMenu(Player player, String difficulty) {
        Inventory parkourMenu = Bukkit.createInventory(null, 27, ChatColor.BLUE + "Select a Parkour");

        for (Parkour parkour : parkourManager.getParkoursByDifficulty(difficulty)) {
            ItemStack item = new ItemStack(Material.PAPER); // Choose an appropriate item
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + parkour.getName());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Click to start!");
            meta.setLore(lore);

            item.setItemMeta(meta);
            parkourMenu.addItem(item);
        }

        player.openInventory(parkourMenu);
    }

    public void openDifficultySelectionMenu(Player player) {
        Inventory difficultyMenu = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Select Difficulty");

        // Example difficulties: Easy, Medium, Hard
        ItemStack easy = createMenuItem(Material.GREEN_WOOL, ChatColor.GREEN + "Easy");
        ItemStack medium = createMenuItem(Material.YELLOW_WOOL, ChatColor.YELLOW + "Medium");
        ItemStack hard = createMenuItem(Material.RED_WOOL, ChatColor.RED + "Hard");

        difficultyMenu.setItem(2, easy);
        difficultyMenu.setItem(4, medium);
        difficultyMenu.setItem(6, hard);

        player.openInventory(difficultyMenu);
    }

    private ItemStack createMenuItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // Method to handle inventory clicks will be added here
}
