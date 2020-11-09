package model.scroll;

import java.util.List;
import model.HitBox;
import model.entity.IEntity;
import model.entity.Player;

public class ManualScroller implements Scroller {
    private boolean scrollsX;
    private boolean scrollsY;

    public ManualScroller(boolean x, boolean y) {
      scrollsX = x;
      scrollsY = y;
    }

  /**
   * Uses the velocity of the player to determine how to shift all entities in order to simulate
   * scrolling
   *
   * @param entityList the list of Entity objects
   * @param player the player whose data we are to use
   */
  @Override
  public void scroll(List<IEntity> entityList, Player player) {
      double xScroll = 0;
      double yScroll = 0;


    if (scrollsX) {
      xScroll = -1 * player.getXVel();
    }
    if (scrollsY) {
      yScroll = -1 * player.getYVel();
    }

    for (IEntity entity : entityList) {
      scrollEntity(entity, xScroll, yScroll);
    }
  }

  /**
   * Moves the Entity
   * @param entity the Entity to be scrolled
   * @param xScroll the amount to scroll the entity in the x direction
   * @param yScroll the amount to scroll the entity in the y direction
   */
  private void scrollEntity(IEntity entity, double xScroll, double yScroll) {
    HitBox hitBox = entity.getHitBox();
    hitBox.translateX(xScroll);
    hitBox.translateY(yScroll);
  }
}
