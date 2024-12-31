package ru.hypestyle.hsearth;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class EarthquakeManager {

    private final JavaPlugin plugin;
    private final Random random = new Random();
    private BukkitRunnable earthquakeTask;
    private boolean isActive;

    public EarthquakeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startEarthquake() {
        if (isActive) return;

        isActive = true;
        earthquakeTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    shakePlayerCamera(player);
                }
            }
        };
        earthquakeTask.runTaskTimer(plugin, 0L, 1L); // 20 тиков = 1 секунда
    }

    public void stopEarthquake() {
        if (!isActive) return;

        isActive = false;
        if (earthquakeTask != null) {
            earthquakeTask.cancel();
            earthquakeTask = null;

            // Создаем взрыв под каждым игроком после окончания землетрясения
            for (Player player : Bukkit.getOnlinePlayers()) {
                createExplosionUnderPlayer(player);
            }
        }
    }

    private void shakePlayerCamera(Player player) {
        // Получаем текущую локацию игрока
        Location loc = player.getLocation();

        // Генерируем случайное смещение для углов поворота
        float yawOffset = (random.nextFloat() - 0.5f) * 10; // От -5 до 5 градусов
        float pitchOffset = (random.nextFloat() - 0.5f) * 10; // От -5 до 5 градусов

        // Применяем смещение
        loc.setYaw(loc.getYaw() + yawOffset);
        loc.setPitch(loc.getPitch() + pitchOffset);

        // Телепортируем игрока на ту же позицию, но с новым направлением
        player.teleport(loc);
    }

    private void createExplosionUnderPlayer(Player player) {
        Location loc = player.getLocation();
        World world = loc.getWorld();

        if (world != null) {
            // Создаем взрыв с силой 0F, чтобы он не наносил урон и не ломал блоки
            world.createExplosion(loc, 0F, false, false);
        }
    }

    public boolean isActive() {
        return isActive;
    }
}
