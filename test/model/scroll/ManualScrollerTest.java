package model.scroll;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import model.entity.Block;
import model.entity.Enemy;
import model.entity.IEntity;
import model.entity.Player;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests the ManualScroller class
 */
public class ManualScrollerTest extends DukeApplicationTest {

  private static final int PLAYER_XVEL = 1;
  private static final int PLAYER_YVEL = 2;

  private static final int PLAYERX = 4;
  private static final int PLAYERY = 5;

  private static final int BARRIERX = 12;
  private static final int BARRIERY = 8;

  private static final int ENEMYX = 6;
  private static final int ENEMYY = 7;

  private Player playerEntity;
  private Block barrierBlockEntity;
  private Enemy enemyEntity;

  private List<IEntity> entityList;

  @Override
  public void start(Stage stage) {
    entityList = new ArrayList<>();

    playerEntity = new Player(PLAYERX, PLAYERY);
    barrierBlockEntity = new Block(BARRIERX, BARRIERY);
    enemyEntity = new Enemy(ENEMYX, ENEMYY);

    playerEntity.setXVel(PLAYER_XVEL);
    playerEntity.setYVel(PLAYER_YVEL);

    entityList.add(playerEntity);
    entityList.add(barrierBlockEntity);
    entityList.add(enemyEntity);
  }

  /**
   * Tests that when a scroller is set to only scroll horizontally, that when the player moves
   * in the x direction, only the x components of all enemy's hitboxes update
   */
  @Test
  public void testHorizontalScroller() {
    ManualScroller scroller = new ManualScroller(true, false);

    playerEntity.moveOneStep();
    scroller.scroll(entityList, playerEntity);

    assertEquals(PLAYERX, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX - PLAYER_XVEL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX - PLAYER_XVEL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + PLAYER_YVEL, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that when a scroller is set to only scroll vertically, that when the player moves
   * in the y direction, only the y components of all enemy's hitboxes update
   */
  @Test
  public void testVerticalScroller() {
    ManualScroller scroller = new ManualScroller(false, true);

    playerEntity.moveOneStep();
    scroller.scroll(entityList, playerEntity);

    assertEquals(PLAYERX + PLAYER_XVEL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY - PLAYER_YVEL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY - PLAYER_YVEL, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that when a scroller is set to scroll in either direction, that when the player moves
   * both the x and y components update
   */
  @Test
  public void testEitherScroller() {
    ManualScroller scroller = new ManualScroller(true, true);

    playerEntity.moveOneStep();
    scroller.scroll(entityList, playerEntity);

    assertEquals(PLAYERX, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX - PLAYER_XVEL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX - PLAYER_XVEL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY - PLAYER_YVEL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY - PLAYER_YVEL, enemyEntity.getHitBox().getYTop());
  }
}