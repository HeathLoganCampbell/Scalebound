package dev.scalebound.slave.common;

import dev.scalebound.shared.commons.FileUtils;
import dev.scalebound.shared.database.MySQLConfig;
import dev.scalebound.shared.database.MySQLDatabase;
import dev.scalebound.shared.servers.repositories.MinecraftServerRepository;
import lombok.Getter;

import java.io.File;

public class Monitor implements Runnable
{

    private File databaseConfigFile = new File("./config/database.json");
    private File serverConfigFile = new File("./config/server.json");
    @Getter
    private MySQLDatabase mySQLDatabase;

    private ServerConfig serverConfig;


    private DataCollector dataCollector;
    @Getter
    private final MinecraftServerRepository minecraftServerRepository;

    public Monitor(DataCollector dataCollector)
    {
        this.dataCollector = dataCollector;

        if(!databaseConfigFile.exists())
        {
            while (!databaseConfigFile.getParentFile().exists())
                databaseConfigFile.getParentFile().mkdirs();
            FileUtils.toFile(new MySQLConfig(), databaseConfigFile);
        }

        if(!serverConfigFile.exists())
        {
            while (!serverConfigFile.getParentFile().exists())
                serverConfigFile.getParentFile().mkdirs();
            FileUtils.toFile(new ServerConfig(), serverConfigFile);
        }

        final MySQLConfig mySQLConfig = FileUtils.toObject(databaseConfigFile, MySQLConfig.class);
        this.serverConfig = FileUtils.toObject(serverConfigFile, ServerConfig.class);
        this.mySQLDatabase = new MySQLDatabase(mySQLConfig);

        this.minecraftServerRepository = new MinecraftServerRepository(this.mySQLDatabase);
    }


    @Override
    public void run()
    {
        //int serverId, int ramMBUsage, int tps, int tps1min, int tps5min, int avgPing, int playerCount, int loadedEntities, int chunkCount
        this.minecraftServerRepository.updateStatistics(this.serverConfig.serverId,
                this.dataCollector.getUsageRamMB(),
                this.dataCollector.getTPS(),
                this.dataCollector.getTPS1MIN(),
                this.dataCollector.getTPS5MIN(),
                0,
                this.dataCollector.getPlayerCount(),
                this.dataCollector.getEntitiesCount(),
                this.dataCollector.getLoadedChunksCount());
    }

    public void disable()
    {
        this.mySQLDatabase.shutdown();
    }
}
