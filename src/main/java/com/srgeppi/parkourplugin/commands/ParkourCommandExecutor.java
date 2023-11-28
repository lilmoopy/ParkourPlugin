package com.srgeppi.parkourplugin.commands;

import com.srgeppi.parkourplugin.data.Parkour;
import com.srgeppi.parkourplugin.data.ParkourManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommandExecutor implements CommandExecutor {

    private ParkourManager parkourManager;

    public ParkourCommandExecutor(ParkourManager parkourManager) {
        this.parkourManager = parkourManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("parkour")) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage: /parkour [create|delete|setcheckpoint|info|tp]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                // Example: /parkour create <name> <creator> <difficulty>
                if (args.length < 4) {
                    sender.sendMessage("Usage: /parkour create <name> <creator> <difficulty>");
                    return true;
                }
                String name = args[1];
                String creator = args[2];
                String difficulty = args[3];
                Parkour newParkour = new Parkour(name, creator, difficulty);
                parkourManager.addParkour(newParkour);
                sender.sendMessage("Parkour created: " + name);
                break;
            case "delete":
                // Example: /parkour delete <name>
                if (args.length < 2) {
                    sender.sendMessage("Usage: /parkour delete <name>");
                    return true;
                }
                String parkourName = args[1];
                parkourManager.removeParkour(parkourName);
                sender.sendMessage("Parkour deleted: " + parkourName);
                break;
            case "setcheckpoint":
                // Example: /parkour setcheckpoint <name> <checkpointNumber>
                // This is a simplified version, you'll need to expand this
                if (args.length < 3) {
                    sender.sendMessage("Usage: /parkour setcheckpoint <name> <checkpointNumber>");
                    return true;
                }
                // Additional logic to set the checkpoint
                sender.sendMessage("Checkpoint set for parkour: " + args[1]);
                break;
            case "info":
                // Example: /parkour info <name>
                if (args.length < 2) {
                    sender.sendMessage("Usage: /parkour info <name>");
                    return true;
                }
                Parkour parkour = parkourManager.getParkour(args[1]);
                if (parkour == null) {
                    sender.sendMessage("Parkour not found: " + args[1]);
                    return true;
                }
                sender.sendMessage("Parkour Info: " + parkour.getName() + ", Difficulty: " + parkour.getDifficulty());
                break;
            case "tp":
                // Example: /parkour tp <name>
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only players can use this command.");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("Usage: /parkour tp <name>");
                    return true;
                }
                Player player = (Player) sender;
                Parkour tpParkour = parkourManager.getParkour(args[1]);
                if (tpParkour == null || tpParkour.getStartLocation() == null) {
                    sender.sendMessage("Parkour or start location not found: " + args[1]);
                    return true;
                }
                player.teleport(tpParkour.getStartLocation());
                sender.sendMessage("Teleported to parkour: " + tpParkour.getName());
                break;
            default:
                sender.sendMessage("Unknown subcommand. Use /parkour [create|delete|setcheckpoint|info|tp]");
                break;
        }

        return true;
    }
}