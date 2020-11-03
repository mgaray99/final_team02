package model.configuration;

/**
 * An exception class responsible for communication configuration failures during simulation creation.
 * As it extends from Exception, the exceptions should be caught in a try/catch block.
 * Included is an enumeration of reasons that an exception would have been thrown, which can be accessed
 * from the exception instance itself.
 * @author Mike Garay
 */
public class InvalidConfigurationException extends Exception{
    private final ExceptionReason exceptionReason;

    /**
     * Constructs an InvalidConfigurationException due to an invalid config key
     * @param configKey The config key involved
     * @param configValue The invalid config value
     */
    public InvalidConfigurationException(String configKey, String configValue){
        super("Invalid configuration value for configuration key " + configKey + ": " + configValue);
        this.exceptionReason = ExceptionReason.INVALID_CONFIG_KEY;
    }

    /**
     * Constructs in InvalidConfigurationException due to an invalid config file
     * @param resource The name of the file involved
     */
    public InvalidConfigurationException(String resource){
        super("Invalid configuration file: " + resource);
        this.exceptionReason = ExceptionReason.INVALID_CONFIG_FILE;
    }

    /**
     * Accessor for the exception reason of an InvalidConfigurationException
     * @return The ExceptionReason
     */
    public ExceptionReason getExceptionReason() {
        return this.exceptionReason;
    }

    /**
     * An enum of reasons for exception, used when the InvalidConfigurationException is constructd
     */
    public enum ExceptionReason{
        INVALID_CONFIG_KEY,
        INVALID_CONFIG_FILE
    }
}
