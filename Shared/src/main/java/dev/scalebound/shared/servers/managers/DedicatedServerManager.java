package dev.scalebound.shared.servers.managers;

import dev.scalebound.shared.servers.types.DedicatedServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DedicatedServerManager
{
    private HashMap<String, DedicatedServer> serversByNames = new HashMap<>();
    private HashMap<String, DedicatedServer> serversByAddress = new HashMap<>();


    public void registerServer(DedicatedServer dedicatedServer)
    {
        this.serversByNames.put(dedicatedServer.getServerName(), dedicatedServer);
        this.serversByAddress.put(dedicatedServer.getAddress(), dedicatedServer);
    }

    public void unregisterServer(DedicatedServer dedicatedServer)
    {
        this.serversByNames.remove(dedicatedServer.getServerName());
        this.serversByAddress.remove(dedicatedServer.getAddress());
    }

    public DedicatedServer getServerByName(String name)
    {
        return this.serversByNames.get(name);
    }

    public DedicatedServer getServerByAddress(String Address)
    {
        return this.serversByAddress.get(serversByAddress);
    }

    public void clearServers()
    {
        this.serversByAddress.clear();
        this.serversByNames.clear();
    }

    public Set<Map.Entry<String, DedicatedServer>> getServers()
    {
        return this.serversByNames.entrySet();
    }


    public boolean isEmpty()
    {
        return this.serversByNames.isEmpty();
    }
}
