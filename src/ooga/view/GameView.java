package ooga.view;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.text.html.CSS;
import ooga.controller.GameController;

public class GameView extends Application {

  enum viewName {HOME_SCREEN, SELECT_RESOURCE_BUNDLE, SELECT_CSS_STYLESHEET};
  viewName lastView;
  viewName currentView;

  private Map<viewName, GameScene> mapOfScenes;

  private static final double ANIMATION_SPEED = 1/60.0;
  private static final String CSS_EXTENSION = ".css";
  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private Stage stage;

  /**
   * Begins our view, (i.e. builds the scene and group objects responsible for showing our project)
   * @param s the main stage of the program
   */
  public void start(Stage sta) {
    stage = sta;
    lastView = viewName.HOME_SCREEN;
    currentView = viewName.HOME_SCREEN;
    GameSceneMap map = new GameSceneMap();
    map.buildMapOfScenes(this);
    mapOfScenes = map.getMapOfScenes();

    stage.setScene(mapOfScenes.get(viewName.HOME_SCREEN));
    stage.show();

    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(ANIMATION_SPEED), e -> update(ANIMATION_SPEED));
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Updates the view given that time has passed
   * @param timeElapsed the amount of time that has passed since the last update
   */
  private void update(double timeElapsed) {

  }

  /**
   * Switches the stylesheets of all scenes to the stylesheet referenced by name
   * @param name the name of the stylesheet (i.e. dark/light)
   */
  public void switchStylesheet(String name) {
    String stylesheetPath = "ooga/resources/cssstylesheets/";
    for (viewName view : viewName.values()) {
      mapOfScenes.get(view).getStylesheets().clear();
      mapOfScenes.get(view).getStylesheets().add(stylesheetPath + name + CSS_EXTENSION);
    }
  }

  public void updateLanguage(String name) {
    String languagePath = "ooga/resources/resourcebundles";
  }

  /**
   * Switches the scene to the viewName indexed by view
   * @param view the viewName (i.e. HOME_SCREEN, SELECT_CSS_STYLESHEET) to become the new scene
   */
  private void setScene(viewName view) {
    lastView = currentView;
    stage.setScene(mapOfScenes.get(view));
    currentView = view;
  }

  /**
   * Switches to the menu screen
   */
  public void switchToHomeScreen() {
    setScene(viewName.HOME_SCREEN);
  }

  /**
   * Switches to Css Stylesheet Selection Screen
   */
  public void switchToSelectCssStylesheetScreen() {
    setScene(viewName.SELECT_CSS_STYLESHEET);
  }

  /**
   * Switches to Select Language Screen
   */
  public void switchToSelectLanguageScreen() {
    setScene(viewName.SELECT_RESOURCE_BUNDLE);
  }

  /**
   * Switches back to the last view
   */
  public void back() {
    setScene(lastView);
  }

  /**
   * Starts the game
   */
  public void start() {}

  /**
   * Launches the application
   * @param args any arguments to be passed in
   */
  public static void beginOoga(String[] args) {
    launch(args);
  }
}
