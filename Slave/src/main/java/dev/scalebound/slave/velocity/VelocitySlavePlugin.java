package dev.scalebound.slave.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "scaleboundslave", name = "Scalebound Slave", version = "1.0-SNAPSHOT",
        description = "Slave plugin which reports updates", authors = {"Sprock", "SprockPls"})
public class VelocitySlavePlugin
{
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public VelocitySlavePlugin(ProxyServer server, Logger logger)
    {
        this.server = server;
        this.logger = logger;
    }
}
