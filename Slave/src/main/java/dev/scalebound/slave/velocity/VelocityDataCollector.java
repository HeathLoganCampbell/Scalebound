package dev.scalebound.slave.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import dev.scalebound.slave.common.DataCollector;

public class VelocityDataCollector extends DataCollector
{
    public static final int MB = 1024 * 1024;
    private ProxyServer server;

    public VelocityDataCollector(ProxyServer server)
    {
        this.server = server;
    }

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
    public int getPlayerCount() {
        return this.server.getPlayerCount();
    }

    @Override
    public int getLoadedChunksCount() {
        return 0;
    }

    @Override
    public int getEntitiesCount() {
        return 0;
    }

    @Override
    public int getUsageRamMB() {
        Runtime instance = Runtime.getRuntime();
        return (int) ((instance.totalMemory() - instance.freeMemory()) / MB);
    }
}
