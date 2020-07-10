package dev.scalebound.shared.servers.managers;

import dev.scalebound.shared.servers.types.ProxyServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProxyServerManager
{
    private HashMap<String, ProxyServer> serversByNames;

    public void registerServer(ProxyServer proxyServer)
    {
        this.serversByNames.put(proxyServer.getServerName(), proxyServer);
    }

    public void unregisterServer(ProxyServer proxyServer)
    {
        this.serversByNames.remove(proxyServer.getServerName());
    }

    public ProxyServer getServerByName(String name)
    {
        return this.serversByNames.get(name);
    }

    public void clearServers()
    {
        this.serversByNames.clear();
    }

    public Set<Map.Entry<String, ProxyServer>> getServers()
    {
        return this.serversByNames.entrySet();
    }
}
