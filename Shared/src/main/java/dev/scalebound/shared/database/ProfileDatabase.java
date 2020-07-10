package dev.scalebound.shared.database;

public class ProfileDatabase
{
    public static final String PROFILE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `profiles` ( `profile_id` INT NOT NULL AUTO_INCREMENT , `profile_name` VARCHAR(16) NOT NULL, `buffer_server` INT NOT NULL, `content` JSON NOT NULL, PRIMARY KEY (`profile_id`)) ENGINE = MyISAM;";
    public static final String PROFILE_FETCH_ALL = "SELECT * FROM `profiles`";
    public static final String PROFILE_INSERT = "INSERT INTO `profiles` (`profile_id`, `profile_name`, `buffer_server`, `content`, `max_ram_mb`, `max_player_count`, `buffer_max_player_count`) VALUES (NULL, ?, ?, ?, ?, ?, ?);";
    public static final String PROFILE_DELETE_PROFILE_NAME = "DELETE FROM `profiles` WHERE `profiles`.`profile_name` = ?";
    public static final String PROFILE_DELETE_PROFILE_ID = "DELETE FROM `profiles` WHERE `profiles`.`profile_id` = ?";

    public static final String PROFILE_UPDATE_CONTENT_PROFILE_ID = "UPDATE `profiles` SET `content` = ? WHERE `profiles`.`profile_id` = ?;";
    public static final String PROFILE_UPDATE_CONTENT_PROFILE_NAME = "UPDATE `profiles` SET `content` = ? WHERE `profiles`.`profile_name` = ?;";
}
