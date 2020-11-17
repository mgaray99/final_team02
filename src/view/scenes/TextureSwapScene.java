package view.scenes;

import javafx.scene.Group;
import view.GameScene;

public class TextureSwapScene extends GameScene {
  private static final String ID = "TEXTURE_SWAP";
  private static final String TEXTURES_FOLDERPATH = "./src/resources/images/texturefiles";
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";

  public TextureSwapScene(Group myRoot, double width, double height) {
    super(myRoot, ID, width, height);

    addButtonsToControllerFromFile(
        BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.xml");

    buildOptionsSelectorFromFolderForController(
        TEXTURES_FOLDERPATH, PROPERTIES_EXTENSION, "switchTextures");
  }
}