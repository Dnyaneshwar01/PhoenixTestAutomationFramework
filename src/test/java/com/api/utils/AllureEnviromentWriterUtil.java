package com.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AllureEnviromentWriterUtil {

    private static final Logger LOGGER = LogManager.getLogger(AllureEnviromentWriterUtil.class);

    public static void createEnviromentProperties() {

        Path path = Paths.get(System.getProperty("user.dir"), "target", "allure-results");
        String folderPath = path.toString();

        File file = new File(folderPath);
        file.mkdirs();

        Properties prop = new Properties();
        prop.setProperty("Project Name", "PhoenixTestAutomationFramework");
        prop.setProperty("ENV", ConfigManager.env);
        prop.setProperty("Base_URI", ConfigManager.getProperty("BASE_URI"));
        prop.setProperty("Operating System", System.getProperty("os.name"));
        prop.setProperty("Operating System Version", System.getProperty("os.version"));
        prop.setProperty("Java Version", System.getProperty("java.version"));


        FileWriter fw = null;
        try {
            fw = new FileWriter(folderPath + "//environment.properties");
            prop.store(fw, "My Properties File");
            LOGGER.info("Created the enviroment.properties file at {}", folderPath);
        } catch (IOException e) {
            LOGGER.error("Unable to create the environment.properties file", e);
            throw new RuntimeException(e);
        }
    }
}
