package model.scroll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
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
 * Tests the HorizontalGenerationScroller class
 */

public class HorizontalGenerationScrollerTest extends DukeApplicationTest{

  private static final int MAX = 15;
  private static final String PATH = "resources/game_configuration/auto/autoflappy.xml";
  private static final double XSCROLL = -0.5;
  private static final double YSCROLL = 0.5;

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
   * Tests that the HorizontalGenerationScroller correctly scrolls everything when not generating
   * a new portion of the game
   */
  @Test
  public void testSimpleScroll() {
    AutoGenerationScroller scroller = new AutoGenerationScroller(XSCROLL,YSCROLL, PATH);
    scroller.scroll(level, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().getXLeft());
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().getXLeft());
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().getXLeft());

    assertEquals(PLAYERY + YSCROLL, playerEntity.getHitBox().getYTop());
    assertEquals(BARRIERY + YSCROLL, barrierBlockEntity.getHitBox().getYTop());
    assertEquals(ENEMYY + YSCROLL, enemyEntity.getHitBox().getYTop());
  }
}