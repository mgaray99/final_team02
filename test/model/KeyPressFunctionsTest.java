package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the KeyPressFunctions class
 */
public class KeyPressFunctionsTest extends DukeApplicationTest {

  KeyPressFunctions keyPressFunctions = new KeyPressFunctions();

  @Test
  public void isPausedTest() {
    assertFalse(keyPressFunctions.isPaused());
    keyPressFunctions.pauseGame();
    assertTrue(keyPressFunctions.isPaused());
    keyPressFunctions.resumeGame();;
    assertFalse(keyPressFunctions.isPaused());
  }

  @Test
  public void isPlayerMovingLeftTest() {
    assertFalse(keyPressFunctions.isPlayerMovingLeft());
    keyPressFunctions.startMovingPlayerLeft();
    assertTrue(keyPressFunctions.isPlayerMovingLeft());
    keyPressFunctions.stopMovingPlayerLeft();
    assertFalse(keyPressFunctions.isPlayerMovingLeft());
  }

  @Test
  public void isPlayerMovingRightTest() {
    assertFalse(keyPressFunctions.isPlayerMovingRight());
    keyPressFunctions.startMovingPlayerRight();
    assertTrue(keyPressFunctions.isPlayerMovingRight());
    keyPressFunctions.stopMovingPlayerRight();
    assertFalse(keyPressFunctions.isPlayerMovingRight());
  }

  @Test
  public void isPlayerJumpingTest() {
    assertFalse(keyPressFunctions.isPlayerJumping());
    keyPressFunctions.startPlayerJumping();
    assertTrue(keyPressFunctions.isPlayerJumping());
    keyPressFunctions.stopPlayerJumping();
    assertFalse(keyPressFunctions.isPlayerJumping());
  }


}
