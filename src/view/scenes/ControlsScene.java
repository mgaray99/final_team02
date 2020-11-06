package view.scenes;

import controller.GameController;
import controller.KeyBinder;
import javafx.scene.Group;
import view.GameScene;
import view.GameView.viewName;

public class ControlsScene extends GameScene {

  private static final String ID = "CONTROLS";
  private static final String BUTTON_FOLDERPATH_SLASH = "resources/buttons/";

  public ControlsScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.txt");

    addKeyBinders();
  }

  /**
   * Adds a key binder to this scene
   */
  private void addKeyBinders() {
    KeyBinder key = new KeyBinder();
    key.setId(key.getClass().getSimpleName());
    addElementToRoot(key);
  }
}