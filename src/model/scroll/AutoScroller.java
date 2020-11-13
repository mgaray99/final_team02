package model.scroll;

import java.util.List;
import model.HitBox;
import model.Level;
import model.entity.IEntity;
import model.entity.Player;

public class AutoScroller implements Scroller{
    protected double xScroll;
    protected double yScroll;
    protected boolean playerScrolls;

    public AutoScroller(double x, double y, boolean lockPlayerInPlace) {
      xScroll = x;
      yScroll = y;
      playerScrolls = lockPlayerInPlace;
    }

  /**
   * Scrolls the list of Entitiies
   * @param level the level to be scrolled
   * @param player the player of the level
   */
  @Override
  public void scroll(Level level, Player player) {
    level.translateAllEntities(xScroll, yScroll);

    if (playerScrolls) {
      scrollPlayer(player);
    }
  }

  /**
   * Moves the Player in the reverse direction as the level is moving to simulate locking them
   * in place
   * @param player the Player to be scrolled
   */
  private void scrollPlayer(Player player) {
    HitBox hitBox = player.getHitBox();
    hitBox.translateX(-1 * xScroll);
    hitBox.translateY(-1 * yScroll);
  }
}
