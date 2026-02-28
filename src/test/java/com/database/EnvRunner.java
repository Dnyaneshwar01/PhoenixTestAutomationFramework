package com.database;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvRunner {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("PhoenixTestAutomationFramework")
                .filename(".env")
                .load();
        String data = dotenv.get("DB_URL");
        System.out.println(data);
    }
}
