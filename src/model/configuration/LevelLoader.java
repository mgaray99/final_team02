package model.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.entity.EntityType;
import model.entity.IEntityType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class responsible for creating the initial state of a game
 * @author Mike Garay
 */

public class LevelLoader {
    private final ArrayList<ArrayList<IEntityType>> levelMatrix = new ArrayList<>();
    private final Map<String, String> levelDecoder;
    /**
     * Constructs a LevelLoader given a CSV file
     * @param levelFileIn The CSV File to be used for seed creation
     * @throws InvalidFileException if the file is a directory,
     * an invalid file, or not a CSV file
     */
    public LevelLoader(File levelFileIn) throws InvalidFileException {
        this.handleConstructionExceptions(levelFileIn);
        //alex start
        try {
            LevelDecoder decoderMap = new LevelDecoder();
            levelDecoder = decoderMap.getIdToEntityMap();
        }
        catch (Exception e) {
            throw new InvalidFileException(ExceptionReason.FILE_NOT_FOUND, levelFileIn.getPath());
        }
        //alex end
        this.createLevelMatrix(levelFileIn);
    }

    private void createLevelMatrix(File levelFileIn) throws InvalidFileException {
        try {
            Scanner fileReader = new Scanner(levelFileIn);
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String[] currentStringArray = currentLine.split(",");
                ArrayList<IEntityType> currentRow = new ArrayList<>();
                buildRow(currentStringArray, currentRow);
                this.levelMatrix.add(currentRow);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new InvalidFileException(ExceptionReason.FILE_NOT_FOUND, levelFileIn.getPath());
        }
    }

    /**
     * Fills an empty List of IEntityType with the IEntityTypes corresponding to String values
     * in an array (i.e. "ENEMY" -> IEntityType.ENEMY)
     *
     * @param currentStringArray the String array containing the entity Strings to add to the row
     * @param currentRow the List object to store the IEntityTypes corresponding to those entity
     *                   Strings
     */
    private void buildRow(String[] currentStringArray, List<IEntityType> currentRow) {
        for (String entityString : currentStringArray) {
            String formattedEntityString = entityString.toUpperCase();
            IEntityType entityType;
            // alex start
            formattedEntityString = levelDecoder.get(entityString);
            // alex end
            try {
                entityType = EntityType
                    .valueOf(formattedEntityString); // Should make this allow for other enums
            } catch (IllegalArgumentException illegalArgumentException) {
                entityType = EntityType.EMPTY;
            }

                currentRow.add(entityType);
        }
    }

    /**
     * An accessor for the int matrix of a Seed
     * @return The int matrix of the Seed instance
     */
    public ArrayList<ArrayList<IEntityType>> getLevelMatrix() {
        return this.levelMatrix;
    }

    private void handleConstructionExceptions(File levelFileIn) throws InvalidFileException {
        if(levelFileIn.isDirectory()){
            throw new InvalidFileException(
                    ExceptionReason.DIRECTORY,
                    levelFileIn.getPath());
        }

        else if(!levelFileIn.isFile()){
            throw new InvalidFileException(
                    ExceptionReason.NOT_A_FILE,
                    levelFileIn.getPath());
        }

        else if(!FileHelper.isCSVFile(levelFileIn)){
            throw new InvalidFileException(
                    ExceptionReason.NOT_A_CSV_FILE,
                    levelFileIn.getPath());
        }
    }


}
