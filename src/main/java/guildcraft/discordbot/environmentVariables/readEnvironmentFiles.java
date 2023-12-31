package guildcraft.discordbot.environmentVariables;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

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
public class readEnvironmentFiles {
    
    private Map<String, String> variables;
    private File environmentFile;
    private Scanner readEnvironment;
    private final String REGEX = "\\b(\\w+)\\s?=\\s?([A-Za-z0-9_.-]+)\\b";
    private final Pattern regexPattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
    
    /**
     * Takes a string for the filepath of the environment file you would like to read. Converts the string into a File type and calls the readEnvironmentFiles(File file) constructor
     * 
     * @param filepath String value of the filepath name to the .env file in question
     */
    public readEnvironmentFiles(String filepath) {
        this(new File (filepath));
    }
    
    /**
     * Takes in a file object and adds all of the contents to the Map called variables. The file object should be a list of variables in this format var = value
     * <p>
     * The Map will take the String values of the variable names with the String values
     * 
     * @param file The File object of the .env file
     */
    public readEnvironmentFiles(File file) {
        try {
            this.environmentFile = file;
            this.readEnvironment = new Scanner(this.environmentFile);
            this.variables = new HashMap<>();
            
            while(this.readEnvironment.hasNextLine()) {
                String data = this.readEnvironment.nextLine();
                Matcher patternMatcher = this.regexPattern.matcher(data);
                while(patternMatcher.find()) {
                    this.variables.put(patternMatcher.group(1), patternMatcher.group(2));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(readEnvironmentFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * See the return.
     * 
     * @param VariableName String value of the variable you are trying to retrieve from the .env file
     * @return The String of the value of an Object in the Map variables
     */
    public String variableValue(String VariableName) {
        return this.variables.get(VariableName);
    }
    
    /**
     * Appending the .env document and adding a new line with variables that you specify later on
     * 
     * @param variable The variable being added on a new line in the .env file
     * @param value The Value of the Variable we re adding to the .env file
     * @throws IOException This is to be caught when this is called
     */
    public void addVariablesToEnvFile(String variable, String value) throws IOException {
        String appendDocument = "/n" + variable + " = " + value;
        
        try (FileWriter writeToFile = new FileWriter(environmentFile)) {
            writeToFile.append(appendDocument);
        }
    }
    
}
