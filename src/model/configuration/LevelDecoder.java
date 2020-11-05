package model.configuration;

import controller.PairBuilder;
import controller.PairBuilderInstantiationException;
import java.util.HashMap;
import java.util.Map;

public class LevelDecoder {
  private Map<String, String> keyToEntityTypeMap;
  private static final String ID_TO_ENTITY_PATH = "resources/game_configuration/idtoentitymap.txt";

  public LevelDecoder() throws PairBuilderInstantiationException {
    buildKeyToEntityTypeMap();
  }

  /**
   * Builds a map, mapping String ids -> String entity types (i.e. "1" -> "PLAYER")
   * @throws PairBuilderInstantiationException
   */
  private void buildKeyToEntityTypeMap() throws PairBuilderInstantiationException {
    PairBuilder builder = new PairBuilder(ID_TO_ENTITY_PATH);
    keyToEntityTypeMap = new HashMap<>(builder.getFoundMap());
  }

  /**
   * Returns the map from keys to entities
   * @return keyToEntityTypeMap
   */
  public Map<String, String> getIdToEntityMap() {
    return keyToEntityTypeMap;
  }
}
