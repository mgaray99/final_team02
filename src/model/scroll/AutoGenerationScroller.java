package model.scroll;

import java.util.List;
import model.Level;
import model.autogenerator.GenerationException;
import model.entity.IEntity;
import model.entity.Player;

public class AutoGenerationScroller extends AutoScroller {
  private double flagX;
  private AutoGenerationHelper helper;
  private final int GENERATE_MAX_BOUND;
  private static final String EXCEPTION_MESSAGE = "Failed to build auto-generation";

  public AutoGenerationScroller(double xScr, double yScr, boolean pScrolls, String path) {
    super(xScr,yScr, pScrolls);

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
   * @param level the level to be scrolled
   * @param player the player of the level
   */
  @Override
  public void scroll(Level level, Player player) {
    checkForGeneration(level);
    flagX+=xScroll;

    super.scroll(level, player);
  }

  /**
   * Checks to see if it's necessary to generate a new generation
   */
  private void checkForGeneration(Level level) {
    if (flagX <= GENERATE_MAX_BOUND) {
      helper.generateForLevel(level, 0, flagX);
      flagX+= helper.getAddedNumColumns();
      cleanGarbage(level);
    }
  }

  /**
   * Checks the entityList to see if any of the entities have gone off screen forever (i.e. have
   * x < 0), if so, removes them from entityList
   *
   * @param level the Level whose garbage we're cleaning
   */
  private void cleanGarbage(Level level) {
    List<IEntity> entityList = level.getAllEntities();

    for (int index = entityList.size() - 1; index >= 0; index --) {
      IEntity entity = entityList.get(index);
      if (entity.getHitBox().getXRight() < 0) {
        entityList.remove(entity);
      }
    }
  }
}
