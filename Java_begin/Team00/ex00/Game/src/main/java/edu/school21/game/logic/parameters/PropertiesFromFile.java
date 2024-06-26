package edu.school21.game.logic.parameters;

import java.io.InputStream;
import java.util.Properties;

import edu.school21.game.exceptions.NoPropertiesFileException;

public class PropertiesFromFile {
    String resource = null;

    public PropertiesFromFile(String mode) {
        if (mode.equals("production")) {
            this.resource = "/application-production.properties";
        } else if (mode.equals("development")) {
            this.resource = "/application-dev.properties";
        }
    }

    public Properties read() throws NoPropertiesFileException {
        Properties properties = new Properties();

        try (InputStream is = this.getClass().getResourceAsStream(resource)) {
            properties.load(is);
        } catch (Exception e) {
            throw new NoPropertiesFileException("File not found");
        }

        return properties;
    }

}
