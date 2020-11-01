package view;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import controller.GameController;
import view.GameView.viewName;

/**
 * The purpose of this class is to build the map of viewName (i.e. HOME_SCREEN,
 * SELECT_CSS_STYLESHEET etc.) to fully prepared GameScene objects that contain Controllers which
 * contain Buttons and OptionSelectors
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
   * @param gameView the GameView that we're building the map for
   */
  public void buildMapOfScenes(GameView gameView) {
    mapOfScenes = new HashMap<>();

    for (GameView.viewName view : GameView.viewName.values()) {
      mapOfScenes.put(view, new GameScene(new Group(), WIDTH, HEIGHT));
      mapOfScenes.get(view).setController(new GameController(gameView));
    }

    buildOptionsSelectorsForControllers();
    addButtonsToControllers();
  }

  /**
   * Builds the OptionsSelectors for specific scenes
   */
  private void buildOptionsSelectorsForControllers() {
    try {
      mapOfScenes.get(viewName.SELECT_CSS_STYLESHEET).buildOptionsSelectorForController(
          CSS_FOLDERPATH, CSS_EXTENSION,
          getOptionSelectorMethodFromString("switchStylesheet"));
      mapOfScenes.get(viewName.SELECT_RESOURCE_BUNDLE).buildOptionsSelectorForController(
          LANGUAGE_FOLDERPATH, PROPERTIES_EXTENSION,
          getOptionSelectorMethodFromString("updateLanguage"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a method based on a String input
   * @param methodName the String representation of the method name
   * @return the method in GameView corresponding to methodName
   * @throws NoSuchMethodException
   */
  private Method getOptionSelectorMethodFromString(String methodName) throws NoSuchMethodException{
    return GameView.class.getDeclaredMethod(methodName, String.class);
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

  /**
   * Returns the map of viewName to GameScene
   * @return mapOfScenes
   */
  public Map<viewName, GameScene> getMapOfScenes() {
    return mapOfScenes;
  }

}
