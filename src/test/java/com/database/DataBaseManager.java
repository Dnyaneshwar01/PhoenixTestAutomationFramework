package com.database;

import com.api.utils.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

    private static final String DB_URL = ConfigManager.getProperty("DB_URL");
    private static final String DB_USERNAME = ConfigManager.getProperty("DB_USER_NAME");
    private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
    private volatile static Connection connection;

    public static void createConnection() {

        if (connection == null) {
            synchronized (DataBaseManager.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(connection);
                }
            }
        }
    }
}