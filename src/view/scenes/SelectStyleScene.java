package view.scenes;

import controller.GameController;
import javafx.scene.Group;
import view.GameScene;
import view.GameView.viewName;

public class SelectStyleScene extends GameScene {
  private static final String CSS_FOLDERPATH = "./src/resources/cssstylesheets";
  private static final String ID = "SELECT_CSS_STYLESHEET";
  private static final String CSS_EXTENSION = ".css";
  private static final String BUTTON_FOLDERPATH_SLASH = "resources/buttons/";

  public SelectStyleScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.txt");

    buildOptionsSelectorFromFolderForController(CSS_FOLDERPATH, CSS_EXTENSION,
        "switchStylesheet");
  }
}