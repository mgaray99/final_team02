package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.GameModel;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


public class KeyInputterTest extends DukeApplicationTest {

  private KeyInputter testInputter;

  private static final String DJ_INPUTS = "resources/keyinputs/doodlejumpkeyinputs.properties";
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
  public void start(Stage stage) throws InvalidFileException {
      testInputter = new KeyInputter(
          new GameModel(new GameConfiguration("configuration.properties")));
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to left (i.e. for the
   * default key input map, that would be DEFAULT_LEFT or A) the left() method is called
   */
  @Test
  public void testLeft() {
    testInputter.keyPressed(DEFAULT_LEFT);
    assertEquals(LEFT, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to up (i.e. for the
   * default key input map, that would be DEFAULT_UP or W) the up() method is called
   */
  @Test
  public void testUp() {
    testInputter.keyPressed(DEFAULT_UP);
    assertEquals(UP, testInputter.getLastPush());
  }
  /**
   * Tests to make sure that when the user pushes the key corresponding to right (i.e. for the
   * default key input map, that would be DEFAULT_RIGHT or D) the right() method is called
   */
  @Test
  public void testRight() {
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());
  }
  /**
   * Tests to make sure that when the user pushes the key corresponding to down (i.e. for the
   * default key input map, that would be DEFAULT_DOWN or S) the down() method is called
   */
  @Test
  public void testDown() {
    testInputter.keyPressed(DEFAULT_DOWN);
    assertEquals(DOWN, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to left (i.e. for the
   * default key input map, that would be DEFAULT_LEFT or A) the left() method is called
   */
  @Test
  public void testPause() {
    testInputter.keyPressed(DEFAULT_PAUSE);
    assertEquals(PAUSE, testInputter.getLastPush());
  }

  /**
   * Tests to make sure that when the user pushes the key corresponding to pause (i.e. for the
   * default key input map, that would be DEFAULT_PAUSE or P) the pause() method is called
   */
  @Test
  public void testIrrelevantKeyPush() {
    testInputter.keyPressed("L");
    assertEquals("", testInputter.getLastPush());
  }

  /**
   * Tests the situation where the user switches a valid replacement key (i.e. a key that is not
   * currently mapped to a method) into a valid current key (i.e. a key that is currently mapped
   * to a method)
   */
  @Test
  public void testValidSwap() {
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput(DEFAULT_RIGHT, "B");
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals("", testInputter.getLastPush());

    testInputter.keyPressed("B");
    assertEquals(RIGHT, testInputter.getLastPush());
  }

  /**
   * Tests the situation where the user switches a valid replacement key (i.e. a key that is not
   * currently mapped to a method) into an invalid current key (i.e. a key that is not currently
   * mapped to a method)
   */
  @Test
  public void testValidSwapInvalidCurrentKey() {
    testInputter.keyPressed(DEFAULT_RIGHT);
    assertEquals(RIGHT, testInputter.getLastPush());

    testInputter.swapKeyInput("C", "B");
    testInputter.keyPressed("C");
    assertEquals("", testInputter.getLastPush());
    testInputter.keyPressed("B");
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
    testInputter.keyPressed(DEFAULT_RIGHT);
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

  /**
   * Tests the method getKeyMethodPairings() to return the key -> method <String, String> pairs
   * that the KeyInputter currently contains
   */
  @Test
  public void testGetKeyMethodPairings() {
    List<Pair<String, String>> keyMethodPairings = testInputter.getKeyMethodPairings();

    assertEquals(5, keyMethodPairings.size());
    String[] keys = {"A", "S", "D", "W", "P"};
    List<String> keysList = Arrays.asList(keys);

    for (Pair<String, String> pair : keyMethodPairings) {
      assertTrue(keysList.contains(pair.getKey()));
      switch (pair.getKey()) {
        case "A" -> assertEquals("left", pair.getValue());
        case "S" -> assertEquals("down", pair.getValue());
        case "D" -> assertEquals("right", pair.getValue());
        case "W" -> assertEquals("up", pair.getValue());
        case "P" -> assertEquals("pause", pair.getValue());
      }
    }
  }
}
