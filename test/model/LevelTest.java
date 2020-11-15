package model;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import model.entity.Player;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class LevelTest extends DukeApplicationTest {

  private static final int PLAYER_XVEL = 1;
  private static final int PLAYER_YVEL = 4;
  private static final int NEW_PLAYERY = 14;

  private static final int DEFAULTX = 7;
  private static final int DEFAULTY = 6;

  private Level level;

  @Override
  public void start(Stage stage) throws InvalidFileException {

    GameConfiguration gameConfiguration = new GameConfiguration("doodlejump.properties");
    LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile());
    level = new Level(levelLoader);
  }

  /**
   * Tests to make sure that when the player falls off the screen, his location resets to its
   * initial location
   */
  @Test
    public void testPlayerLocationResets() {
      Player player = level.getPlayerList().get(0);
      assertEquals(DEFAULTX, player.getHitBox().getXLeft());
      assertEquals(DEFAULTY, player.getHitBox().getYTop());

      player.setYVel(PLAYER_YVEL);
      player.setXVel(PLAYER_XVEL);
      player.updatePosition();
      level.step();

      assertNotEquals(DEFAULTX, player.getHitBox().getXLeft());
      assertNotEquals(DEFAULTY, player.getHitBox().getYTop());

      player.getHitBox().setYTop(NEW_PLAYERY);
      player.updatePosition();
      level.step();

      player = level.getPlayerList().get(0);
      assertEquals(DEFAULTX, player.getHitBox().getXLeft());
      assertEquals(DEFAULTY, player.getHitBox().getYTop());
    }
}