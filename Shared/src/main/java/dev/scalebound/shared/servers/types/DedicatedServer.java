package dev.scalebound.shared.servers.types;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class DedicatedServer extends ServerBase
{
    public void load(ResultSet rs) throws SQLException
    {
        super.load(rs);
    }
}
