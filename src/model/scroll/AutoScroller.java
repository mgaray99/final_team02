package model.scroll;

import java.util.List;
import model.HitBox;
import model.entity.IEntity;
import model.entity.Player;

public class AutoScroller implements Scroller{
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
  public void scroll(List<IEntity> entityList, Player player) {
    entityList.forEach(entity -> scrollEntity(entity));
  }

  /**
   * Moves the Entity
   * @param entity the Entity to be scrolled
   */
  private void scrollEntity(IEntity entity) {
    HitBox hitBox = entity.getHitBox();
    hitBox.translateX(xScroll);
    hitBox.translateY(yScroll);
  }
}
