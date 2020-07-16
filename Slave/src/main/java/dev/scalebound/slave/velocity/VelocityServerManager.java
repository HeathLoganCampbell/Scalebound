package dev.scalebound.slave.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import dev.scalebound.shared.servers.types.MinecraftServer;
import dev.scalebound.slave.common.Monitor;

import java.net.InetSocketAddress;
import java.util.List;

public class VelocityServerManager implements Runnable
{
    private final ProxyServer server;
    private final Monitor monitor;

    public VelocityServerManager(ProxyServer server, Monitor monitor)
    {
        this.server = server;
        this.monitor = monitor;
    }

    @Override
    public void run()
    {
        List<MinecraftServer> allMinecraftServers = this.monitor.getMinecraftServerRepository().getAllMinecraftServers();
        for (MinecraftServer minecraftServer : allMinecraftServers)
        {
            if(minecraftServer.isProxy() || minecraftServer.getPort() == 25565) continue;
            if(!this.server.getServer(minecraftServer.getServerName()).isPresent())
            {
                InetSocketAddress address = new InetSocketAddress(minecraftServer.getAddress(), minecraftServer.getPort());
                this.server.registerServer(new ServerInfo(minecraftServer.getServerName(), address));
            }

        }
    }
}
