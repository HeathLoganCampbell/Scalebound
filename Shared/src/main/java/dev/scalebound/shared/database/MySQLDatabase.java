package dev.scalebound.shared.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLDatabase
{
    private HikariDataSource dataSource;

    public MySQLDatabase(MySQLConfig mySQLConfig) {
        this(mySQLConfig.address, mySQLConfig.port, mySQLConfig.database, mySQLConfig.username, mySQLConfig.password);
    }


    public MySQLDatabase(String address, String port, String database, String username, String password)
    {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + address + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException
    {
        return this.dataSource.getConnection();
    }

    @SneakyThrows
    public void createTable(String query)
    {
        try(Connection connection = this.getConnection())
        {
            final PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
        }
    }

    public void shutdown()
    {
        this.dataSource.close();
    }
}
