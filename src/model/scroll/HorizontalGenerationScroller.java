package model.scroll;

import java.util.List;
import model.entity.IEntity;
import model.entity.Player;

public class HorizontalGenerationScroller extends AutoGenerationScroller {
  private double flagX;
  private final int GENERATE_MAX_BOUND;

  public HorizontalGenerationScroller(double xScr, double yScr, int max, String path) {
    super(xScr,yScr,path);
    GENERATE_MAX_BOUND = max;
    flagX = max;
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
      generateForEntityList(entityList, 0, GENERATE_MAX_BOUND);
      flagX+=currentGeneration[0].length;
    }
  }
}
