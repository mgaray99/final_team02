package model.configuration;

import api.model.configuration.ILevelDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class LevelDecoder implements ILevelDecoder {
  private Map<String, String> keyToEntityTypeMap;
  private static final String ID_TO_ENTITY_PATH =
      "resources/game_configuration/entityids.properties";

  public LevelDecoder() throws IOException, NullPointerException {
    buildKeyToEntityTypeMap();
  }

  /**
   * Builds a map, mapping String ids -> String entity types (i.e. "1" -> "PLAYER")
   * @throws IOException
   */
  @Override
  public void buildKeyToEntityTypeMap() throws IOException, NullPointerException {

    Properties properties = new Properties();
    InputStream stream =  getClass().getClassLoader().getResourceAsStream(
        ID_TO_ENTITY_PATH);
    properties.load(stream);

    keyToEntityTypeMap = new TreeMap(properties);
  }

  /**
   * Returns the map from keys to entities
   * @return keyToEntityTypeMap
   */
  @Override
  public Map<String, String> getIdToEntityMap() {
    return keyToEntityTypeMap;
  }
}
