package com.database;

public class DemoRunner {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        DataBaseManager.createConnection();
        DataBaseManager.createConnection();
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
    }
}
