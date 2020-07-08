package dev.sprock.scalebound.shared.database;

// 3 tables, DedicatedServers, MinecraftServers, ProxyServers
public class ServerDatabase
{
    public static final String DEDI_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `DedicatedServers` ( `server_id` INT NOT NULL AUTO_INCREMENT , `server_address` VARCHAR(24) NULL , `server_name` VARCHAR(16) NOT NULL , `max_ram_mb` INT NOT NULL , `used_ram_mb` INT NULL , `creation_ts` BIGINT NOT NULL , `updated_ts` BIGINT NULL , PRIMARY KEY (`server_id`)) ENGINE = MyISAM;";
    public static final String MINECRAFT_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `MinecraftServers` ( `server_id` INT NOT NULL AUTO_INCREMENT , `server_address` VARCHAR(24) NULL , `server_name` VARCHAR(16) NOT NULL , `max_ram_mb` INT NOT NULL , `used_ram_mb` INT NULL , `creation_ts` BIGINT NOT NULL , `updated_ts` BIGINT NULL , `server_port` INT NULL , `server_tps` INT NULL , `server_tps_1min` INT NULL , `server_tps_5min` INT NULL , `server_avg_ping` INT NULL , `server_player_count` INT NULL , `server_loaded_entities` INT NULL , `server_max_player_count` INT NOT NULL, `server_loaded_chunks` INT NULL , `server_errors` INT NULL , `profile_id` INT NULL, `seen` BOOLEAN NOT NULL, PRIMARY KEY (`server_id`)) ENGINE = MyISAM;";
    public static final String PROXY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `ProxyServer` ( `server_id` INT NOT NULL AUTO_INCREMENT , `server_address` VARCHAR(24) NULL , `server_name` VARCHAR(16) NOT NULL , `max_ram_mb` INT NOT NULL , `used_ram_mb` INT NULL , `creation_ts` BIGINT NOT NULL , `updated_ts` BIGINT NULL , `server_port` INT NULL, `max_player_count` INT NOT NULL, `server_player_count` INT NULL , `profile_id` INT NULL , PRIMARY KEY (`server_id`)) ENGINE = MyISAM;";

    public static final String DEDI_FETCH_ALL = "SELECT * FROM `DedicatedServers`";


    public static final String DEDI_INSERT = "INSERT INTO `dedicatedservers` (`server_id`, `server_address`, `server_name`, `max_ram_mb`, `used_ram_mb`, `creation_ts`, `updated_ts`) VALUES (NULL, ?, ?, ?, NULL, ?, NULL);";


    public static final String DEDI_DELETE_SERVER_ID = "DELETE FROM `dedicatedservers` WHERE `dedicatedservers`.`server_id` = ?";
    public static final String DEDI_DELETE_SERVER_NAME = "DELETE FROM `dedicatedservers` WHERE `dedicatedservers`.`server_name` = ?";
}
