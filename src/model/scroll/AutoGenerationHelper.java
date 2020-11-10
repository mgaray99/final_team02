package model.scroll;

import java.util.List;
import java.util.Map;
import model.autogenerator.AutoGenerator;
import model.autogenerator.GenerationException;
import model.configuration.EntityFactory;
import model.configuration.LevelDecoder;
import model.entity.IEntity;

public class AutoGenerationHelper {

  private AutoGenerator generator;
  private EntityFactory factory;
  private Map<String, String> decoderMap;
  protected String[][] currentGeneration;


  public AutoGenerationHelper(String generatorPath) {
    try {
      factory = new EntityFactory();
      generator = new AutoGenerator(generatorPath);
      LevelDecoder decoder = new LevelDecoder();
      decoderMap = decoder.getIdToEntityMap();
    }
    catch (Exception e) {
      throw new GenerationException("");
    }
  }


  /**
   * Creates a new part of the level and adds it to the entity list
   * @param entityList the list of entities
   */
  public void generateForEntityList(List<IEntity> entityList, int rowOffset, int colOffset) {
    currentGeneration = generator.generateNextBlock();

    for (int row = 0; row < currentGeneration.length; row+=1) {
      for (int column = 0; column < currentGeneration[0].length; column+=1) {
        String entityCode = currentGeneration[row][column];
        insertIntoEntityList(entityCode, entityList,
            rowOffset + row, colOffset + column);
      }
    }
  }

  /**
   * Builds an entity from entityCode and inserts it into entityList
   *
   * @param entityCode the String containing the type of entity
   * @param entityList the List of Entity to have the new entity inserted into itself
   */
  private void insertIntoEntityList(String entityCode, List<IEntity> entityList, int row, int col) {
    IEntity entity = factory.createEntity(entityCode, col, row);
    if (entity!=null) {
      entityList.add(entity);
    }
  }

  /**
   * Reveals the number of new rows that have been added to the List<IEntity> parameter in
   * generateForEntityList() as of the last call to that method
   * @return the number of rows in the "current" currentGeneration array
   */
  public int getAddedNumRows() {
    return currentGeneration.length;
  }

  /**
   * Reveals the number of new columns that have been added to the List<IEntity> parameter in
   * generateForEntityList() as of the last call to that method
   * @return the number of new columns in the "current" currentGeneration array
   */
  public int getAddedNumColumns() {
    return currentGeneration[0].length;
  }
}
