package model.scroll;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;
import model.EntityFactory;
import model.autogenerator.AutoGenerator;
import model.autogenerator.GenerationException;
import model.configuration.LevelDecoder;
import model.entity.Entity;
import model.entity.EntityType;
import model.entity.IEntityType;
import model.entity.PlayerEntity;

public abstract class AutoGenerationScroller extends AutoScroller {

  private AutoGenerator generator;
  private EntityFactory factory;
  private Map<String, String> decoderMap;

  protected String[][] currentGeneration;

  public AutoGenerationScroller(double xScr, double yScr, String generatorPath) throws GenerationException {
    super(xScr,yScr);

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
   * Scrolls all of the entities
   * @param entityList the List of Entities
   * @param player the player of the level
   */
  @Override
  public void scroll(List<Entity> entityList, PlayerEntity player) {
    super.scroll(entityList, player);
  }



  /**
   * Creates a new part of the level and adds it to the entity list
   * @param entityList the list of entities
   */
  protected void generateForEntityList(List<Entity> entityList, int rowOffset, int colOffset) {
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
  private void insertIntoEntityList(String entityCode, List<Entity> entityList, int row, int col) {
    IEntityType type = determineType(entityCode);

    if (!type.equals(EntityType.EMPTY)) {
      Entity entity = factory.createEntity(type, col, row);
      entityList.add(entity);
    }
  }

  /**
   * Determines the entity type represented from a String entityCode -> if this String is invalid,
   * returns EntityType.EMPTY
   * @param entityCode the code mapping to the entity type
   * @return an IEntityType representing the type of entity represented or EntityType.EMPTY
   */
  private IEntityType determineType(String entityCode) {
    try {
      entityCode = decoderMap.get(entityCode);
      return EntityType.valueOf(entityCode);
    }
    catch (IllegalArgumentException illegalArgumentException) {
      return  EntityType.EMPTY;
    }
  }
}
