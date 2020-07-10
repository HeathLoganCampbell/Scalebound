package dev.scalebound.shared.servers.managers;

import dev.scalebound.shared.servers.types.MinecraftServer;
import dev.scalebound.shared.profiles.ServerProfile;

import java.util.*;

public class MinecraftServerManager
{
    private HashMap<String, MinecraftServer> serversByNames = new HashMap<>();

    private HashMap<String, List<MinecraftServer>> serverByAddress = new HashMap<>();
    private HashMap<String, HashSet<Integer>> usedPorts = new HashMap<>();

    public void registerServer(MinecraftServer minecraftServer)
    {
        this.serversByNames.put(minecraftServer.getServerName(), minecraftServer);

        if(minecraftServer.getAddress() != null)
        {
            propergateAddressData(minecraftServer);
        }


    }

    public void propergateAddressData(MinecraftServer minecraftServer)
    {
        if(minecraftServer.getAddress() != null)
        {
            this.serverByAddress.computeIfAbsent(minecraftServer.getAddress(), (key) -> new ArrayList<>());
            this.serverByAddress.get(minecraftServer.getAddress()).add(minecraftServer);

            final HashSet<Integer> usedPort = this.usedPorts.computeIfAbsent(minecraftServer.getAddress(), (key) -> new HashSet<>());
            usedPort.add(minecraftServer.getPort());
        }
    }

    public void unregisterServer(MinecraftServer minecraftServer)
    {
        this.serversByNames.remove(minecraftServer.getServerName());
        final HashSet<Integer> usedPort = this.usedPorts.get(minecraftServer.getAddress());
        if(usedPort != null) usedPort.remove(minecraftServer.getPort());
    }

    public MinecraftServer getServerByName(String name)
    {
        return this.serversByNames.get(name);
    }

    public void clearServers()
    {
        this.serversByNames.clear();
        this.serverByAddress.clear();
        this.usedPorts.clear();
    }

    public Set<Map.Entry<String, MinecraftServer>> getServers()
    {
        return this.serversByNames.entrySet();
    }

    public void reloadAllAddressServer()
    {
        for (Map.Entry<String, MinecraftServer> server : this.getServers())
        {
            final MinecraftServer minecraftServer = server.getValue();
            if(minecraftServer.getAddress() != null)
            {
                this.serverByAddress.computeIfAbsent(minecraftServer.getAddress(), (key) -> new ArrayList<>());
                this.serverByAddress.get(minecraftServer.getAddress()).add(minecraftServer);
            }
        }
    }

    public List<MinecraftServer> getLazyAllServerByAddress(String address)
    {
        ArrayList<MinecraftServer> minecraftServers = new ArrayList<>();
        for (Map.Entry<String, MinecraftServer> server : this.getServers())
        {
            if (server.getValue().getAddress().equalsIgnoreCase(address))
            {
                minecraftServers.add(server.getValue());
            }
        }
        return minecraftServers;
    }

    public List<MinecraftServer> getAllServerByAddress(String address)
    {
        return this.serverByAddress.get(address);
    }


    public boolean isUsedPort(String address, int port)
    {
        final HashSet<Integer> ports = this.usedPorts.get(address);
        if(ports == null) return false;
        return ports.contains(port);
    }

    public List<MinecraftServer> getAllServerByProfile(ServerProfile profile)
    {
        ArrayList<MinecraftServer> minecraftServers = new ArrayList<>();
        for (Map.Entry<String, MinecraftServer> server : this.getServers())
        {
            if (profile.getProfileId() == server.getValue().getProfileId())
            {
                minecraftServers.add(server.getValue());
            }
        }
        return minecraftServers;
    }

    public String getFreeServerName(ServerProfile profile)
    {
        int id = 1;
        for (Map.Entry<String, MinecraftServer> serverEntry : this.getServers())
        {
            final MinecraftServer server = serverEntry.getValue();
            if (profile.getProfileId() == server.getProfileId())
            {
                final String serverName = server.getServerName();
                final int serverNameId = Integer.parseInt(serverName.replaceAll(profile.getName() + "-", ""));
                if(id <= serverNameId)
                    id = serverNameId + 1;
            }
        }

        return profile.getName() + "-" + id;
    }
}
