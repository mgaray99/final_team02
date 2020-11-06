package model.configuration;

/**
 * An exception class responsible for communicating configuration failures during leevel creation.
 * As it extends from Exception, the exceptions should be caught in a try/catch block.
 * @author Mike Garay
 */
public class InvalidConfigurationException extends Exception{
    private final ModelExceptionReason modelExceptionReason;

    /**
     * Constructs an InvalidConfigurationException due to an invalid config key
     * @param configKey The config key involved
     * @param configValue The invalid config value
     */
    public InvalidConfigurationException(String configKey, String configValue){
        super("Invalid configuration value for configuration key " + configKey + ": " + configValue);
        this.modelExceptionReason = ModelExceptionReason.INVALID_CONFIG_KEY;
    }

    /**
     * Constructs in InvalidConfigurationException due to an invalid config file
     * @param resource The name of the file involved
     */
    public InvalidConfigurationException(String resource){
        super("Invalid configuration file: " + resource);
        this.modelExceptionReason = ModelExceptionReason.INVALID_CONFIG_FILE;
    }
}
