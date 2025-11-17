package guildcraft.discordbot.environmentVariables;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is designed to take in an .env file and add the contents of the file to a Map.
 * <p>
 * You could take in any file type, it does not have to be an .env file to get this to work.
 * <p>
 * The following code snippet shows the format on how the file needs to be structured inside.
 * <p>
 * <code>private final String REGEX = "\\b(\\w+)\\s?=\\s?([A-Za-z0-9_.-]+)\\b";</code>
 * <p>
 * This file is meant to only be used for variables that might be a bit more sensitive that can be left out on import to github
 *
 * @author Guildcraft
 * @version 1.0
 * @since 2023-12-28
 */
public class ReadEnvironmentFiles {
    
    private Properties props = new Properties();
    private Path envFile = Paths.get(Paths.get("").toAbsolutePath().toString() +  "\\src\\main\\java\\guildcraft\\discordbot\\environmentVariables\\.env");
    
    private static final Logger logger = LoggerFactory.getLogger(ReadEnvironmentFiles.class);
    
    public ReadEnvironmentFiles() {
        try(InputStream inputStream = Files.newInputStream(envFile)) {
            props.load(inputStream);
            logger.info("Environment variables loaded");
        } catch(Exception e) {
            logger.error("ENV File not found", e);
        }
    }
    
    /**
     * 
     * @param envKey String name of the environment variable loaded from file
     * @return The value for the environment value in a String format
     */
    public String getEnvValue(String envKey) {
        return props.getProperty(envKey);
    }
    
}
