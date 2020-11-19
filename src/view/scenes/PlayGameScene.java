package view.scenes;

import java.util.Arrays;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.GameSaver;
import api.model.score.IScoreTuple;
import api.model.score.IGameLeaderboard;
import api.model.IGameSaver;
import model.Level;
import model.score.GameLeaderboard;
import model.score.ScoreTuple;
import view.GameScene;
import api.view.scenes.IPlayGameScene;

public class PlayGameScene extends GameScene implements IPlayGameScene {
  private static final String ID = "GAME";
  private static final String TEXTFIELD_ID = "TEXTFIELD";
  private static final String SCOREFIELD_ID = "SCOREFIELD";
  private static final String TEXTURES = "textures";
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";

  private static final String SAVE_INSTRUCTIONS = "SaveInstructions";
  private static final String CSV_EXTENSION = ".csv";
  private static final String SAVE_FILEPATH = "data/saves/";

  private static final String[] bannedCharacters = {"\n", "/", ".", ",","\\"};

  private Text scoreLabel;
  private Level currentLevel;
  private String scorePath;
  private TextField scoreField;
  private TextField saveField;

  public PlayGameScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.xml");

    addTexturesGroup();
    buildSavingFunctionality();
    buildScoreField();
    makeScoreText();
  }

  /**
   * Builds the text label to display score
   */
  @Override
  public void makeScoreText() {
    scoreLabel = new Text();
    scoreLabel.setText("");
    scoreLabel.setLayoutX(WIDTH / 2 - scoreLabel.getLayoutBounds().getWidth() / 2);
    scoreLabel.setLayoutY(HEIGHT/20);
    scoreLabel.setId("errorStyle");
    addElementToRoot(scoreLabel);
  }

  /**
   * Updates and recenters the score text
   * @param update the new text to be displayed
   */
  @Override
  public void updateScoreText(String update) {
    scoreLabel.setText(update);
    scoreLabel.setLayoutX(WIDTH / 2 - scoreLabel.getLayoutBounds().getWidth() / 2);

  }

  /**
   * Makes the group node that will hold the textures
   */
  @Override
  public void addTexturesGroup() {
    Group textures = new Group();
    textures.setId(TEXTURES);
    addElementToRoot(textures);
    textures.toBack();
    lookupElementInRoot(BACKGROUND).toBack();
  }

  /**
   * Enables the TextField that will take in the file name
   */
  @Override
  public void buildSavingFunctionality() {
    saveField = new TextField();
    buildTextField(saveField);

    saveField.setId(TEXTFIELD_ID);
    saveField.setOnKeyPressed(event -> handleTextFieldPress(event));

    addElementToRoot(saveField);
  }

  /**
   * Pins a textfield to the center of the screen and makes it invisible
   * @param field the field in question
   */
  @Override
  public void buildTextField(TextField field) {
    field.setMinWidth(WIDTH/8);
    field.setMinHeight(HEIGHT/20);
    field.setLayoutX(WIDTH/2 - field.getMinWidth());
    field.setLayoutY(HEIGHT/2 - field.getMinHeight());
    field.setVisible(false);
    field.toFront();
  }

  /**
   * Builds the scoreField instance variable
   */
  @Override
  public void buildScoreField() {
    scoreField = new TextField();
    buildTextField(scoreField);
    scoreField.setId(SCOREFIELD_ID);
    scoreField.setOnKeyPressed(event -> attemptScoreSave(event));

    addElementToRoot(scoreField);
  }

  /**
   * Saves the game
   */
  @Override
  public void launchSave(Level level) {
    currentLevel = level;
    String saveInstructions = getValueFromBundle(SAVE_INSTRUCTIONS);
    updateErrorText(saveInstructions);
    saveField.setVisible(true);
    currentLevel.setIsSaving(true);
  }

  /**
   * Handles the event that a key was pressed in the textfield
   * @param event the key event that has occurred
   */
  @Override
  public void handleTextFieldPress(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ENTER)) {
        attemptSave();
    }
  }

  /**
   * Tries to save using the file name given
   */
  @Override
  public void attemptSave() {
    if(checkIsValidText(saveField.getText())) {
      finalizeSave();
    }
    else {
      saveField.clear();
      updateErrorText(getValueFromBundle("SAVE_ERROR"));
    }
  }

  /**
   * Checks to make sure text is longer than 0 characters and does not contain a space for its
   * final character
   * @param text the text to be checked
   * @return a boolean revealing whether or not the text is valid
   */
  @Override
  public boolean checkIsValidText(String text) {
    List<String> bannedList= Arrays.asList(bannedCharacters);
    boolean containsBannedChar = false;

    for (String bannedChar : bannedList) {
      if (text.indexOf(bannedChar) >= 0) {
        containsBannedChar = true;
      }
    }

    return text.length() > 0 && text.charAt(text.length() - 1) != ' ' && !containsBannedChar;
  }

  /**
   * Finishes the saving
   */
  @Override
  public void finalizeSave() {
    IGameSaver saver = new GameSaver(currentLevel);
    saver.writeNewLevelCSVFile(SAVE_FILEPATH + saveField.getText() + CSV_EXTENSION);

    clearFields();
    currentLevel.setIsSaving(false);
  }

  /**
   * Attempts to save the score
   * @param key the key that was pressed
   */
  @Override
  public void attemptScoreSave(KeyEvent key) {
    if (key.getCode().equals(KeyCode.ENTER) && checkIsValidText(scoreField.getText())) {
      finalizeScoreSave();
    }
    else if (key.getCode().equals(KeyCode.ENTER)){
      scoreField.clear();
      updateErrorText(getValueFromBundle("SCORE_ERROR"));
    }
  }

  /**
   * Finishing saving the score
   */
  @Override
  public void finalizeScoreSave() {
    try {
      IGameLeaderboard leaderboard = new GameLeaderboard(scorePath);
      IScoreTuple tuple = new ScoreTuple(scoreField.getText(), currentLevel.getScore());
      leaderboard.addScoreTuple(tuple);
    }
    catch (Exception e) {
      updateErrorText(getValueFromBundle("FINAL_SCORE_ERROR"));
    }

    clearFields();
    currentLevel.setIsSaving(false);
    currentLevel.reinitialize();
    pauseLevel();
    updateErrorText(getValueFromBundle("RestartInstructions"));
  }

  /**
   * Allows the user to attempt to save
   *
   * @param path the String path leading to the high score file in which to save
   *             high scores
   * @param level the level that has just been lost
   */
  @Override
  public void inputScore(String path, Level level) {
    scorePath = path;
    currentLevel = level;
    updateErrorText(getValueFromBundle("SCORE_INSTRUCTIONS"));
    scoreField.setVisible(true);
    currentLevel.setIsSaving(true);
  }

  /**
   * Clears all of the text fields and the error labels and resumes the game
   */
  @Override
  public void clearFields() {
    scoreField.setVisible(false);
    scoreField.clear();

    saveField.setVisible(false);
    saveField.clear();

    updateErrorText("");
    hideErrorText();

    currentLevel.setIsSaving(false);
  }

  /**
   * Pauses the level currentLevel
   */
  @Override
  public void pauseLevel() {
    currentLevel.getKeyPressFunctions().pauseGame();
  }
}
