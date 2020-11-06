package view.scenes;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.GameSaver;
import model.Level;
import view.GameScene;

public class PlayGameScene extends GameScene {
  private static final String ID = "GAME";
  private static final String TEXTURES = "textures";
  private static final String BUTTON_FOLDERPATH_SLASH = "resources/buttons/";

  private static final String SAVE_INSTRUCTIONS = "Please input a filename and press ENTER";
  private static final String SAVE_ERROR = "Please input a valid filename!";

  private static final String CSV_EXTENSION = ".csv";
  private static final String SAVE_FILEPATH = "data/saves/";
  private Level currentLevel;

  private TextField saveField;

  public PlayGameScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.txt");

    addTexturesGroup();
    buildSavingFunctionality();
  }

  /**
   * Makes the group node that will hold the textures
   */
  private void addTexturesGroup() {
    Group textures = new Group();
    textures.setId(TEXTURES);
    addElementToRoot(textures);
  }

  /**
   * Enables the TextField that will take in the file name
   */
  private void buildSavingFunctionality() {
    saveField = new TextField();

    saveField.setMinWidth(WIDTH/8);
    saveField.setMinHeight(HEIGHT/20);
    saveField.setLayoutX(WIDTH/2 - saveField.getMinWidth());
    saveField.setLayoutY(HEIGHT/2 - saveField.getMinHeight());
    saveField.setVisible(false);
    saveField.setOnKeyPressed(event -> handleTextFieldPress(event));

    addElementToRoot(saveField);
  }

  /**
   * Saves the game
   */
  public void launchSave(Level level) {
    currentLevel = level;
    updateErrorText(SAVE_INSTRUCTIONS);
    saveField.setVisible(true);
  }

  /**
   * Handles the event that a key was pressed in the textfield
   * @param event
   */
  private void handleTextFieldPress(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ENTER)) {
        attemptSave();
    }
  }

  /**
   * Tries to save using the file name given
   */
  private void attemptSave() {
    if(checkIsValidText(saveField.getText())) {
      finalizeSave();
    }
    else {
      saveField.clear();
      updateErrorText(SAVE_ERROR);
    }
  }

  /**
   * Checks to make sure text is longer than 0 characters and does not contain a space for its
   * final character
   * @param text the text to be checked
   * @return a boolean revealing whether or not the text is valid
   */
  private boolean checkIsValidText(String text) {
    return text.length() > 0 && text.charAt(text.length() - 1) != ' ';
  }

  /**
   * Finishes the saving
   */
  private void finalizeSave() {
    GameSaver saver = new GameSaver(currentLevel);
    saver.writeNewLevelCSVFile(SAVE_FILEPATH + saveField.getText() + CSV_EXTENSION);

    saveField.clear();
    saveField.setVisible(false);
    hideErrorText();
  }
}
