package ru.hypestyle.hsearth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EarthquakeCommand implements CommandExecutor {

    private final EarthquakeManager earthquakeManager;

    public EarthquakeCommand(EarthquakeManager earthquakeManager) {
        this.earthquakeManager = earthquakeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /earthquake <start|stop>");
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            if (earthquakeManager.isActive()) {
                player.sendMessage("Earthquake is already active!");
            } else {
                earthquakeManager.startEarthquake();
                player.sendMessage("Earthquake started!");
            }
        } else if (args[0].equalsIgnoreCase("stop")) {
            if (!earthquakeManager.isActive()) {
                player.sendMessage("Earthquake is not active!");
            } else {
                earthquakeManager.stopEarthquake();
                player.sendMessage("Earthquake stopped!");
            }
        } else {
            player.sendMessage("Usage: /earthquake <start|stop>");
        }

        return true;
    }
}
