package model.entity;

import util.DukeApplicationTest;


/**
 * Tests whether the PlayerEntity can actually move
 */
public class PlayerMovementTest extends DukeApplicationTest {
/*
  private static final String TEST_LEVEL_FILE_PATH = "playerMovementTestLevel.properties";

  GameModel gameModel = new GameModel(new GameConfiguration(TEST_LEVEL_FILE_PATH, this.getClass()));
  PlayerEntity playerEntity = gameModel.getLevel().getPlayerEntity();

  public PlayerMovementTest() throws InvalidFileException {
  }


  @Test
  public void PlayerMoveLeftTest() {
    double oldXPos = playerEntity.getHitBox().getX();
    gameModel.getKeyPressFunctions().startMovingPlayerLeft();
    gameModel.getLevel().step();
    double newXPos = playerEntity.getHitBox().getX();
    assertTrue(newXPos < oldXPos);
  }

  @Test
  public void PlayerMoveRightTest() {
    double oldXPos = playerEntity.getHitBox().getX();
    gameModel.getKeyPressFunctions().startMovingPlayerRight();
    gameModel.getLevel().step();
    double newXPos = playerEntity.getHitBox().getX();
    assertTrue(newXPos > oldXPos);
  }

  @Test
  public void PlayerJumpPlayerGoesUpTest() {
    double oldYPos = playerEntity.getHitBox().getY();
    gameModel.getKeyPressFunctions().startPlayerJumping();
    gameModel.getLevel().step();
    double newYPos = playerEntity.getHitBox().getY();
    assertTrue(newYPos > oldYPos);
  }

  @Test
  public void PlayerJumpPlayerGoesBackDownTest() {
    gameModel.getKeyPressFunctions().startPlayerJumping();
    for(int i=0; i < 5; i++) {
      gameModel.getLevel().step();
    }
    double oldYPos = playerEntity.getHitBox().getY();
    for(int i=0; i < 200; i++) {
      gameModel.getLevel().step();
    }
    double newYPos = playerEntity.getHitBox().getY();
    assertTrue(newYPos < oldYPos);
  }

  @Test
  public void PlayerJumpStopsAtGroundTest() {
    double oldYPos = playerEntity.getHitBox().getY();
    gameModel.getKeyPressFunctions().startPlayerJumping();
    for(int i=0; i < 200; i++) {
      gameModel.getLevel().step();
    }
    double newYPos = playerEntity.getHitBox().getY();
    assertTrue(Math.abs(newYPos - oldYPos) < 0.01);
  }
*/
}
