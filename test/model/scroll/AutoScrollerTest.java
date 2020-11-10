package model.scroll;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import model.Level;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import model.entity.Block;
import model.entity.Enemy;
import model.entity.IEntity;
import model.entity.Player;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the AutoScroller class
 */
public class AutoScrollerTest extends DukeApplicationTest {

  private static final double XSCROLL = -0.5;
  private static final double YSCROLL = 0.5;
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

  private Level level;

  @Override
  public void start(Stage stage) throws InvalidFileException {

    playerEntity = new Player(PLAYERX, PLAYERY);
    barrierBlockEntity = new Block(BARRIERX, BARRIERY);
    enemyEntity = new Enemy(ENEMYX, ENEMYY);

    GameConfiguration gameConfiguration = new GameConfiguration("oneBlock.properties");
    LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile());
    level = new Level(levelLoader);

    level.addEntity(playerEntity);
    level.addEntity(barrierBlockEntity);
    level.addEntity(enemyEntity);
  }

  /**
   * Tests that the Autoscroller automatically moves all entities by the amounts specified
   */
  @Test
  public void testSimpleScroll() {
    AutoScroller scroller = new AutoScroller(XSCROLL,YSCROLL);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + YSCROLL, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY + YSCROLL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY + YSCROLL, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that the Autoscroller scrolls correctly when it is told not to scroll by any amount
   * vertically and only to scroll horizontally
   */
  @Test
  public void testScrollHorizontal() {
    AutoScroller scroller = new AutoScroller(XSCROLL,0);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that the Autoscroller scrolls correctly when it is told not to scroll by any amount
   * horizontally and only to scroll vertically
   */
  @Test
  public void testScrollVertical() {
    AutoScroller scroller = new AutoScroller(XSCROLL,YSCROLL);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + YSCROLL, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY + YSCROLL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY + YSCROLL, enemyEntity.getHitBox().getYTop());
  }

  /**
   * Tests that the Autoscroller correctly positions the player when they move right before a scroll
   */
  @Test
  public void testPlayerScroll() {
    AutoScroller scroller = new AutoScroller(XSCROLL,YSCROLL);

    playerEntity.setXVel(PLAYER_XVEL);
    playerEntity.setYVel(PLAYER_YVEL);
    playerEntity.moveOneStep();
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL + PLAYER_XVEL, playerEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + YSCROLL + PLAYER_YVEL, playerEntity.getHitBox().getYTop());
  }

}