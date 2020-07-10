package dev.scalebound.shared.servers.types;

import dev.scalebound.shared.profiles.ServerProfile;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class ProxyServer extends ServerBase
{
    private int maxPlayerCount;
    private int playerCount;
    private int port;


    private ServerProfile serverProfile;

    @Override
    public void load(ResultSet resultSet) throws SQLException
    {
        super.load(resultSet);

        this.playerCount = resultSet.getInt("server_player_count");
        this.maxPlayerCount = resultSet.getInt("max_player_count");
        this.port = resultSet.getInt("server_port");
    }
}
