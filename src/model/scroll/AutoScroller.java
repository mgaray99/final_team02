package model.scroll;

import java.util.List;
import model.HitBox;
import model.Level;
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
   * @param level the level to be scrolled
   * @param player the player of the level
   */
  @Override
  public void scroll(Level level, Player player) {
    List<IEntity> entityList = level.getAllEntities();
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
