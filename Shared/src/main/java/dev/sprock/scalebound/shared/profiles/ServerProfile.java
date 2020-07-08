package dev.sprock.scalebound.shared.profiles;

import dev.sprock.scalebound.shared.commons.GsonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerProfile
{
    private int profileId;
    private String name;
    private int bufferServers = 5;//mini number of extra server
    private int maxPlayerCount;
    private int bufferMaxPlayerCount;//We count it as a buffer server, until this number is met
    private int maxRamMB;
    private ProfileContent content;

    public void load(ResultSet resultSet) throws SQLException
    {
        this.profileId = resultSet.getInt("profile_id");
        this.name = resultSet.getString("profile_name");
        this.bufferServers = resultSet.getInt("buffer_server");
        this.maxPlayerCount = resultSet.getInt("max_player_count");
        this.bufferMaxPlayerCount = resultSet.getInt("buffer_max_player_count");
        this.maxRamMB = resultSet.getInt("max_ram_mb");
        String jsonContent = resultSet.getString("content");

        this.content = GsonUtils.getGson().fromJson(jsonContent, ProfileContent.class);
    }
}
