package dev.sprock.scalebound.shared.servers.repositories;

import dev.sprock.scalebound.shared.database.MinecraftServerDatabase;
import dev.sprock.scalebound.shared.database.MySQLDatabase;
import dev.sprock.scalebound.shared.database.ServerDatabase;
import dev.sprock.scalebound.shared.profiles.ServerProfile;
import dev.sprock.scalebound.shared.servers.types.DedicatedServer;
import dev.sprock.scalebound.shared.servers.types.MinecraftServer;
import lombok.SneakyThrows;

import java.util.List;

public class MinecraftServerRepository extends RepositoryBase
{
    public MinecraftServerRepository(MySQLDatabase database)
    {
        super(database);
    }

    @SneakyThrows
    public List<MinecraftServer> getAllMinecraftServers() {
        return this.getTransformerResult(MinecraftServerDatabase.MCSERVER_FETCH_ALL, MinecraftServer.class, (rs) ->  {
            final MinecraftServer minecraftServer = new MinecraftServer();
            minecraftServer.load(rs);
            return minecraftServer;
        });
    }

    public void updateAddresssAndPort(MinecraftServer minecraftServer)
    {
        this.execute(MinecraftServerDatabase.MCSERVER_UPDATE_ADDRESS_PORT, (prepared) -> {
            prepared.setString(1, minecraftServer.getAddress());//address
            prepared.setInt(2, minecraftServer.getPort());//port
            prepared.setInt(3, minecraftServer.getServerId());//server id
        });
    }

    public void updateStatistics(int serverId, int ramMBUsage, int tps, int tps1min, int tps5min, int avgPing, int playerCount, int loadedEntities, int chunkCount)
    {
        this.execute(MinecraftServerDatabase.MCSERVER_STATISTICS, (prepared) -> {
            prepared.setInt(1, ramMBUsage);
            prepared.setInt(2, tps);
            prepared.setInt(3, tps1min);
            prepared.setInt(4, tps5min);
            prepared.setInt(5, avgPing);
            prepared.setInt(6, playerCount);
            prepared.setInt(7, loadedEntities);
            prepared.setInt(8, chunkCount);
            prepared.setLong(9, System.currentTimeMillis());
            prepared.setInt(10, serverId);//server id
        });
    }

    public void addMinecraftServer(String serverName, int maxRamMB, int maxPlayerCount, long currentTimeMillis, ServerProfile profile)
    {
        this.execute(MinecraftServerDatabase.MCSERVER_INSERT, (prepared) -> {
            prepared.setString(1, serverName);//server_name
            prepared.setInt(2, maxRamMB);//max_ram_mb
            prepared.setLong(3, currentTimeMillis);//creation_ts
            prepared.setInt(4, maxPlayerCount);//server_max_player_count
            prepared.setInt(5, profile.getProfileId()); //profile_id
        });
    }

    //(`server_id`, `server_address`, `server_name`, `max_ram_mb`, `used_ram_mb`, `creation_ts`, `updated_ts`, `server_port`, `server_tps`, `server_tps_1min`, `server_tps_5min`, `server_avg_ping`, `server_player_count`, `server_loaded_entities`, `server_max_player_count`, `server_loaded_chunks`, `server_errors`, `profile_id`) VALUES (NULL, NULL, ?, ?, NULL, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, NULL, NULL, ?)
    public MinecraftServer addMinecraftServer(String serverName, long currentTimeMillis, ServerProfile profile)
    {
        final long[] serverId = {0};
        this.executeAndLastKey(MinecraftServerDatabase.MCSERVER_INSERT, (prepared) -> {
            prepared.setString(1, serverName);//server_name
            prepared.setInt(2, profile.getMaxRamMB());//max_ram_mb
            prepared.setLong(3, currentTimeMillis);//creation_ts
            prepared.setInt(4, profile.getMaxPlayerCount());//server_max_player_count
            prepared.setInt(5, profile.getProfileId()); //profile_id
        }, (rs) -> {
            if (rs.next()) {
                serverId[0] = rs.getLong(1);
            }
        });

        final MinecraftServer minecraftServer = new MinecraftServer();
        minecraftServer.setServerId((int) serverId[0]);
        minecraftServer.setServerName(serverName);
        minecraftServer.setCreationTS(System.currentTimeMillis());
        minecraftServer.setProfileId(profile.getProfileId());
        return minecraftServer;
    }

    public void setMinecraftServerSeen(int serverId, boolean seen)
    {
        this.execute(MinecraftServerDatabase.MCSERVER_UPDATE_SEEN, (prepared) -> {
            prepared.setBoolean(1, seen);//server_name
            prepared.setInt(2, serverId);//max_ram_mb
        });
    }

//    public void addMinecraftServer(String serverName, int maxRamMB, int maxPlayerCount, long currentTimeMillis, ServerProfile profile)
//    {
//        this.execute(MinecraftServerDatabase.MCSERVER_INSERT, (prepared) -> {
//            prepared.setString(1, serverName);//server_name
//            prepared.setInt(2, maxRamMB);//max_ram_mb
//            prepared.setLong(3, currentTimeMillis);//creation_ts
//            prepared.setInt(4, maxPlayerCount);//server_max_player_count
//            prepared.setInt(5, profile.getProfileId()); //profile_id
//        });
//    }

    public void removeMinecraftServer(String serverName)
    {
        this.execute(MinecraftServerDatabase.MCSERVER_DELETE_SERVER_NAME, (prepared) -> {
            prepared.setString(1, serverName);
        });
    }

    public void removeMinecraftServerById(int serverId)
    {
        this.execute(MinecraftServerDatabase.MCSERVER_DELETE_SERVER_ID, (prepared) -> {
            prepared.setInt(1, serverId);
        });
    }
}
