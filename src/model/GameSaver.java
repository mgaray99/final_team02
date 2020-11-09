package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import model.configuration.FileHelper;
import model.configuration.LevelDecoder;
import model.entity2.IEntity;

/**
 * A class responsible for writing an existing state of the simulation to a CSV file
 * @author Mike Garay
 */
public class GameSaver {

    private static final String CSV_EXTENSION = ".csv";
    private static final int DEFAULT_ENTITY_KEY = 0;
    private Level currentLevel;

    /**
     * Constructs a GameSaver
     * @param currentLevel A Level representing the current state of the level
     */
    public GameSaver(Level currentLevel){
        this.currentLevel = currentLevel;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Writes a new CSV file given a file name
     * @param fileNameIn The name of the file to be written
     */
    public void writeNewLevelCSVFile(String fileNameIn) {
        String fileNameToWrite =
                FileHelper.isCSVFile(fileNameIn) ?
                        fileNameIn :
                        FileHelper.removeFileExtensionIfPresent(fileNameIn) + CSV_EXTENSION;


        try {
            writeFile(fileNameToWrite);
        } catch (IOException ioException){
            // TODO:
            ioException.printStackTrace();
        }

    }

    private void writeFile(String fileNameToWrite) throws IOException {
        LevelDecoder levelDecoder = new LevelDecoder();
        Map<String, String> levelDecoderMap = levelDecoder.getIdToEntityMap();
        FileWriter seedCSVWriter = new FileWriter(fileNameToWrite);
        for(int i = 0; i < currentLevel.getLevelLength(); i++){
            StringBuilder currentRow = new StringBuilder();
            for(int j = 0; j < currentLevel.getLevelWidth(); j++){
                IEntity entity = currentLevel.getEntityAt(j, i);
                String entityTypeString = null;
                if (entity != null) {
                    entityTypeString = entity.getType();
                }
                Stream<String> matchingKeysForEntity = getMatchingKeysForValue(levelDecoderMap, entityTypeString);
                // Use the first key found, regardless of if there are multiple
                Optional<String> optionalEntityDecoderKey = matchingKeysForEntity.findFirst();
                if(optionalEntityDecoderKey.isPresent()){
                    currentRow.append(optionalEntityDecoderKey.get());
                }
                else{
                    currentRow.append(DEFAULT_ENTITY_KEY);
                }
                if(j < currentLevel.getLevelWidth() - 1){
                    currentRow.append(",");
                }
            }
            seedCSVWriter.append(currentRow);
            if(i < currentLevel.getLevelLength() - 1){
                seedCSVWriter.append("\n");
            }
        }

        seedCSVWriter.flush();
        seedCSVWriter.close();
    }

    /**
     * Gets a stream of possible keys for a value in a map
     * Source: https://www.baeldung.com/java-map-key-from-value
     * @param map The Map in which to find matching keys for the given value
     * @param value The value to find matching keys for
     * @return A Stream of the same type as the Map's keys that should contain any matching keys for the given value
     */
    public <K, V> Stream<K> getMatchingKeysForValue(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}

