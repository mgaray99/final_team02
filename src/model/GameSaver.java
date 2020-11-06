package model;

import model.configuration.FileHelper;
import model.configuration.LevelDecoder;
import model.entity.IEntityType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
     * @param currentLevel A ConwayGrid representing the current state of the simulation
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
     * @throws IOException if the named file exists but is a directory rather
     * than a regular file, does not exist but cannot be created,
     * or cannot be opened for any other reason
     */
    public void writeNewLevelCSVFile(String fileNameIn) throws IOException {
        String fileNameToWrite =
                FileHelper.isCSVFile(fileNameIn) ?
                        fileNameIn :
                        FileHelper.removeFileExtensionIfPresent(fileNameIn) + CSV_EXTENSION;


        try {
            writeFile(fileNameToWrite);
        } catch (PairBuilderInstantiationException pairBuilderInstantiationException) {
            // TODO:
        } catch (IOException ioException){
            // TODO:
        }

    }

    private void writeFile(String fileNameToWrite) throws IOException {
        LevelDecoder levelDecoder = new LevelDecoder();
        Map<String, String> levelDecoderMap = levelDecoder.getIdToEntityMap();
        FileWriter seedCSVWriter = new FileWriter(fileNameToWrite);
        for(int i = 0; i < currentLevel.getLevelLength(); i++){
            StringBuilder currentRow = new StringBuilder();
            for(int j = 0; j < currentLevel.getLevelWidth(); j++){
                IEntityType entityType = currentLevel.getEntity(j, i).getEntityType();
                String entityTypeString = entityType.getTypeID();
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

