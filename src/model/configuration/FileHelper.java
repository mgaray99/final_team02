package model.configuration;

//import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A utility class that assists in validating or parsing files in addition to
 * assisting in the creation of Properties instances.
 * @author Mike Garay
 */
public class FileHelper {

    public static final String CSV_FILE_TYPE = "csv";
    public static final String PROPERTIES_FILE_TYPE = "properties";
    public static final int PARSE_FAILURE_REPLACEMENT = -1;

    /**
     * A function that obtains the extension of a given file
     * @param fileIn The file whose extension is attempted to be returned
     * @return A String representing the extension of a file
     */
    public static String getFileExtension(File fileIn){
        String filePath = fileIn.getPath();
        String fileName = new File(filePath).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * Removes the file extension of a path if it is present
     * @param pathIn A String representing the path of a file
     * @return The inputted String path of a file without the file extension if it was present
     */
    public static String removeFileExtensionIfPresent(String pathIn){
        int dotIndex = pathIn.lastIndexOf('.');
        return (dotIndex == -1) ? pathIn : pathIn.substring(0, dotIndex);
    }

    /**
     * A function that obtains the extension of a given file path
     * @param pathIn A String representing the path of a file
     * @return A String representing the extension of a file
     */
    public static String getFileExtension(String pathIn){
        int dotIndex = pathIn.lastIndexOf('.');
        return (dotIndex == -1) ? "" : pathIn.substring(dotIndex + 1);
    }

    /**
     * A function that determines whether or not a File is a CSV file
     * @param fileIn The File whose extension is checked
     * @return The boolean result of the determination
     */
    public static boolean isCSVFile(File fileIn){
        return getFileExtension(fileIn).equals(CSV_FILE_TYPE);
    }

    /**
     * A function that determines whether or not the name of a file makes it a CSV file
     * @param path The String path representing the path of a file to be checked
     * @return The boolean result of the determination
     */
    public static boolean isCSVFile(String path){
        return getFileExtension(path).equals(CSV_FILE_TYPE);
    }

    /**
     * A function taht determines whether or not the name of a file makes it a properties file
     * @param path The String path representing the path of a file to be checked
     * @return The boolean result of the determination
     */
    public static boolean isPropertiesFile(String path){
        return getFileExtension(path).equals(PROPERTIES_FILE_TYPE);
    }

    /**
     * A function that attempts to parse an integer
     * @param string The String from which an integer is attempted to be parsed from
     * @return The parsed integer, or -1 which represents a parse failure
     */
    public static int tryParseInt(String string){
        try{
            return Integer.parseInt(string);
        } catch(NumberFormatException e){
            return PARSE_FAILURE_REPLACEMENT;
        }
    }

    /**
     * A function that attempts to parse a String array into an int array
     * @param stringArrayIn The String array from which an integer array is attempted to be parsed from
     * @return The parsed integer array, with -1 representing any parse failures
     */

    public static int[] parseIntForStringArray(String[] stringArrayIn){
        int[] intArray = new int[stringArrayIn.length];
        for (int index = 0; index < stringArrayIn.length; index++) {
            try {
                intArray[index] = Integer.parseInt(stringArrayIn[index]);
            }
            catch (NumberFormatException e){
                System.out.println("Unable to parse integer at index " + index + "! Attempted to parse: " + stringArrayIn[index]);
                intArray[index] = PARSE_FAILURE_REPLACEMENT;
            }
        }
        return intArray;
    }

    /**
     * A function that attempts to create an InputStream from a properties file
     * @param clazz The class whose loader is used to attempt to obtain the properties file as a stream
     * @param resource A String representing the properties file name
     * @return An InputStream, or null if unable to be created
     */
    //@Nullable
    public static InputStream tryCreateInputStreamFromPropertiesFileName(Class clazz, String resource) {
        return isPropertiesFile(resource) ? clazz.getClassLoader().getResourceAsStream(resource) : null;
    }

    /**
     * A function that attempts to create a Properties with values loaded from the given InputStream
     * @param inputStream An InputStream containing the properties file values
     * @return A Properties instance, which may or may not contain the loaded properties
     */
    public static Properties createPropertiesAndTryLoadFromStream(InputStream inputStream) {
        Properties properties = new Properties();
        if(inputStream == null){
            System.out.println("Received null input stream, returning early!");
            return properties;
        }
        try {
            properties.load(inputStream);
        } catch (IOException ignored) {
            System.out.println("Did not load properties from input stream!");
        }
        return properties;
    }

    /**
     * A function that attempts to create a Properties with values loaded from a given properties file name
     * @param clazz The class whose loader is used to attempt to obtain the properties file as a stream
     * @param resource A String representing the properties file name
     * @return A Properties instance, which may or may not contain the loaded properties
     */
    public static Properties createPropertiesAndTryLoadFromResource(Class clazz, String resource){
        return createPropertiesAndTryLoadFromStream(tryCreateInputStreamFromPropertiesFileName(clazz, resource));
    }

}

