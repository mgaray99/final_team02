package model.scroll;

import java.awt.geom.Rectangle2D;
import java.util.List;
import model.entity.Entity;
import model.entity.PlayerEntity;

public class ManualScroller extends Scroller {
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
  public void scroll(List<Entity> entityList, PlayerEntity player) {
      double xScroll = 0;
      double yScroll = 0;


    if (scrollsX) {
      xScroll = -1 * player.getXVel();
    }
    if (scrollsY) {
      yScroll = -1 * player.getYVel();
    }

    for (Entity entity : entityList) {
      scrollEntity(entity, xScroll, yScroll);
    }
  }

  /**
   * Moves the Entity
   * @param entity the Entity to be scrolled
   * @param xScroll the amount to scroll the entity in the x direction
   * @param yScroll the amount to scroll the entity in the y direction
   */
  private void scrollEntity(Entity entity, double xScroll, double yScroll) {
    Rectangle2D.Float hitBox = entity.getHitBox();
    hitBox.x += xScroll;
    hitBox.y += yScroll;
  }
}
