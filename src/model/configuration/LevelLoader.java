package model.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class responsible for creating the initial state of a game
 * @author Mike Garay
 */

public class LevelLoader {

    public static final int DEFAULT_GRID_LENGTH = 10;
    public static final int DEFAULT_GRID_WIDTH = 10;
    private int gridLength;
    private int gridWidth;
    private int[][] levelMatrix;

    /**
     * Constructs a LevelLoader given a CSV file
     * @param levelFileIn The CSV File to be used for seed creation
     * @throws InvalidFileException if the file is a directory,
     * an invalid file, or not a CSV file
     */
    public LevelLoader(File levelFileIn) throws InvalidFileException {
        this.handleConstructionExceptions(levelFileIn);
        this.createLevelMatrix(levelFileIn);
    }

    /**
     * Constructs a LevelLoader with default values of 10x10 grid size and filled entirely with zeros
     */
    public LevelLoader(){
        this.gridLength = DEFAULT_GRID_LENGTH;
        this.gridWidth = DEFAULT_GRID_WIDTH;
        this.levelMatrix = createDefaultSeedMatrix();
    }

    /**
     * Attempts to create a LevelLoader given a CSV file.
     * If it fails due to InvalidConfigurationException,
     * then it instead creates a LevelLoader with default values
     * @param csvFile The CSV File to be used for level loader creation
     * @return A valid LevelLoader instance
     */
    public static LevelLoader createLevelLoader(File csvFile){
        LevelLoader levelLoader;
        try {
            levelLoader = new LevelLoader(csvFile);
        } catch (InvalidFileException e) {
            System.out.println(e.getMessage());
            levelLoader = new LevelLoader();
        }
        return levelLoader;
    }

    /**
     * Creates an int matrix (2D int array)
     * with a default width and length of 10
     * filled entirely with zeros
     * @return an int matrix (2D int array)
     */
    public static int[][] createDefaultSeedMatrix(){
        int[][] defaultSeedMatrix = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_LENGTH];
        for (int[] matrix : defaultSeedMatrix) {
            Arrays.fill(matrix, 0);
        }
        return defaultSeedMatrix;
    }

    private void createLevelMatrix(File seedCSVFileIn) throws InvalidFileException {
        try {
            Scanner fileReader = new Scanner(seedCSVFileIn);
            int rowCounter = -1;
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String[] currentStringArray = currentLine.split(",");
                int[] currentIntArray = FileHelper.parseIntForStringArray(currentStringArray);
                boolean isDimensionArray = rowCounter == -1;
                if(isDimensionArray){
                    this.initializeMatrix(currentIntArray);
                }
                else{
                    this.levelMatrix[rowCounter] = currentIntArray;
                }
                rowCounter++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new InvalidFileException("Unable to read seed file passed in!",seedCSVFileIn.getPath());
        }
    }

    private void initializeMatrix(int[] dimensionArray){
        boolean cannotDetermineGridDimensions = dimensionArray.length < 2;

        if(cannotDetermineGridDimensions){
            throw new RuntimeException("Cannot determine grid length and width if header row does not have two integer values!");
        }
        else{
            this.gridLength = dimensionArray[0];
            this.gridWidth = dimensionArray[1];
            this.levelMatrix = new int[gridLength][gridWidth];
        }
    }

    /**
     * An accessor for the grid length of a Seed
     * @return The grid length of the Seed instance
     */
    public int getGridLength() {
        return this.gridLength;
    }

    /**
     * An accessor for the grid width of a Seed
     * @return The grid width of the Seed instance
     */
    public int getGridWidth() {
        return this.gridWidth;
    }

    /**
     * An accessor for the int matrix of a Seed
     * @return The int matrix of the Seed instance
     */
    public int[][] getLevelMatrix() {
        return this.levelMatrix;
    }

    private void handleConstructionExceptions(File levelFileIn) throws InvalidFileException {
        if(levelFileIn.isDirectory()){
            throw new InvalidFileException(
                    "Cannot create a level out of a directory!",
                    levelFileIn.getPath());
        }

        else if(!levelFileIn.isFile()){
            throw new InvalidFileException(
                    "Cannot create a level out of an invalid file!",
                    levelFileIn.getPath());
        }

        else if(!FileHelper.isCSVFile(levelFileIn)){
            throw new InvalidFileException(
                    "Cannot create a level out of a non-CSV file type!",
                    levelFileIn.getPath());
        }
    }


}
