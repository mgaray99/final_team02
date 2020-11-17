package view.scenes;

import controller.KeyBinder;
import javafx.scene.Group;
import view.GameScene;

public class ControlsScene extends GameScene {

  private static final String ID = "CONTROLS";
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";
  private KeyBinder key;

  public ControlsScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.xml");

    addKeyBinders();
  }

  /**
   * Adds a key binder to this scene
   */
  private void addKeyBinders() {
    key = new KeyBinder(bundle);
    key.setId(key.getClass().getSimpleName());
    addElementToRoot(key);
  }

  /**
   * Updates the resource bundle displaying text for each scene
   * @param name the name of the resource bundle
   */
  @Override
  public void updateResources(String name) {
    super.updateResources(name);
    key.setBundle(bundle);
  }
}
