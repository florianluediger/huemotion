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
            properties.put("SpeechToTextUser", "");
            properties.put("SpeechToTextPassword", "");
            properties.put("ToneAnalyzerUser", "");
            properties.put("ToneAnalyzerPassword", "");
            try {
                properties.store(new FileOutputStream(filePath), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSpeechToTextUser() {
        return properties.getProperty("SpeechToTextUser");
    }

    public String getSpeechToTextPassword() {
        return properties.getProperty("SpeechToTextPassword");
    }

    public String getToneAnalyzerUser() {
        return properties.getProperty("ToneAnalyzerUser");
    }

    public String getToneAnalyzerPassword() {
        return properties.getProperty("ToneAnalyzerPassword");
    }

}