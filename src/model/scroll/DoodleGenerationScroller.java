package model.scroll;

import java.util.List;
import model.entity.IEntity;
import model.entity.Player;

public class DoodleGenerationScroller extends ManualScroller{

  private AutoGenerationHelper helper;
  private final int GENERATE_MAX_BOUND;
  private double flagY;

  public DoodleGenerationScroller(double left, double right, double up, double down,
      int max, String path) {
    super(left,right,up,down);
    helper = new AutoGenerationHelper(path);

    GENERATE_MAX_BOUND = max;
    flagY = max;
  }

  /**
   * Scrolls all of the entities
   * @param entityList the List of Entities
   * @param player the player of the level
   */
  @Override
  public void scroll(List<IEntity> entityList, Player player) {
    checkForGeneration(entityList);

    flagY-= player.getYVel();

    super.scroll(entityList, player);
  }

  /**
   * Checks to see if it's necessary to generate a new generation
   */
  private void checkForGeneration(List<IEntity> entityList) {
    if (flagY >= GENERATE_MAX_BOUND) {
      helper.generateForEntityList(entityList, flagY, 0);
      flagY-=helper.getAddedNumRows();
    }
  }
}
