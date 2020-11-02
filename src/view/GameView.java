package view;

import controller.GameController;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameView extends Application {

  public enum viewName {HOME_SCREEN, SELECT_RESOURCE_BUNDLE, SELECT_CSS_STYLESHEET, GAME, GAMEVERSION};
  viewName lastView;
  viewName currentView;

  private Map<viewName, GameScene> mapOfScenes;

  private static final double ANIMATION_SPEED = 1/60.0;
  private static final String CSS_EXTENSION = ".css";
  private Stage stage;

  /**
   * Begins our view, (i.e. builds the scene and group objects responsible for showing our project)
   * @param sta the main stage of the program
   */
  public void start(Stage sta) {
    stage = sta;
    lastView = viewName.HOME_SCREEN;
    currentView = viewName.HOME_SCREEN;
    GameSceneMap map = new GameSceneMap();
    map.buildMapOfScenes();
    mapOfScenes = map.getMapOfScenes();
    listenOnControllers();

    stage.setScene(mapOfScenes.get(viewName.HOME_SCREEN));
    stage.show();

    prepareAnimation();
  }

  /**
   * Updates the view given that time has passed
   * @param timeElapsed the amount of time that has passed since the last update
   */
  private void update(double timeElapsed) {
  }


  /**
   * Builds the animation functionality that will run the program
   */
  private void prepareAnimation() {
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(ANIMATION_SPEED), e -> update(ANIMATION_SPEED));
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Builds listeners for all controllers
   */
  private void listenOnControllers() {
      for (viewName view : viewName.values()) {
        GameController cont = mapOfScenes.get(view).getController();
        cont.addEventHandler(EventType.ROOT, event -> handleControllerEvent(cont, event));
      }
  }

  /**
   * Handles an event in the controller by screening it and then passing it to performReflection
   * @param cont the GameController that fired the event
   * @param event the Event that has occured
   */
  private void handleControllerEvent(GameController cont, Event event) {
    if (event.getEventType().getName().equals("controller")) {
      List<String> reflectionArgs = new ArrayList<>();
      reflectionArgs.addAll(cont.getBuffer());
      performReflection(reflectionArgs);
    }
    else if (event.getEventType().getName().equals("key")) {
    }
  }

  /**
   * Reflectively calls the method as specified in reflectionArgs
   * @param reflectionArgs - either a 1 or 2 String List containing either a method with no
   *                       parameters or a method with one String parameter
   */
  private void performReflection(List<String> reflectionArgs) {
      try {
        if (reflectionArgs.size() == 1) {
          Method method = this.getClass().getDeclaredMethod(reflectionArgs.get(0));
          method.invoke(this);
        } else if (reflectionArgs.size() == 2) {
          Method method = this.getClass().getDeclaredMethod(reflectionArgs.get(0), String.class);
          method.invoke(this, reflectionArgs.get(1));
        }
      }
      catch (Exception e) {
        System.out.println("bad reflection");
      }
  }

  /**
   * Switches the stylesheets of all scenes to the stylesheet referenced by name
   * @param name the name of the stylesheet (i.e. dark/light)
   */
  public void switchStylesheet(String name) {
    String stylesheetPath = "resources/cssstylesheets/";
    for (viewName view : viewName.values()) {
      mapOfScenes.get(view).getStylesheets().clear();
      mapOfScenes.get(view).getStylesheets().add(stylesheetPath + name + CSS_EXTENSION);
    }
  }

  /**
   * Updates the language bundles that writes to all of the buttons
   * @param name the name of the resourcebundle
   */
  public void updateLanguage(String name) {
    String languagePath = "resources/resourcebundles";
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
  public void switchToSelectLanguageScreen() {setScene(viewName.SELECT_RESOURCE_BUNDLE);
  }

  /**
   * switches to Select Game Type Screen
   */

  public void selectGameType() {setScene(viewName.GAMEVERSION);}

  /**
   * Ends Game
   */

  public void endGame(){}



  /**
   * Switches back to the last view
   */
  public void back() {
    setScene(lastView);
  }

  /**
   * Starts the game
   */
  public void start() { setScene(viewName.GAME); }

  /**
   * Launches the application
   * @param args any arguments to be passed in
   */
  public static void beginOoga(String[] args) {
    launch(args);
  }
}
