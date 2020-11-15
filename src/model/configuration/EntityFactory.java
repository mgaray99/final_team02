package model.configuration;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entity.IEntity;
import org.jetbrains.annotations.Nullable;

public class EntityFactory {

  private LevelDecoder decoder;
  private Map<String, String> idToEntityMap;
  private static final String ENTITY_PACKAGE_PATH = "model.entity.";
  private static final String PLAYER_KEY = "1";
  private final String[] validPlayerValues = {"MarioPlayer", "FlappyPlayer", "DoodlePlayer"};

  public EntityFactory() {
    try {
      decoder = new LevelDecoder();
      idToEntityMap = new HashMap<>(decoder.getIdToEntityMap());
    }
    catch (IOException e) {

    }
  }

  /**
   * Updates the map so that the <PLAYER_KEY, idToEntityMap.get(PLAYER_KEY)> pair becomes
   * <PLAYER_KEY, newMapping>
   * @param newMapping the new String value that will be stored at PLAYER_KEY in the mapping
   */
  public void updatePlayerMapping(String newMapping) {
    List<String> validPlayerValueList = Arrays.asList(validPlayerValues);
    if (validPlayerValueList.contains(newMapping)) {
      idToEntityMap.put(PLAYER_KEY, newMapping);
    }
  }

  @Nullable
  public IEntity createEntity(String entityString, double rowIndex, double colIndex) {
      IEntity decodedEntity;
      String decodedEntityString = idToEntityMap.get(entityString);
      if(decodedEntityString == null) return (IEntity)null;

      decodedEntity = reflectEntity(decodedEntityString, rowIndex, colIndex);
      return decodedEntity;

  }

  @Nullable
  public IEntity reflectEntity(String decodedEntityString, double rowIndex, double colIndex) {
    IEntity decodedEntity;
    try {
      Class entityClass = Class.forName(ENTITY_PACKAGE_PATH + decodedEntityString);
      Constructor entityConstructor = entityClass.getConstructor(double.class, double.class);
      decodedEntity = (IEntity) entityConstructor.newInstance(rowIndex, colIndex);
      return decodedEntity;
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | InstantiationException
        | InvocationTargetException
        | IllegalAccessException e) {
      return (IEntity) null;
    }
  }
}
