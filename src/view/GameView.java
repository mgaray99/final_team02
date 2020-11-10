package view;

import controller.GameController;
import controller.KeyBinder;
import controller.KeyInputter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameModel;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.entity.IEntity;
import view.scenes.PlayGameScene;

/**
 * The view for our game - handles scene changes and updates to graphical appearance (i.e. language
 * and stylesheet
 *
 * @author Alex Lu & Edem Ahorlu
 */
public class GameView extends Application {

  public enum viewName {HOME_SCREEN, CONTROLS,
    SELECT_RESOURCE_BUNDLE, SELECT_CSS_STYLESHEET, GAME, GAMEVERSION};
  viewName lastView;
  viewName currentView;
  private Map<viewName, GameScene> mapOfScenes;

  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private static final double ANIMATION_SPEED = 1/60.0;
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String TEXTURES = "textures";

  private String configPath = "doodlejump.properties";
  private GameModel model;
  private Stage stage;
  private Timeline animation;
  private KeyInputter inputter;
  private Texturer texturer;

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

      configPath = "doodlejump.properties";
      buildModel();

      prepareAnimation();
      stage.setScene(mapOfScenes.get(viewName.HOME_SCREEN));
      stage.show();
  }

  /**
   * Prepares the model that the view will update with an animation timer and display
   */
  private void buildModel() {
    try {
      model = new GameModel(new GameConfiguration(configPath));
      inputter = new KeyInputter(model);
      texturer = new Texturer(WIDTH, HEIGHT, model.getTexturesPath(),
          (Group)(mapOfScenes.get(viewName.GAME).lookup("#" + TEXTURES)));
    } catch (InvalidFileException ife) {
      endGame();
    }
  }

  /**
   * Updates the view
   */
  private void update() {
    if (currentView.equals(viewName.GAME)) {
      model.updateGame();

      List<IEntity> entityList = model.getAllEntitiesInLevel();
      texturer.updateTextures(entityList, 15, 15);
    }
  }


  /**
   * Builds the animation functionality that will run the program
   */
  private void prepareAnimation() {
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(ANIMATION_SPEED), e -> update());
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Builds listeners for all controllers
   */
  private void listenOnControllers() {
      for (viewName view : viewName.values()) {
        GameController cont = mapOfScenes.get(view).getGameController();
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
  }

  /**
   * Handles the event of a key press
   * @param key the key that has been pressed
   */
  private void keyPressed(String key) {
    inputter.keyPressed(key);
  }

  /**
   * Handles the event of a key release
   * @param key the key that has been pressed
   */
  private void keyReleased(String key) {
    inputter.keyReleased(key);
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
        e.printStackTrace();
      }
  }

  /**
   * Switches the stylesheets of all scenes to the stylesheet referenced by name
   * @param name the name of the stylesheet (i.e. dark/light)
   */
  public void switchStylesheet(String name) {
    for (viewName view : viewName.values()) {
      mapOfScenes.get(view).updateStylesheet(name);
    }
  }

  /**
   * Updates the language bundles that writes to all of the buttons
   * @param name the name of the resourcebundle
   */
  public void switchLanguage(String name) {
   mapOfScenes.keySet().forEach(key -> mapOfScenes.get(key).updateResources(name));
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
  public void selectGameTypeScreen() {setScene(viewName.GAMEVERSION);}

  /**
   * Launches a save box to save the current state of the level in a csv file
   */
  public void saveGame() {
    ((PlayGameScene)mapOfScenes.get(viewName.GAME)).launchSave(model.getLevel());
  }

  /**
   * Ends Game
   */
  public void endGame(){
    animation.stop();
    stage.close();
  }

  /**
   * select game type
   */
  public void createGameTypeButtons(String type) {
    configPath = type.toLowerCase().replaceAll(" ", "") + PROPERTIES_EXTENSION;
    buildModel();
  }

  /**
   * Switches to the controller screen
   */
  public void switchToControlScreen() {
    KeyBinder binder = (KeyBinder)(mapOfScenes.get(viewName.CONTROLS).lookupElementInRoot(
        "KeyBinder"));
    binder.updateKeyInputScreen(inputter);
    setScene(viewName.CONTROLS);
  }

  /**
   * Switches back to the last view
   */
  public void back() { setScene(lastView); }

  /**
   * Starts the game
   */
  public void start() { setScene(viewName.GAME); }

  /**
   * Returns to the home screen
   */
  public void homeScreen() { setScene(viewName.HOME_SCREEN); }

  /**
   * Launches the application
   * @param args any arguments to be passed in
   */
  public static void beginOoga(String[] args) {
    launch(args);
  }
}
