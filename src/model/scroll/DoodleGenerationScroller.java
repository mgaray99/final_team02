package model.scroll;

import java.util.List;
import model.Level;
import model.entity.IEntity;
import model.entity.Player;

public class DoodleGenerationScroller extends ManualScroller{

  private AutoGenerationHelper helper;
  private final int GENERATE_MAX_BOUND;
  private double flagY;

  public DoodleGenerationScroller(double left, double right, double up, double down,
      String path) {
    super(left,right,up,down);
    helper = new AutoGenerationHelper(path);

    GENERATE_MAX_BOUND = -1 * helper.getAddedNumRows();
    flagY = GENERATE_MAX_BOUND;
  }

  /**
   * Scrolls all of the entities
   * @param level
   * @param player the player of the level
   */
  @Override
  public void scroll(Level level, Player player) {

    super.scroll(level, player);

    flagY += currentYScroll;
    checkForGeneration(level);

  }

  /**
   * Checks to see if it's necessary to generate a new generation
   */
  private void checkForGeneration(Level level) {
    if (flagY >= GENERATE_MAX_BOUND) {
      helper.generateForLevel(level, flagY, 0);
      flagY-=helper.getAddedNumRows();
      cleanGarbage(level);
    }
  }

  /**
   * Checks the entityList to see if any of the entities have gone off screen forever (i.e. have
   * yTop > NUM_BLOCKS), if so, removes them from entityList
   *
   * @param level the Level whose garbage we're cleaning
   */
  private void cleanGarbage(Level level) {
    List<IEntity> entityList = level.getAllEntities();

    for (int index = entityList.size() - 1; index >= 0; index --) {
      IEntity entity = entityList.get(index);
      if (entity.getHitBox().getYTop() > NUM_BLOCKS) {
        entityList.remove(entity);
      }
    }
  }

  /**
   * Resets the scroller - i.e. return flagY to its starting position so that automatic level
   * generation can proceed as if this object has just been instantiated
   */
  @Override
  public void reset() {
    flagY = GENERATE_MAX_BOUND;
  }
}
