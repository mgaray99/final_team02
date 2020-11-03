package view;

import controller.ImageBuilder;
import controller.KeyBinder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import controller.GameController;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import view.GameView.viewName;

/**
 * The purpose of this class is to build the map of viewName (i.e. HOME_SCREEN,
 * SELECT_CSS_STYLESHEET etc.) to fully prepared GameScene objects that contain Controllers which
 * contain Buttons and OptionSelectors
 *
 * @author Alex Lu & Edem Ahorlu
 */
public class GameSceneMap {
  private Map<viewName,GameScene> mapOfScenes;
  private static double WIDTH = 800;
  private static double HEIGHT = 800;
  private static final String LANGUAGE_FOLDERPATH = "./src/resources/resourcebundles";
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String CSS_FOLDERPATH = "./src/resources/cssstylesheets";
  private static final String BUTTON_FOLDERPATH_SLASH = "resources/buttons/";
  private static final String CSS_EXTENSION = ".css";
  private static final String[] GAME_TYPES = {"Super Mario", "Flappy Bird", "Doodle Jump", "Penu"};

  public GameSceneMap() {
  }

  /**
   * Builds the map containing each of the scenes that we will display during our project
   * First instantiates the "mapOfScenes" variable as a HashMap.
   *
   * Then for each possible "view" we have declared in the enum viewName (i.e. HOME_SCREEN, etc.)
   * builds a "GameScene" object corresponding to that "view" that will serve as the view's
   * scene
   *
   */
  public void buildMapOfScenes() {
    mapOfScenes = new HashMap<>();

    for (GameView.viewName view : GameView.viewName.values()) {
      mapOfScenes.put(view, new GameScene(new Group(), view.toString(), WIDTH, HEIGHT));
      mapOfScenes.get(view).setGameController(new GameController());
    }

    addImagesToControllers();
    buildOptionsSelectorsForControllers();
    addButtonsToControllers();
    addKeyBinders();

  }

  /**
   * Builds the OptionsSelectors for specific scenes
   */
  private void buildOptionsSelectorsForControllers() {
    try {
      mapOfScenes.get(viewName.SELECT_CSS_STYLESHEET).buildOptionsSelectorFromFolderForController(
          CSS_FOLDERPATH, CSS_EXTENSION, "switchStylesheet");
      mapOfScenes.get(viewName.SELECT_RESOURCE_BUNDLE).buildOptionsSelectorFromFolderForController(
          LANGUAGE_FOLDERPATH, PROPERTIES_EXTENSION, "switchLanguage");
      mapOfScenes.get(viewName.GAMEVERSION).buildOptionsSelectorFromListForController(
              Arrays.asList(GAME_TYPES), "createGameTypeButtons");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a key binder to the scene in question
   */
  private void addKeyBinders() {
    KeyBinder key = new KeyBinder();
    key.setId(key.getClass().getSimpleName());
    mapOfScenes.get(viewName.CONTROLS).addElementToRoot(key);
  }


  /**
   * Adds a set of buttons to each scene represents by view
   */
  private void addButtonsToControllers() {
    for (viewName view : GameView.viewName.values()) {
      mapOfScenes.get(view).addButtonsToControllerFromFile(BUTTON_FOLDERPATH_SLASH +
          view.toString().toLowerCase()+"buttons.txt");
    }
  }


  private void addImagesToControllers() {
    Node background = mapOfScenes.get(viewName.HOME_SCREEN).lookupElementInRoot("background");
    mapOfScenes.get(viewName.HOME_SCREEN).removeElementFromRoot(background);
    ImageBuilder image = new ImageBuilder(WIDTH, HEIGHT, "resources/images/home_screenimages.txt");
    for (ImageView view: image.getFoundImages()) {
    mapOfScenes.get(viewName.HOME_SCREEN).addElementToRoot(view);
    view.toBack();
    }
  }




  /**
   * Returns the map of viewName to GameScene
   * @return mapOfScenes
   */
  public Map<viewName, GameScene> getMapOfScenes() {
    return mapOfScenes;
  }

}
