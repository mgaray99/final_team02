package view.scenes;

import controller.GameController;
import javafx.scene.Group;
import view.GameScene;
import view.GameView.viewName;

public class PlayGameScene extends GameScene {
  private static final String ID = "GAME";
  private static final String TEXTURES = "textures";
  private static final String BUTTON_FOLDERPATH_SLASH = "resources/buttons/";

  public PlayGameScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.txt");

    addTexturesGroup();

  }

  /**
   * Makes the group node that will hold the textures
   */
  private void addTexturesGroup() {
    Group textures = new Group();
    textures.setId(TEXTURES);
    addElementToRoot(textures);
  }
}
