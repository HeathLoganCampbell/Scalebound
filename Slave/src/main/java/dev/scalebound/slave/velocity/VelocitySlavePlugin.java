package dev.scalebound.slave.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.scalebound.slave.bukkit.BukkitDataCollector;
import dev.scalebound.slave.common.Monitor;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

@Plugin(id = "scaleboundslave", name = "Scalebound Slave", version = "1.0-SNAPSHOT",
        description = "Slave plugin which reports updates", authors = {"Sprock", "SprockPls"})
public class VelocitySlavePlugin
{
    private final ProxyServer server;
    private final Logger logger;
    private Monitor monitor;

    @Inject
    public VelocitySlavePlugin(ProxyServer server, Logger logger)
    {
        this.server = server;
        this.logger = logger;

        this.monitor = new Monitor(new VelocityDataCollector(this.server));
        this.server.getScheduler().buildTask(this, this.monitor).repeat(5, TimeUnit.SECONDS);
        this.server.getScheduler().buildTask(this, new VelocityServerManager(this.server, this.monitor)).repeat(5, TimeUnit.SECONDS);
    }
}
