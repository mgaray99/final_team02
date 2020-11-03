package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.GameModel;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests that KeyBinder actually changes the binding of the keys in testInputter
 */
public class KeyBinderTest extends DukeApplicationTest {

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

  private static final String UPDATE_LABEL_TEXT = "Press a key to replace ";
  private static final String BAD_KEY_TEXT = "That key is invalid!";
  private static final String UPDATE_ID = "UPDATE";

  private KeyInputter testInputter;
  private KeyBinder testBinder;
  private Text instructionsLabel;

  @Override
  public void start(Stage stage) throws KeyInputBuilderInstantiationException,
      InvalidFileException {
    testInputter = new KeyInputter(new GameModel(new GameConfiguration("")));
    testBinder = new KeyBinder();
    testBinder.updateKeyInputScreen(testInputter);
    instructionsLabel = (Text)(testBinder.lookup("#" + UPDATE_ID));
  }

  /**
   * Simulates a key press on testBinder of type code
   * @param code the code
   */
  private void keyPress(KeyCode code) {
    testBinder.getOnKeyPressed().handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.F.getChar(),
        code.getName(), code, false, false,
        false, false));
  }

  /**
   * This test makes sure that the label on the button which shows which key is mapped to the method
   * at its left updates to show the new key mapped to that method (i.e. the button says 'A'
   * initially, but then says 'B' after the user clicks 'A' and types 'B')
   */
  @Test
  public void buttonLabelChangesOnValidSwitch() {
    Button pauseButton = (Button)(testBinder.lookup("#" + DEFAULT_LEFT));
    pauseButton.fire();


  }

  /**
   *
   */
  @Test
  public void buttonLabelDoesNotChangeOnInvalidSwitch() {

  }

  /**
   * Tests that when the user presses a button to change its key label, the instructions appear to
   * tell the user what to do
   */
  @Test
  public void testInstructionLabelChangesOnButtonPush() {
    assertEquals("", instructionsLabel.getText());

    Button pauseButton = (Button)(testBinder.lookup("#" + DEFAULT_PAUSE));
    pauseButton.fire();

    assertEquals(UPDATE_LABEL_TEXT + DEFAULT_PAUSE, instructionsLabel.getText());
  }

  /**
   * Tests that when the user presses a button to change its key label and then passes in a valid
   * key, the instruction text disappears (becomes "") because it is no longer necessary to
   * provide the user with instructions
   */
  @Test
  public void testInstructionLabelResetsOnValidKeyPress() {
    Button pauseButton = (Button)(testBinder.lookup("#" + DEFAULT_PAUSE));
    pauseButton.fire();

    assertEquals(UPDATE_LABEL_TEXT + DEFAULT_PAUSE, instructionsLabel.getText());

    testBinder.getOnKeyPressed().handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.F.getChar(),
        KeyCode.F.getName(), KeyCode.F, false, false,
        false, false));

    assertEquals("", instructionsLabel.getText());
  }

  /**
   * Tests that when the user presses a button to change its key label and then passes in an invalid
   * key, the instruction text shows an error
   */
  @Test
  public void testInstructionLabelShowsErrorOnInvalidKeyPress() {
    Button pauseButton = (Button)(testBinder.lookup("#" + DEFAULT_PAUSE));
    pauseButton.fire();

    assertEquals(UPDATE_LABEL_TEXT + DEFAULT_PAUSE, instructionsLabel.getText());

    testBinder.getOnKeyPressed().handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.D.getChar(),
        KeyCode.D.getName(), KeyCode.D, false, false,
        false, false));

    assertEquals(BAD_KEY_TEXT, instructionsLabel.getText());
  }
}