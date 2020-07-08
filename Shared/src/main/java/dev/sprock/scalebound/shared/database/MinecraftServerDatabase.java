package dev.sprock.scalebound.shared.database;

public class MinecraftServerDatabase
{
    //PROFILE_FETCH_ALL
    public static final String MCSERVER_FETCH_ALL = "SELECT * FROM `minecraftservers`;";
    public static final String MCSERVER_FETCH = "SELECT * FROM `minecraftservers` WHERE server_id = ?;";
    public static final String MCSERVER_INSERT = "INSERT INTO `minecraftservers` (`server_id`, `server_address`, `server_name`, `max_ram_mb`, `used_ram_mb`, `creation_ts`, `updated_ts`, `server_port`, `server_tps`, `server_tps_1min`, `server_tps_5min`, `server_avg_ping`, `server_player_count`, `server_loaded_entities`, `server_max_player_count`, `server_loaded_chunks`, `server_errors`, `profile_id`, `seen`) VALUES (NULL, NULL, ?, ?, NULL, ?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ?, NULL, NULL, ?, 0);";
    public static final String MCSERVER_DELETE_SERVER_ID = "DELETE FROM `minecraftservers` WHERE `minecraftservers`.`server_id` = ?";
    public static final String MCSERVER_DELETE_SERVER_NAME = "DELETE FROM `minecraftservers` WHERE `minecraftservers`.`server_name` = ?";

    public static final String MCSERVER_UPDATE_ADDRESS_PORT = "UPDATE `minecraftservers` SET `server_address` = ?, server_port = ? WHERE `minecraftservers`.`server_id` = ?;";
    public static final String MCSERVER_UPDATE_SEEN = "UPDATE `minecraftservers` SET `seen` = ? WHERE `minecraftservers`.`server_id` = ?;";
}
