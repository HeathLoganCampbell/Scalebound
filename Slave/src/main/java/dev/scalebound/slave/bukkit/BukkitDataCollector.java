package dev.scalebound.slave.bukkit;

import dev.scalebound.slave.common.DataCollector;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class BukkitDataCollector extends DataCollector
{
    public static final int MB = 1024 * 1024;

    @Override
    public int getTPS() {
        return 0;
    }


    @Override
    public int getTPS1MIN() {
        return 0;
    }

    @Override
    public int getTPS5MIN() {
        return 0;
    }

    @Override
    public int getTPS15MIN() {
        return 0;
    }

    @Override
    public int getPlayerCount()
    {
        return Bukkit.getOnlinePlayers().size();
    }

    @Override
    public int getLoadedChunksCount() {
        int loadedChunksCount = 0;
        for (World world : Bukkit.getWorlds()) {
            loadedChunksCount += world.getLoadedChunks().length;
        }
        return loadedChunksCount;
    }

    @Override
    public int getEntitiesCount()
    {
        int entitiesCount = 0;
        for (World world : Bukkit.getWorlds()) {
            entitiesCount += world.getEntities().size();
        }
        return entitiesCount;
    }

    @Override
    public int getUsageRamMB() {
        Runtime instance = Runtime.getRuntime();
        return (int) ((instance.totalMemory() - instance.freeMemory()) / MB);
    }
}
