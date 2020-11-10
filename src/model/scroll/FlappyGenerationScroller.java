package model.scroll;

import java.util.List;
import model.autogenerator.GenerationException;
import model.entity.IEntity;
import model.entity.Player;

public class FlappyGenerationScroller extends AutoScroller {
  private double flagX;
  private AutoGenerationHelper helper;
  private final int GENERATE_MAX_BOUND;
  private static final String EXCEPTION_MESSAGE = "Failed to build auto-generation";

  public FlappyGenerationScroller(double xScr, double yScr, int max, String path) {
    super(xScr,yScr);
    try {
      helper = new AutoGenerationHelper(path);

      GENERATE_MAX_BOUND = max;
      flagX = max;
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
      helper.generateForEntityList(entityList, 0, GENERATE_MAX_BOUND);
      flagX+= helper.getAddedNumColumns();
    }
  }
}
