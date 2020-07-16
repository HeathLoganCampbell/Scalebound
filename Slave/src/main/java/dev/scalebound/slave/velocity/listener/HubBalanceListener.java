package dev.scalebound.slave.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.scalebound.slave.velocity.VelocitySlavePlugin;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
public class HubBalanceListener
{
    @NonNull
    private VelocitySlavePlugin plugin;
    private HashSet<UUID> connected = new HashSet<>();

    @Subscribe
    public void onConnect(ServerPreConnectEvent e)
    {
        System.out.println("Connecting........");
        Player player = e.getPlayer();
        if(!connected.contains(player.getUniqueId()))
        {
            System.out.println("Finding server........");
            RegisteredServer bestServer = plugin.getBestServer("TEST");
            System.out.println("Found server........");
            if(bestServer == null)
            {
                e.setResult(ServerPreConnectEvent.ServerResult.denied());
                return;
            }
            System.out.println("Connecting........");
            e.setResult(ServerPreConnectEvent.ServerResult.allowed(bestServer));
            connected.add(player.getUniqueId());
        }
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent e)
    {
        Player player = e.getPlayer();
        connected.remove(player.getUniqueId());
    }
}
