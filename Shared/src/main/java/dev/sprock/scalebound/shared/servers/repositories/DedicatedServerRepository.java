package dev.sprock.scalebound.shared.servers.repositories;

import dev.sprock.scalebound.shared.database.MySQLDatabase;
import dev.sprock.scalebound.shared.database.ServerDatabase;
import dev.sprock.scalebound.shared.servers.types.DedicatedServer;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.*;

public class DedicatedServerRepository extends RepositoryBase
{
    public DedicatedServerRepository(MySQLDatabase database)
    {
        super(database);
    }

    @SneakyThrows
    public List<DedicatedServer> getAllServers() {
        return this.getTransformerResult(ServerDatabase.DEDI_FETCH_ALL, DedicatedServer.class, (rs) ->  {
            final DedicatedServer dedicatedServer = new DedicatedServer();
            dedicatedServer.load(rs);
            return dedicatedServer;
        });
    }


    public void addDedicatedServer(String dediName, String ipAddress, int maxRamMB, long currentTimeMillis)
    {
        this.execute(ServerDatabase.DEDI_INSERT, (prepared) -> {
            prepared.setString(1, ipAddress);
            prepared.setString(2, dediName);
            prepared.setInt(3, maxRamMB);
            prepared.setLong(4, currentTimeMillis);
        });
    }

    public void removeDedicatedServerByName(String dediName)
    {
        this.execute(ServerDatabase.DEDI_DELETE_SERVER_NAME, (prepared) -> {
            prepared.setString(1, dediName);
        });
    }

    public void removeDedicatedServerById(int serverId)
    {
        this.execute(ServerDatabase.DEDI_DELETE_SERVER_ID, (prepared) -> {
            prepared.setInt(1, serverId);
        });
    }
}
