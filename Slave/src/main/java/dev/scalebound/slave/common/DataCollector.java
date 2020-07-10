package dev.scalebound.slave.common;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;

public abstract class DataCollector
{
    public abstract int getTPS();
    public abstract int getTPS1MIN();
    public abstract int getTPS5MIN();
    public abstract int getTPS15MIN();
    public abstract int getPlayerCount();
    public abstract int getLoadedChunksCount();
    public abstract int getEntitiesCount();
    public abstract int getUsageRamMB();
}
