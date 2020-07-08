package dev.sprock.scalebound.shared.servers.types;

import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
public class ServerBase
{
    private int serverId;
    private String address;
    private String serverName;
    private int usedRamMB = 0;
    private int maxRamMB = 0;
    private long creationTS = 0;//Timestamp server was created
    private long lastUpdatedTS = 0;//last timestamp server was updated

    public void load(ResultSet resultSet) throws SQLException
    {
        this.serverId = resultSet.getInt("server_id");
        this.address = resultSet.getString("server_address");
        this.serverName = resultSet.getString("server_name");
        this.usedRamMB = resultSet.getInt("used_ram_mb");
        this.maxRamMB = resultSet.getInt("max_ram_mb");
        this.creationTS = resultSet.getLong("creation_ts");
        this.lastUpdatedTS = resultSet.getLong("updated_ts");
    }
}
