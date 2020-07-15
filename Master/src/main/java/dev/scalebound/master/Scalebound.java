package dev.scalebound.master;

import dev.scalebound.master.console.ScaleboundConsole;
import dev.scalebound.master.utils.Console;
import dev.scalebound.master.booter.BooterManager;
import dev.scalebound.shared.commons.FileUtils;
import dev.scalebound.shared.database.MySQLConfig;
import dev.scalebound.shared.database.MySQLDatabase;
import dev.scalebound.shared.database.ProfileDatabase;
import dev.scalebound.shared.database.ServerDatabase;
import dev.scalebound.shared.profiles.ServerProfile;
import dev.scalebound.shared.servers.helpers.AddressAssignHelper;
import dev.scalebound.shared.servers.managers.DedicatedServerManager;
import dev.scalebound.shared.servers.managers.MinecraftServerManager;
import dev.scalebound.shared.servers.managers.ProfileManager;
import dev.scalebound.shared.servers.managers.ProxyServerManager;
import dev.scalebound.shared.servers.repositories.DedicatedServerRepository;
import dev.scalebound.shared.servers.repositories.MinecraftServerRepository;
import dev.scalebound.shared.servers.repositories.ProfileRepository;
import dev.scalebound.shared.servers.types.DedicatedServer;
import dev.scalebound.shared.servers.types.MinecraftServer;
import lombok.Getter;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Scalebound implements Runnable
{
    public static final long UPDATE_EVERY_MS = 5000L;

    private int ticks = 0;
    private boolean running = false;
    private long startUpdate = System.currentTimeMillis();
    @Getter
    private BooterManager booterManager;


    @Getter
    private DedicatedServerManager dedicatedServerManager;
    @Getter
    private MinecraftServerManager minecraftServerManager;
    @Getter
    private ProxyServerManager proxyServerManager;
    @Getter
    private ProfileManager profileManager;

    private MySQLDatabase mySQLDatabase;

    @Getter
    private DedicatedServerRepository dedicatedServerRepository;
    @Getter
    private ProfileRepository profileRepository;
    @Getter
    private MinecraftServerRepository minecraftServerRepository;

    private AddressAssignHelper addressAssignHelper;


    private File databaseConfig = new File("./config/database.json");

    private ScaleboundConsole console;

    public Scalebound()
    {
        this.dedicatedServerManager = new DedicatedServerManager();
        this.minecraftServerManager = new MinecraftServerManager();
        this.proxyServerManager = new ProxyServerManager();
        this.profileManager = new ProfileManager();
        this.booterManager = new BooterManager();

        this.addressAssignHelper = new AddressAssignHelper();

        this.run();
    }

    private void init()
    {
        this.running = true;

        if(!databaseConfig.exists())
        {
            Console.log("Config", "Creating database.json...");
            while (!databaseConfig.getParentFile().exists())
                databaseConfig.getParentFile().mkdirs();
            FileUtils.toFile(new MySQLConfig(), databaseConfig);
        }

        final MySQLConfig mySQLConfig = FileUtils.toObject(databaseConfig, MySQLConfig.class);
        this.mySQLDatabase = new MySQLDatabase(mySQLConfig);

        // create mysql tables
        Console.log("MySQL", "Creating database tables...");
        this.mySQLDatabase.createTable(ServerDatabase.DEDI_CREATE_TABLE);
        this.mySQLDatabase.createTable(ServerDatabase.MINECRAFT_CREATE_TABLE);
        this.mySQLDatabase.createTable(ServerDatabase.PROXY_CREATE_TABLE);

        this.mySQLDatabase.createTable(ProfileDatabase.PROFILE_CREATE_TABLE);
        Console.log("MySQL", "Created tables!");

        this.dedicatedServerRepository = new DedicatedServerRepository(this.mySQLDatabase);
        this.profileRepository = new ProfileRepository(this.mySQLDatabase);
        this.minecraftServerRepository = new MinecraftServerRepository(this.mySQLDatabase);

        this.console = new ScaleboundConsole(this);
        new Thread(() -> {
            this.console.start();
        }).start();
    }

    public void reloadDedicatedServers()
    {
        this.dedicatedServerManager.clearServers();
        final List<DedicatedServer> allServers = this.dedicatedServerRepository.getAllServers();
        for (DedicatedServer dediServer : allServers)
        {
            this.dedicatedServerManager.registerServer(dediServer);
        }
    }

    public void reloadProfiles()
    {
        this.profileManager.clearProfiles();
        for (ServerProfile profile : this.profileRepository.getAllProfiles())
        {
            this.profileManager.registerProfile(profile);
        }
    }

    public void reloadMinecraftServers()
    {
        this.minecraftServerManager.clearServers();
        for (MinecraftServer minecraftServer : this.minecraftServerRepository.getAllMinecraftServers())
        {
            this.minecraftServerManager.registerServer(minecraftServer);
        }
    }

    public void shutdown()
    {

    }

    private void update()
    {
        if(ticks % 60 == 0)
        {
            this.reloadProfiles();
        }

        if(ticks % 15 == 0 || this.dedicatedServerManager.isEmpty())
        {
            this.reloadDedicatedServers();
        }

        this.reloadMinecraftServers();


        //Profiles
        //Check buffer servers required
        for (Map.Entry<String, ServerProfile> profileEntry : this.getProfileManager().getProfiles())
        {
            final ServerProfile profile = profileEntry.getValue();

            final int bufferMaxPlayerCount = profile.getBufferMaxPlayerCount();
            final int bufferServers = profile.getBufferServers();

            int activeBufferServers = 0;
            final List<MinecraftServer> serverByProfile = this.minecraftServerManager.getAllServerByProfile(profile);
            for (MinecraftServer minecraftServer : serverByProfile)
            {

                if(minecraftServer.isOnline() &&
                        minecraftServer.getPlayerCount() <= bufferMaxPlayerCount)
                    activeBufferServers++;
            }

            if(activeBufferServers < bufferServers)
            {
                int requiredServers = bufferServers - activeBufferServers;
                Console.log(profile.getName(), "We require " + requiredServers + " more servers of " + profile.getName());

                for (int i = 0; i < requiredServers; i++) {
                    String serverName = this.getMinecraftServerManager().getFreeServerName(profile);
//                    Console.log("Buffer", "Creating " + serverName);
                    final MinecraftServer minecraftServer = this.getMinecraftServerRepository().addMinecraftServer(serverName, System.currentTimeMillis(), profile);
                    this.getMinecraftServerManager().registerServer(minecraftServer);
                }

                Console.log(profile.getName(), "Created " + requiredServers + " servers of " + profile.getName());
            }
        }

        //Assign ips to servers
        for (Map.Entry<String, MinecraftServer> serverEntry : this.getMinecraftServerManager().getServers())
        {
            final MinecraftServer minecraftServer = serverEntry.getValue();
            if(minecraftServer.getAddress() == null || !minecraftServer.isSeen())
            {
//                Console.log("Address Assign", minecraftServer.getAddress() + " " + minecraftServer.getServerName());
//                Console.log("Address Assign", "Assigning address to " + minecraftServer.getServerName());
                long start = System.currentTimeMillis();
                final boolean addressAssigned = this.addressAssignHelper.assignAddress(minecraftServer, dedicatedServerManager, minecraftServerManager);
                if(addressAssigned)
                {
                    this.getMinecraftServerRepository().updateAddresssAndPort(minecraftServer);
                    Console.log("Address Assign", minecraftServer.getServerName() + " now " + minecraftServer.getAddress() + ":" + minecraftServer.getPort() + " in " + (System.currentTimeMillis() - start) + "ms");
                }
                else
                    Console.log("Address Assign", "!! Not enough dedicated servers to assign more servers !! to add " + minecraftServer.getServerName());
            }
        }

        //Start up servers
        for (Map.Entry<String, MinecraftServer> serverEntry : this.getMinecraftServerManager().getServers())
        {
            final MinecraftServer minecraftServer = serverEntry.getValue();
            if(!minecraftServer.isSeen())
            {
                Console.log(minecraftServer.getServerName(), "Starting on " + minecraftServer.getAddress() + "...");
                this.minecraftServerRepository.setMinecraftServerSeen(minecraftServer.getServerId(), true);
                minecraftServer.setSeen(true);
                new Thread(() -> this.booterManager.startServer(minecraftServer, this.profileManager)).start();
            }
        }
    }

    @Override
    public void run()
    {

        this.init();

        while(this.running)
        {
            this.startUpdate = System.currentTimeMillis();

            this.update();
            ticks++;

            try {
                Thread.sleep(UPDATE_EVERY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        shutdown();
    }
}
