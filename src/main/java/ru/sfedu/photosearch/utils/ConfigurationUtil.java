package ru.sfedu.photosearch.utils;

import ru.sfedu.photosearch.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationUtil {

    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/environment.properties";
    private static final Properties configuration = new Properties();

    private ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if (configuration.isEmpty()) {
            loadConfiguration();
        }
        return configuration;
    }

    private static void loadConfiguration() throws IOException {
        File newFile;
        if (System.getProperty(Constants.CONFIG_PATH) != null) {
            newFile = new File(System.getProperty(Constants.CONFIG_PATH));
        } else {
            newFile = new File(DEFAULT_CONFIG_PATH);
        }
        // DEFAULT_CONFIG_PATH.getClass().getResourceAsStream(DEFAULT_CONFIG_PATH);
        try (InputStream in = new FileInputStream(newFile)) {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    public static String getConfigurationEntry(String key) throws IOException {
        return getConfiguration().getProperty(key);
    }
}