package model.scroll;

import java.awt.geom.Rectangle2D;
import java.util.List;
import model.entity.Entity;
import model.entity.PlayerEntity;

public class AutoScroller extends Scroller{
    protected double xScroll;
    protected double yScroll;

    public AutoScroller(double x, double y) {
      xScroll = x;
      yScroll = y;
    }

  /**
   * Scrolls the list of Entitiies
   * @param entityList the List of Entities
   * @param player the player of the level
   */
  @Override
  public void scroll(List<Entity> entityList, PlayerEntity player) {
    entityList.forEach(entity -> scrollEntity(entity));
  }

  /**
   * Moves the Entity
   * @param entity the Entity to be scrolled
   */
  private void scrollEntity(Entity entity) {
    Rectangle2D.Float hitBox = entity.getHitBox();
    hitBox.x += xScroll;
    hitBox.y += yScroll;
  }
}
