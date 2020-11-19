package model.scroll;

import java.util.Map;
import java.util.Optional;

import model.Level;
import model.autogenerator.AutoGenerator;
import model.autogenerator.GenerationException;
import model.configuration.EntityFactory;
import api.model.configuration.ILevelDecoder;
import api.model.configuration.IEntityFactory;
import model.configuration.LevelDecoder;
import api.model.entity.IEntity;

public class AutoGenerationHelper {

  private AutoGenerator generator;
  private IEntityFactory factory;
  private Map<String, String> decoderMap;
  protected String[][] currentGeneration;


  public AutoGenerationHelper(String generatorPath) {
    try {
      factory = new EntityFactory();
      generator = new AutoGenerator(generatorPath);
      ILevelDecoder decoder = new LevelDecoder();
      decoderMap = decoder.getIdToEntityMap();
      currentGeneration = generator.generateNextBlock();
    }
    catch (Exception e) {
      throw new GenerationException("");
    }
  }


  /**
   * Creates a new part of the level and adds it to the entity list
   * @param level the Level in which to insert the entities
   */
  public void generateForLevel(Level level, double rowOffset, double colOffset) {
    currentGeneration = generator.generateNextBlock();

    for (int row = 0; row < currentGeneration.length; row+=1) {
      for (int column = 0; column < currentGeneration[0].length; column+=1) {
        String entityCode = currentGeneration[row][column];
        insertIntoLevel(entityCode, level,
            rowOffset + row, colOffset + column);
      }
    }
  }

  /**
   * Builds an entity from entityCode and inserts it into level
   *
   * @param entityCode the String containing the type of entity
   * @param level the Level in which to insert the entity
   */
  private void insertIntoLevel(String entityCode, Level level, double row, double col) {
    Optional<IEntity> optionalEntity = factory.createEntity(entityCode, col, row);
    optionalEntity.ifPresent(level::addEntity);
  }

  /**
   * Reveals the number of new rows that have been added to the Level parameter in
   * generateForLevel() as of the last call to that method
   * @return the number of rows in the "current" currentGeneration array
   */
  public int getAddedNumRows() {
    return currentGeneration.length;
  }

  /**
   * Reveals the number of new columns that have been added to the Level parameter in
   * generateForLevel() as of the last call to that method
   * @return the number of new columns in the "current" currentGeneration array
   */
  public int getAddedNumColumns() {
    return currentGeneration[0].length;
  }
}
