package model.scroll;

import java.util.List;
import model.autogenerator.GenerationException;
import model.entity.IEntity;
import model.entity.Player;

public class AutoGenerationScroller extends AutoScroller {
  private double flagX;
  private AutoGenerationHelper helper;
  private final int GENERATE_MAX_BOUND;
  private static final String EXCEPTION_MESSAGE = "Failed to build auto-generation";

  public AutoGenerationScroller(double xScr, double yScr, String path) {
    super(xScr,yScr);
    try {
      helper = new AutoGenerationHelper(path);

      GENERATE_MAX_BOUND = NUM_BLOCKS;
      flagX = GENERATE_MAX_BOUND;
    }
    catch (Exception e) {
      throw new GenerationException(EXCEPTION_MESSAGE);
    }
  }

  /**
   * Scrolls all of the entities
   * @param entityList the List of Entities
   * @param player the player of the level
   */
  @Override
  public void scroll(List<IEntity> entityList, Player player) {
    checkForGeneration(entityList);
    flagX+=xScroll;

    super.scroll(entityList, player);
  }

  /**
   * Checks to see if it's necessary to generate a new generation
   */
  private void checkForGeneration(List<IEntity> entityList) {
    if (flagX <= GENERATE_MAX_BOUND) {
      helper.generateForEntityList(entityList, 0, flagX);
      flagX+= helper.getAddedNumColumns();
      cleanGarbage(entityList);
    }
  }

  /**
   * Checks the entityList to see if any of the entities have gone off screen forever (i.e. have
   * x < 0), if so, removes them from entityList
   *
   * @param entityList the list of entities to check
   */
  private void cleanGarbage(List<IEntity> entityList) {
    for (int index = entityList.size() - 1; index >= 0; index --) {
      IEntity entity = entityList.get(index);
      if (entity.getHitBox().getXRight() < 0) {
        entityList.remove(entity);
      }
    }
  }
}
