package dev.scalebound.shared.database;

public class ProfileDatabase
{
    public static final String PROFILE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `Profiles` ( `profile_id` INT NOT NULL AUTO_INCREMENT , `profile_name` VARCHAR(16) NOT NULL, `buffer_server` INT NOT NULL, `content` JSON NOT NULL, `max_ram_mb` int(11) NOT NULL, `max_player_count` int(11) NOT NULL, `buffer_max_player_count` int(11) NOT NULL, PRIMARY KEY (`profile_id`)) ENGINE = MyISAM;";
    public static final String PROFILE_FETCH_ALL = "SELECT * FROM `Profiles`";
    public static final String PROFILE_INSERT = "INSERT INTO `Profiles` (`profile_id`, `profile_name`, `buffer_server`, `content`, `max_ram_mb`, `max_player_count`, `buffer_max_player_count`) VALUES (NULL, ?, ?, ?, ?, ?, ?);";
    public static final String PROFILE_DELETE_PROFILE_NAME = "DELETE FROM `Profiles` WHERE `Profiles`.`profile_name` = ?";
    public static final String PROFILE_DELETE_PROFILE_ID = "DELETE FROM `Profiles` WHERE `Profiles`.`profile_id` = ?";

    public static final String PROFILE_UPDATE_CONTENT_PROFILE_ID = "UPDATE `Profiles` SET `content` = ? WHERE `Profiles`.`profile_id` = ?;";
    public static final String PROFILE_UPDATE_CONTENT_PROFILE_NAME = "UPDATE `Profiles` SET `content` = ? WHERE `Profiles`.`profile_name` = ?;";
}
