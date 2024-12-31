package ru.hypestyle.hsearth;

import org.bukkit.plugin.java.JavaPlugin;

public class EarthquakePlugin extends JavaPlugin {

    private EarthquakeManager earthquakeManager;

    @Override
    public void onEnable() {
        getLogger().info("EarthquakePlugin enabled!");

        // Инициализируем менеджера землетрясений
        earthquakeManager = new EarthquakeManager(this);

        // Регистрируем команду /earthquake
        this.getCommand("earthquake").setExecutor(new EarthquakeCommand(earthquakeManager));
    }

    @Override
    public void onDisable() {
        getLogger().info("EarthquakePlugin disabled!");
    }
}
