package dev.sprock.scalebound.shared.database;

import lombok.Getter;

@Getter
public class MySQLConfig
{
    public String address = "mariadb";
    public String port = "3306";

    public String database = "scalebound";

    public String username = "scalebound_user";
    public String password = "scalebound_password";
}
