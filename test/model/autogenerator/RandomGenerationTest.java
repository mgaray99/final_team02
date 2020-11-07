package model.autogenerator;

import static org.junit.jupiter.api.Assertions.*;


import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the RandomGeneration class
 */
public class RandomGenerationTest extends DukeApplicationTest{

  private static final int NUM_COLS = 20;
  private static final int NUM_ROWS = 10;

  @Override
  public void start(Stage st) {

  }

  /**
   * Tests that a RandomGeneration's values (i.e. start row, end row, etc. equal the expected
   * parameters passed into the method
   *
   * @param inst the RandomInstruction
   * @param expectedEntity the expected entity String
   * @param expectedStartRow the expected start row
   * @param expectedEndRow the expected end row
   * @param expectedStartCol the expected start col
   * @param expectedEndCol the expected end col
   */
  private void testInstruction(RandomGeneration inst, String expectedEntity,
      int expectedStartRow, int expectedEndRow,
      int expectedStartCol, int expectedEndCol) {

    assertEquals(expectedEntity, inst.getEntityTypeToInsert());
    assertEquals(expectedStartRow, inst.getStartRow());
    assertEquals(expectedEndRow, inst.getEndRow());
    assertEquals(expectedStartCol, inst.getStartCol());
    assertEquals(expectedEndCol, inst.getEndCol());
  }

  /**
   * Tests that when the random instruction is built with "constant" parameters (i.e. randomizers
   * or uniform ranomizer symbols, it builds correctly
   */
  @Test
  public void testConstantParameters() {
      String[] args = {"Random", "2", "RIGHT:DOWN", "3", "4", "5", "6"};
      RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
      testInstruction(inst, "2", 3, 7,
          4, 9);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to RIGHT:DOWN means that the width will
   * move to the right and the height down from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionRightDown() {
    String[] args = {"Random", "2", "RIGHT:DOWN", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 5, 7,
        10, 11);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to RIGHT:UP means that the width will
   * move to the right and the height up from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionRightUp() {
    String[] args = {"Random", "2", "RIGHT:UP", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 3, 5,
        10, 11);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to LEFT:UP means that the width will
   * move to the left and the height up from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionLeftUp() {
    String[] args = {"Random", "2", "LEFT:UP", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 3, 5,
        9, 10);
  }

  /**
   * Tests that changing the "direction" parameter (args[2] to LEFT:DOWN means that the width will
   * move to the left and the height down from the origin point specified in args[3] and args[4]
   */
  @Test
  public void testDirectionLeftDown() {
    String[] args = {"Random", "2", "LEFT:DOWN", "5", "10", "3", "2"};
    RandomGeneration inst = new RandomGeneration(NUM_ROWS, NUM_COLS, args);
    testInstruction(inst, "2", 5, 7,
        9, 10);
  }

  /**
   * Tests that making the "direction" parameter (i.e. args[2]) invalid throws an error
   */
  @Test
  public void testInvalidDirection() {
    String[] args = {"Random", "2", "LEFT", "10", "5", "3", "2"};
    assertThrows(GenerationException.class,
        () -> new RandomGeneration(NUM_ROWS, NUM_COLS, args));
  }

}