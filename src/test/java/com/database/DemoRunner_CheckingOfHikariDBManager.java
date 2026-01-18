package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunner_CheckingOfHikariDBManager {

    public static void main(String[] args) throws SQLException {
        Connection connection = DataBaseManager.getConnection();
        System.out.println(connection);
    }
}
