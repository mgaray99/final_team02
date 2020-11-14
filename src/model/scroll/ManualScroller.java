package model.scroll;

import java.util.List;
import model.HitBox;
import model.Level;
import model.entity.IEntity;
import model.entity.Player;

public class ManualScroller implements Scroller {
    protected double leftBound;
    protected double rightBound;
    protected double upBound;
    protected double downBound;

    protected double currentXScroll;
    protected double currentYScroll;

    public static final int NO_SCROLL = -1;
    public static final int ALWAYS_SCROLL = 0;

    public ManualScroller(double l, double r, double u, double d) {
      leftBound = l;
      rightBound = r;
      upBound = u;
      downBound = d;
    }

  /**
   * Uses the velocity of the player to determine how to shift all entities in order to simulate
   * scrolling
   *
   * checkLeftScroll checks to see if the player is to the left of some vertical line x = leftBound
   * and moving further to the left if so, scrolls the screen to the left in order to catch up
   *
   * checkRightScroll checks to see if the player is to the right of some vertical line x =
   * rightBound and moving further to the right if so, scrolls the screen to the left in order to
   * catch up
   *
   * checkUpScroll checks to see if the player is above some horizontal line y = topBound and moving
   * further up and if so, scrolls the screen up in order to catch up
   *
   * checkDownScroll checks to see if the player is below some horizontal line y = bottomBound and
   * moving further down and if so, scrolls the screen down in order to catch up
   *
   * @param level
   * @param player the player whose data we are to use
   */
  @Override
  public void scroll(Level level, Player player) {
      currentXScroll = 0.0;
      currentYScroll = 0.0;

      checkLeftScroll(player);
      checkRightScroll(player);
      checkUpScroll(player);
      checkDownScroll(player);

      level.translateAllEntities(currentXScroll, currentYScroll);
  }

  /**
   * Resets the scroller
   */
  @Override
  public void reset() {
    //DO NOTHING
  }


  /**
   * Checks to see if the screen should scroll left
   * @param player the player whose action will determine the scroll
   */
  private void checkLeftScroll(Player player) {
      if ((player.getHitBox().getXLeft() < leftBound && leftBound!= NO_SCROLL)
          || leftBound == ALWAYS_SCROLL) {

          if (player.getXVel() <0) {
            currentXScroll -= player.getXVel();
          }
      }
  }

  /**
   * Checks to see if the screen should scroll right
   * @param player the player whose action will determine the scroll
   */
  private void checkRightScroll(Player player) {
    if ((player.getHitBox().getXRight() > rightBound && rightBound!= NO_SCROLL)
          || rightBound == ALWAYS_SCROLL) {

        if(player.getXVel() > 0) {
          currentXScroll -= player.getXVel();
        }
    }
  }

  /**
   *Checks to see if the screen should scroll up
   * @param player the player whose action will determine the scroll
   */
  private void checkUpScroll(Player player) {
    if ((player.getHitBox().getYTop() < upBound  && upBound!= NO_SCROLL)
          || upBound == ALWAYS_SCROLL) {

        if (player.getYVel() < 0) {
          currentYScroll -= player.getYVel();
        }
    }
  }

  /**
   * Checks to see if the screen should scroll down
   * @param player the player whose action will determine the scroll
   */
  private void checkDownScroll(Player player) {
    if ((player.getHitBox().getYBottom() > downBound && downBound!= NO_SCROLL)
          || downBound == ALWAYS_SCROLL) {

      if (player.getYVel() > 0) {
        currentYScroll -= player.getYVel();
      }
    }
  }


}
