package view.scenes;

import java.util.Arrays;
import javafx.scene.Group;
import view.GameScene;

public class SelectGameScene extends GameScene {
  private static final String ID = "GAMEVERSION";
  private static final String[] GAME_TYPES = {"Super Mario", "Flappy Bird", "Doodle Jump"};
  private static final String BUTTON_FOLDERPATH_SLASH = "resources/buttons/";

  public SelectGameScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.txt");

    buildOptionsSelectorFromListForController(
        Arrays.asList(GAME_TYPES), "createGameTypeButtons");
  }
}
