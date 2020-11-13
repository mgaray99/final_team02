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
    }
  }
}
