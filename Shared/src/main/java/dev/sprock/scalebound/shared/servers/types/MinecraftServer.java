package dev.sprock.scalebound.shared.servers.types;

import dev.sprock.scalebound.shared.profiles.ServerProfile;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
public class MinecraftServer extends ServerBase
{
    private int port;

    private int tps;
    private int tps1min;
    private int tps5min;

    private int avgPing;

    private int playerCount;
    private int loadedEntities;
    private int loadedChunks;
    private int errors;

    private int profileId;
    private boolean seen;

    public boolean isOnline()
    {
        return System.currentTimeMillis() - this.getLastUpdatedTS() < 15_000L;
    }

    public boolean isProxy()
    {
        return this.getServerName().startsWith("PROXY-");
    }

    @Override
    public void load(ResultSet resultSet) throws SQLException
    {
        super.load(resultSet);

        this.port = resultSet.getInt("server_port");
        this.tps = resultSet.getInt("server_tps");
        this.tps1min = resultSet.getInt("server_tps_1min");
        this.tps5min = resultSet.getInt("server_tps_5min");

        this.avgPing = resultSet.getInt("server_avg_ping");

        this.playerCount = resultSet.getInt("server_player_count");
        this.loadedEntities = resultSet.getInt("server_loaded_entities");
        this.loadedChunks = resultSet.getInt("server_loaded_chunks");
        this.errors = resultSet.getInt("server_errors");

        this.profileId = resultSet.getInt("profile_id");
        this.seen = resultSet.getBoolean("seen");
    }
}
