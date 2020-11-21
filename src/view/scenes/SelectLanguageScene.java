package view.scenes;

import javafx.scene.Group;
import view.GameScene;

/**
 * builds the scene that allows the user to select different languages
 *
 * @author Edem Ahorlu
 */


public class SelectLanguageScene extends GameScene {
  private static final String ID = "SELECT_RESOURCE_BUNDLE";
  private static final String LANGUAGE_FOLDERPATH = "./src/resources/resourcebundles";
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";

  public SelectLanguageScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.xml");

    buildOptionsSelectorFromFolderForController(
        LANGUAGE_FOLDERPATH, PROPERTIES_EXTENSION, "switchLanguage");
  }
}
