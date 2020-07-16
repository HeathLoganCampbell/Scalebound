package dev.scalebound.slave.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.scalebound.shared.servers.repositories.ProfileRepository;
import dev.scalebound.shared.servers.types.MinecraftServer;
import dev.scalebound.slave.common.Monitor;
import dev.scalebound.slave.velocity.listener.HubBalanceListener;
import dev.scalebound.slave.velocity.managers.VelocityServerManager;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Plugin(id = "scaleboundslave", name = "Scalebound Slave", version = "1.0-SNAPSHOT",
        description = "Slave plugin which reports updates", authors = {"Sprock", "SprockPls"})
public class VelocitySlavePlugin
{
    public static final Random RANDOM = new Random();

    private final ProxyServer server;
    private final Logger logger;
    private Monitor monitor;
    private VelocityServerManager serverManager;

    @Inject
    public VelocitySlavePlugin(ProxyServer server, Logger logger)
    {
        this.server = server;
        this.logger = logger;

        this.monitor = new Monitor(new VelocityDataCollector(this.server));
        this.serverManager = new VelocityServerManager(this.server, this.monitor);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event)
    {
        this.server.getScheduler().buildTask(this, () -> this.monitor.run()).repeat(5, TimeUnit.SECONDS).schedule();
        this.server.getScheduler().buildTask(this, () -> this.serverManager.run()).repeat(5, TimeUnit.SECONDS).schedule();

        this.server.getEventManager().register(this, new HubBalanceListener(this));
    }

    public RegisteredServer getBestServer(String profileName)
    {
        List<MinecraftServer> servers = this.serverManager.getProfiledServers(profileName);

        MinecraftServer minecraftServer = servers.get(RANDOM.nextInt(servers.size()));
        Optional<RegisteredServer> server = this.server.getServer(minecraftServer.getServerName());

        return server.orElse(null);
    }
}
