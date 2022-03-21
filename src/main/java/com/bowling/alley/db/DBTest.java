package com.bowling.alley.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Connection con = null;
        try {
            String url = "jdbc:mysql://localhost:3306/mysql";
            String username = "root";
            String password = "0548";
            con = DriverManager.getConnection(url, username, password);
            if (con != null) {
                System.out.println("Successfully connected to MySQL database test");
            }
        }
        catch (SQLException ex) {
            System.out .println("An error occurred while connecting MySQL database");
            ex.printStackTrace();
        }
    }
}


