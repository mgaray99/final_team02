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
import view.scenes.ControlsScene;
import view.scenes.MenuScene;
import view.scenes.PlayGameScene;
import view.scenes.SelectGameScene;
import view.scenes.SelectLanguageScene;
import view.scenes.SelectStyleScene;

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
    mapOfScenes.put(viewName.GAME,
        new PlayGameScene(new Group(), WIDTH, HEIGHT));
    mapOfScenes.put(viewName.HOME_SCREEN,
        new MenuScene(new Group(), WIDTH, HEIGHT));
    mapOfScenes.put(viewName.SELECT_RESOURCE_BUNDLE,
        new SelectLanguageScene(new Group(), WIDTH, HEIGHT));
    mapOfScenes.put(viewName.SELECT_CSS_STYLESHEET,
        new SelectStyleScene(new Group(), WIDTH, HEIGHT));
    mapOfScenes.put(viewName.CONTROLS,
        new ControlsScene(new Group(), WIDTH, HEIGHT));
    mapOfScenes.put(viewName.GAMEVERSION,
        new SelectGameScene(new Group(), WIDTH, HEIGHT));
  }


  /**
   * Returns the map of viewName to GameScene
   * @return mapOfScenes
   */
  public Map<viewName, GameScene> getMapOfScenes() {
    return mapOfScenes;
  }

}
