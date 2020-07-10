package dev.scalebound.shared.servers.repositories;

import dev.scalebound.shared.commons.SQLConsumer;
import dev.scalebound.shared.commons.SQLTransformer;
import dev.scalebound.shared.database.MySQLDatabase;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RepositoryBase
{
    private MySQLDatabase database;

    protected Connection getCon() throws SQLException
    {
        return this.database.getConnection();
    }

    protected void getCon(SQLConsumer<Connection> conConsumer)
    {
        Connection con = null;
        try {
            con = getCon();
            conConsumer.accept(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void executeAndLastKey(String query, SQLConsumer<PreparedStatement> params, SQLConsumer<ResultSet> rsConsumer) {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getCon();
            PreparedStatement prestmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            params.accept(prestmt);
            prestmt.execute();
            rs = prestmt.getGeneratedKeys();
            rsConsumer.accept(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void getResult(String query, SQLConsumer<PreparedStatement> params, SQLConsumer<ResultSet> rsConsumer) {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getCon();
            PreparedStatement prestmt = con.prepareStatement(query);
            params.accept(prestmt);
            rs = prestmt.executeQuery();
            rsConsumer.accept(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void getResult(String query, SQLConsumer<ResultSet> rsConsumer) {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getCon();
            rs = con.prepareStatement(query).executeQuery();
            rsConsumer.accept(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected <T> List<T> getTransformerResult(String query, Class<T> clazz, SQLTransformer<ResultSet, T> rsTransformer) {
        Connection con = null;
        ResultSet rs = null;
        List<T> elements = new ArrayList<>();
        try {

            con = getCon();
            rs = con.prepareStatement(query).executeQuery();
            while(rs.next())
            {
                final T accept = rsTransformer.accept(rs);
                if(accept != null) elements.add(accept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return elements;
    }

    protected void execute(String statement) {
        getCon(con -> con.prepareStatement(statement).execute());
    }

    protected void execute(String statement, SQLConsumer<PreparedStatement> prepared) {
        getCon(con -> {
            PreparedStatement prep = con.prepareStatement(statement);
            prepared.accept(prep);
            prep.execute();
        });
    }

    //Create tables
    protected void onTables()
    {

    }
}
