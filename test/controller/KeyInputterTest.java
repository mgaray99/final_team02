package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.stage.Stage;
import model.GameModel;
import model.configuration.GameConfiguration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


public class KeyInputterTest extends DukeApplicationTest {

  private KeyInputter testInputter;

  private static final String DJ_INPUTS = "resources/keyinputs/doodlejumpkeyinputs.txt";
  private static final String DEFAULT_PAUSE = "P";
  private static final String DEFAULT_UP = "W";
  private static final String DEFAULT_RIGHT = "D";
  private static final String DEFAULT_LEFT = "A";
  private static final String DEFAULT_DOWN = "S";

  private static final String LEFT = "left";
  private static final String UP = "up";
  private static final String RIGHT = "right";
  private static final String DOWN = "down";
  private static final String PAUSE = "pause";





  @Override
  public void start(Stage stage) {
      testInputter = new KeyInputter(new GameModel(new GameConfiguration()));
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to left (i.e. for the
   * default key input map, that would be DEFAULT_LEFT or A) the left() method is called
   */
  @Test
  public void testLeft() {
    testInputter.keyInput(DEFAULT_LEFT);
    assertEquals(LEFT, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to up (i.e. for the
   * default key input map, that would be DEFAULT_UP or W) the up() method is called
   */
  @Test
  public void testUp() {
    testInputter.keyInput(DEFAULT_UP);
    assertEquals(UP, testInputter.getLastPush());
  }
  /**
   * Tests to make sure that when the user pushes the key corresponding to right (i.e. for the
   * default key input map, that would be DEFAULT_RIGHT or D) the right() method is called
   */
  @Test
  public void testRight() {
    testInputter.keyInput(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());
  }
  /**
   * Tests to make sure that when the user pushes the key corresponding to down (i.e. for the
   * default key input map, that would be DEFAULT_DOWN or S) the down() method is called
   */
  @Test
  public void testDown() {
    testInputter.keyInput(DEFAULT_DOWN);
    assertEquals(DOWN, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to left (i.e. for the
   * default key input map, that would be DEFAULT_LEFT or A) the left() method is called
   */
  @Test
  public void testPause() {
    testInputter.keyInput(DEFAULT_PAUSE);
    assertEquals(PAUSE, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to pause (i.e. for the
   * default key input map, that would be DEFAULT_PAUSE or P) the pause() method is called
   */
  @Test
  public void testIrrelevantKeyPush() {
    testInputter.keyInput("L");
    assertEquals("", testInputter.getLastPush());
  }

  /**
   * Tests the situation where the user switches a valid replacement key (i.e. a key that is not
   * currently mapped to a method) into a valid current key (i.e. a key that is currently mapped
   * to a method)
   */
  @Test
  public void testValidSwap() {
    testInputter.keyInput(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput(DEFAULT_RIGHT, "B");
    testInputter.keyInput(DEFAULT_RIGHT);
    assertEquals("", testInputter.getLastPush());

    testInputter.keyInput("B");
    assertEquals(RIGHT, testInputter.getLastPush());
  }

  /**
   * Tests the situation where the user switches a valid replacement key (i.e. a key that is not
   * currently mapped to a method) into an invalid current key (i.e. a key that is not currently
   * mapped to a method)
   */
  @Test
  public void testValidSwapInvalidCurrentKey() {
    testInputter.keyInput(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput("C", "B");
    testInputter.keyInput("C");
    assertEquals("", testInputter.getLastPush());
    testInputter.keyInput("B");
    assertEquals("", testInputter.getLastPush());


    // i.e. make sure that all of the default key inputs still call the default methods
    testDown();
    testLeft();
    testPause();
    testRight();
    testUp();

  }

  /**
   * Tests the situation where the user switches an invalid replacement key (i.e. a key that is
   * currently mapped to a method so it can't be a replacement) into a valid current key
   * (i.e. a key that is currently mapped to a method)
   */
  @Test
  public void testValidSwapInvalidReplacementKey() {
    testInputter.keyInput(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput(DEFAULT_RIGHT, DEFAULT_LEFT);

    // i.e. make sure that the default left and right keys (A,D) still call the correct methods
    // (left, right)
    testLeft();
    testRight();
  }

  @Test
  public void testSwitchKeyInputSourceFile() {
    testInputter.loadKeyInputsFromFile(DJ_INPUTS);
  }

  /**
   * Tests the situation where the user tries to switch the
   */
  @Test
  public void testSwitchKeyInputBadSourceFile() {
    testInputter.loadKeyInputsFromFile("hi");
    testRight();
    testLeft();
    testDown();
    testUp();
    testPause();
  }
}
