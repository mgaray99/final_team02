package model.configuration;

/**
 * An exception class responsible for communication file read failures during simulation creation.
 * As it extends from Exception, the exceptions should be caught in a try/catch block.
 * @author Mike Garay
 */
public class InvalidFileException extends Exception{

    /**
     * Constructs an InvalidFileException
     * @param exceptionReason The reason the file was found to be invalid
     * @param fileName The name of the file
     */
    public InvalidFileException(String exceptionReason, String fileName){
        super(exceptionReason + " File was: " + fileName);
    }
}
