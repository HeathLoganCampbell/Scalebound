package dev.scalebound.slave.velocity.managers;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import dev.scalebound.shared.profiles.ServerProfile;
import dev.scalebound.shared.servers.repositories.ProfileRepository;
import dev.scalebound.shared.servers.types.MinecraftServer;
import dev.scalebound.slave.common.Monitor;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VelocityServerManager implements Runnable
{
    private final ProxyServer server;
    private final Monitor monitor;
    private ProfileRepository profileRepository;

    private int tickProfile = 0;
    private HashMap<String, Integer> profileToId = new HashMap<>();
    private HashMap<Integer, List<MinecraftServer>> profiledServers = new HashMap<>();

    public VelocityServerManager(ProxyServer server, Monitor monitor)
    {
        this.server = server;
        this.monitor = monitor;
        this.profileRepository = new ProfileRepository(this.monitor.getMySQLDatabase());
    }

    public List<MinecraftServer> getProfiledServers(String profile)
    {
        Integer profileId = profileToId.get(profile);
        if(profileId == null) {
            System.out.println("profile null");
            return null;
        }
        System.out.println("profile Id = " + profileId);
        return this.profiledServers.get(profileId.intValue());
    }

    public List<MinecraftServer> getProfiledServers(int profileId)
    {
        return this.profiledServers.get(profileId);
    }

    @Override
    public void run()
    {
        if(tickProfile++ == 20 || this.profiledServers.isEmpty())
        {
            this.profiledServers.clear();
            this.tickProfile = 0;
            List<ServerProfile> allProfiles = this.profileRepository.getAllProfiles();
            for (ServerProfile profile : allProfiles)
                this.profileToId.put(profile.getName(), profile.getProfileId());
        }

        profiledServers.clear();
        List<MinecraftServer> allMinecraftServers = this.monitor.getMinecraftServerRepository().getAllMinecraftServers();
        for (MinecraftServer minecraftServer : allMinecraftServers)
        {
            if(minecraftServer.isProxy() || minecraftServer.getPort() == 25565) continue;
            profiledServers.computeIfAbsent(minecraftServer.getProfileId(), (key) -> new ArrayList<>());
            profiledServers.get(minecraftServer.getProfileId()).add(minecraftServer);
            if(!this.server.getServer(minecraftServer.getServerName()).isPresent())
            {
                InetSocketAddress address = new InetSocketAddress(minecraftServer.getAddress(), minecraftServer.getPort());
                this.server.registerServer(new ServerInfo(minecraftServer.getServerName(), address));
            }

        }
    }
}
