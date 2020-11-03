package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class KeyBinder extends Group {

    private KeyInputter inputter;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;
    private static final double START_VISIBLE_Y = HEIGHT / 8;
    private static final double END_VISIBLE_Y = 7 * HEIGHT / 8;
    private static final double VISIBLE_Y = END_VISIBLE_Y - START_VISIBLE_Y;
    private static final double COLUMN1X = WIDTH /4;
    private static final double COLUMN2X = 3 * WIDTH /4;
    private static final double CENTERX= WIDTH / 2;
    private static final double UPDATE_LABEL_Y = HEIGHT/16;
    private static final String UPDATE_LABEL_TEXT = "Press a key to replace ";
    private static final String BAD_KEY_TEXT = "That key is invalid!";
    private boolean isUpdatingKey;
    private String currentKeyBeingUpdated;
    private Text updateLabel;

    public KeyBinder() {
        isUpdatingKey = false;
        currentKeyBeingUpdated = "";

        setOnKeyPressed(event -> handleKey(event));
        buildUpdateLabel();
    }

  /**
   * Builds the update label, inserts it into this root node and makes it invisible
   */
  private void buildUpdateLabel() {
      updateLabel = new Text(UPDATE_LABEL_TEXT);
      updateLabel.setLayoutX(CENTERX - updateLabel.getLayoutBounds().getWidth());
      updateLabel.setLayoutY(UPDATE_LABEL_Y - updateLabel.getLayoutBounds().getHeight());
    }

  /**
   * Updates the the key input screen
   *
   * @param in the KeyInputter whose data will fill this KeyInputBinder
   */
  public void updateKeyInputScreen(KeyInputter in) {
        prepareUpdate(in);
        fillScreenWithKeyMap();
    }

  /**
   * Prepares the screen to be filled with the key map
   *
   * @param in the KeyInputter whose data will fill this KeyInputBinder
   */
  private void prepareUpdate(KeyInputter in) {
    inputter = in;
    getChildren().clear();
    getChildren().add(updateLabel);
  }

  /**
   * Fills the screen with the key -> method map
   */
  private void fillScreenWithKeyMap() {
      List<Pair<String, String>> keyMethodPairs = new ArrayList<>();
      keyMethodPairs.addAll(inputter.getKeyMethodPairings());

      for (int index = 0; index < keyMethodPairs.size(); index+=1) {
        Pair<String, String> pair = keyMethodPairs.get(index);
        showKeyMethodPair(pair, 1.0 * index / keyMethodPairs.size());
      }
    }

  /**
   * Shows the key -> method pair on the screen - builds the Button representing the key and the
   * Text representing the method that the key corresponds to and when the button is pushed,
   * calls the enableUpdate method, passing in its id as a parameter to help identify that that
   * button called the method
   *
   * @param pair the pair <String, String> of <key, method>
   * @param offset a double multiplier that helps to determine the y coordinate of the method and
   *               keyButton in the parent
   */
  private void showKeyMethodPair(Pair<String, String> pair, double offset) {
        Text method = new Text(pair.getValue());
        method.setLayoutX(COLUMN1X);
        method.setLayoutY(START_VISIBLE_Y + VISIBLE_Y * offset);

        Button keyButton = new Button(pair.getKey());
        keyButton.setId(pair.getKey());
        keyButton.setOnAction(e -> enableUpdate(keyButton.getId()));
        keyButton.setLayoutX(COLUMN2X);
        keyButton.setLayoutY(START_VISIBLE_Y + VISIBLE_Y * offset);

        getChildren().addAll(method, keyButton);
    }

  /**
   * Enables updating of the key -> method pairing - called after the user clicks on a button to
   * update a method to a new key. Identifies which key is to be changed (currentKey) and updates
   * the updateLabel to say something new (i.e. update key 'B')
   *
   * @param currentKey the String representation of the current key being updated
   */
  private void enableUpdate(String currentKey) {
        isUpdatingKey = true;
        currentKeyBeingUpdated = currentKey;
        updateLabel.setText(UPDATE_LABEL_TEXT + currentKey);
    }

  /**
   * Handles the event where a key has been pressed - sets the isUpdatingKey variable to false,
   * clears the updateLabel (i.e. you can't see updating directions any more), and attempts to
   * update the key binding using user input in updateKeyBinding()
   *
   * @param event the KeyEvent that has occurred
   */
  private void handleKey(KeyEvent event) {
      if (isUpdatingKey) {
        isUpdatingKey = false;
        updateLabel.setText("");
        updateKeyBinding(event.getCode().toString());
      }
    }

  /**
   * Tries to replace the key currentlyBeingUpdated with the key userKey - first check the
   * inputter to make sure that userKey is a viable replacement for currentlyBeingUpdated, then
   * execute replace
   *
   * @param userKey the key replacing currentlyBeingUpdated
   */
  private void updateKeyBinding(String userKey) {
    if (inputter.isValidKey(userKey)) {
      Button changedButton = (Button)lookup("#" + currentKeyBeingUpdated);
      changedButton.setText(userKey);
      changedButton.setId(userKey);
      inputter.swapKeyInput(currentKeyBeingUpdated, userKey);
    }
    else {
      updateLabel.setText(BAD_KEY_TEXT);
    }
  }
}
