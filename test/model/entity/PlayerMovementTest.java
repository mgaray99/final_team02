package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.GameModel;
import model.Level;
import model.configuration.EntityFactory;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import model.scroll.Scroller;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests whether the PlayerEntity can actually move
 */
public class PlayerMovementTest extends DukeApplicationTest {

  private static final String TEST_LEVEL_FILE_PATH = "playerMovementTestLevel.properties";

  GameModel gameModel = new GameModel(new GameConfiguration(TEST_LEVEL_FILE_PATH, this.getClass()));
  Player player = gameModel.getLevel().getPlayerList().get(0);

  public PlayerMovementTest() throws InvalidFileException {
  }


  @Test
  public void PlayerMoveLeftTest() {
    double oldXPos = player.getHitBox().getXLeft();
    gameModel.getKeyPressFunctions().startMovingPlayerLeft();
    gameModel.getLevel().step();
    double newXPos = player.getHitBox().getXLeft();
    assertTrue(newXPos < oldXPos);
  }

  @Test
  public void PlayerMoveRightTest() {
    double oldXPos = player.getHitBox().getXLeft();
    gameModel.getKeyPressFunctions().startMovingPlayerRight();
    gameModel.getLevel().step();
    double newXPos = player.getHitBox().getXLeft();
    assertTrue(newXPos > oldXPos);
  }

  @Test
  public void PlayerJumpPlayerGoesUpTest() {
    double oldYPos = player.getHitBox().getYTop();
    gameModel.getKeyPressFunctions().startPlayerJumping();
    gameModel.getLevel().step();
    gameModel.getLevel().step();
    double newYPos = player.getHitBox().getYTop();
    assertTrue(newYPos < oldYPos);
  }

  @Test
  public void PlayerJumpPlayerGoesBackDownTest() {
    gameModel.getKeyPressFunctions().startPlayerJumping();
    for(int i=0; i < 50; i++) {
      gameModel.getLevel().step();
    }
    gameModel.getKeyPressFunctions().stopPlayerJumping();
    double oldYPos = player.getHitBox().getYTop();
    for(int i=0; i < 10000; i++) {
      gameModel.getLevel().step();
    }
    double newYPos = player.getHitBox().getYTop();
    assertTrue(newYPos > oldYPos);
  }

  @Test
  public void PlayerJumpStopsAtGroundTest() {
    double oldYPos = player.getHitBox().getYTop();
    gameModel.getKeyPressFunctions().startPlayerJumping();
    gameModel.getLevel().step();
    gameModel.getKeyPressFunctions().stopPlayerJumping();
    for(int i=0; i < 10000; i++) {
      gameModel.getLevel().step();
    }
    double newYPos = player.getHitBox().getYTop();
    assertTrue(Math.abs(newYPos - oldYPos) < 1);
  }

  /**
   * Checks to make sure that if the player tries to go offscreen to the left, their
   * xLeft is reverted to be just tangent to the edge of the screen
   */
  @Test
  public void PlayerStaysInBoundsLeftTest() {
    Level level = gameModel.getLevel();

    Player player = level.getPlayerList().get(0);
    player.getHitBox().setXLeft(-5);
    assertEquals(-5, player.getHitBox().getXLeft());

    level.step();
    assertEquals(0, player.getHitBox().getXLeft());

  }

  /**
   * Checks to make sure that if the player tries to go offscreen to the right, their
   * xRight is reverted to be just tangent to the edge of the screen
   */
  @Test
  public void PlayerStaysInBoundsRightTest() {
    Level level = gameModel.getLevel();

    Player player = level.getPlayerList().get(0);
    player.getHitBox().setXRight(Scroller.NUM_BLOCKS + 2);
    assertEquals(Scroller.NUM_BLOCKS + 2, player.getHitBox().getXRight());

    level.step();
    assertEquals(Scroller.NUM_BLOCKS, player.getHitBox().getXRight());
  }

}
