package api.model;

import model.Level;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public interface IGameSaver {
    Level getCurrentLevel();

    void setCurrentLevel(Level currentLevel);

    void writeNewLevelCSVFile(String fileNameIn);

    void writeLevelFile(String fileNameToWrite) throws IOException;

    /**
     * Gets a stream of possible keys for a value in a map
     * Source: https://www.baeldung.com/java-map-key-from-value
     * @param map The Map in which to find matching keys for the given value
     * @param value The value to find matching keys for
     * @return A Stream of the same type as the Map's keys that should contain any matching keys for the given value
     */
    default <K, V> Stream<K> getMatchingKeysForValue(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}
