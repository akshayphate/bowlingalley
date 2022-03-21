package com.bowling.alley.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBInit {

    String conn_url, db_url, username, password;
    public DBInit() throws Exception {
        ReadPropertyFile rpf = new ReadPropertyFile();
        Properties prop = rpf.getProperty();
        conn_url = prop.getProperty("conn_url");
        db_url = prop.getProperty("db_url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
    }

    public void createDatabase() throws Exception
    {
        try
        {
            String connectURL = conn_url + "/?user=" + username +"&password=" + password;
            Connection conn = DriverManager.getConnection(connectURL);
            conn.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS BOWLING_ALLEY");
            System.out.println("Created Database...");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void createTables() throws Exception
    {
        try{
            Connection conn = DriverManager.getConnection(db_url, username, password);

            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS BOWLERS " +
                    " (nick_name VARCHAR(255), " +
                    " full_name VARCHAR(255), " +
                    " email VARCHAR(255), " +
                    " PRIMARY KEY ( nick_name ))";

            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS PARTY " +
                    " (party_id INTEGER not NULL, " +
                    " party_name VARCHAR(255), " +
                    " PRIMARY KEY ( party_id ))";

            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS SCORES " +
                    " (nick_name VARCHAR(255), " +
                    " party_id INTEGER not NULL, " +
                    " score INTEGER not NULL, " +
                    " strikes INTEGER not NULL, " +
                    " gutters INTEGER not NULL, " +
                    " PRIMARY KEY ( nick_name,party_id ), " +
                    " FOREIGN KEY (nick_name) REFERENCES BOWLERS(nick_name), "+
                    " FOREIGN KEY (party_id) REFERENCES PARTY(party_id))";

            stmt.executeUpdate(sql);
            System.out.println("Created tables in the database...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
