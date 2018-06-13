package de.huemotion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private final String filePath = System.getProperty("user.dir") + File.separator + "Credentials.cfg";

    private Properties properties;

    public ConfigManager() {
        properties = new Properties();
        if ((new File(filePath)).exists()) {
            try {
                properties.load(new FileInputStream(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            properties.put("user", "");
            properties.put("password", "");
            try {
                properties.store(new FileOutputStream(filePath), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUser() {
        return properties.getProperty("user");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

}