package view.scenes;

import controller.GameController;
import controller.ImageBuilder;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import view.GameScene;

public class MenuScene extends GameScene {

    private static final String ID = "HOME_SCREEN";
    private static final String BUTTON_FOLDERPATH_SLASH = "resources/buttons/";

    public MenuScene(Group myRoot, double width, double height) {
      super(myRoot, ID, width, height);

      addButtonsToControllerFromFile(
          BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.txt");

      addImagesToHomeScreen();
    }

  /**
   * Adds images to the home screen
   */
  private void addImagesToHomeScreen() {
    Node background = lookupElementInRoot("background");
    removeElementFromRoot(background);
    ImageBuilder image = new ImageBuilder(WIDTH, HEIGHT,
        "resources/images/home_screenimages.txt");

    for (ImageView view: image.getFoundImages()) {
      addElementToRoot(view);
      view.toBack();
    }
  }
}
