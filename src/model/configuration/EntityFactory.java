package model.configuration;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import model.entity.Block;
import model.entity.Enemy;
import model.entity.IEntity;
import model.entity.Player;
import model.entity.PowerUp;
import org.jetbrains.annotations.Nullable;

public class EntityFactory {

  private LevelDecoder decoder;
  private Map<String, String> levelDecoder;
  private static final String ENTITY_PACKAGE_PATH = "model.entity.";

  public EntityFactory() {
    try {
      decoder = new LevelDecoder();
      levelDecoder = new HashMap<>(decoder.getIdToEntityMap());
    }
    catch (IOException e) {

    }
  }

  public IEntity createEntity(String entityString, int rowIndex, int colIndex) {
      IEntity decodedEntity;
      String decodedEntityString = levelDecoder.get(entityString);
      if(decodedEntityString == null) return null;

      decodedEntity = reflectEntity(decodedEntityString, rowIndex, colIndex);
      return decodedEntity;

  }

  @Nullable
  private IEntity reflectEntity(String decodedEntityString, int rowIndex, int colIndex) {
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
