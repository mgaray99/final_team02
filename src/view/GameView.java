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
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameModel;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.entity.IEntity;
import view.scenes.ControlsScene;
import view.scenes.MenuScene;
import view.scenes.PlayGameScene;
import view.scenes.SelectGameScene;
import view.scenes.SelectLanguageScene;
import view.scenes.SelectStyleScene;

/**
 * The view for our game - handles scene changes and updates to graphical appearance (i.e. language
 * and stylesheet
 *
 * @author Alex Lu & Edem Ahorlu
 */
public class GameView extends Application {

  private PlayGameScene playGameScene;
  private MenuScene menuScene;
  private SelectLanguageScene selectLanguageScene;
  private SelectStyleScene selectStyleScene;
  private ControlsScene controlsScene;
  private SelectGameScene selectGameScene;
  private List<GameScene> gameScenes;


  private Scene currentScene;
  private Scene lastScene;

  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;
  private static final double ANIMATION_SPEED = 1/60.0;
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String TEXTURES = "textures";
  private static final String SECRET_CONFIG_PATH = "/secret/masteregg.properties";

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

      buildScenes();
      buildScenesList();
      listenOnControllers();

      lastScene = menuScene;
      currentScene = menuScene;

      configPath = "doodlejump.properties";
      buildModel();

      prepareAnimation();
      stage.setScene(menuScene);
      stage.show();
  }

  /**
   * Builds the scenes
   */
  private void buildScenes() {
    playGameScene = new PlayGameScene(new Group(), WIDTH, HEIGHT);
    menuScene = new MenuScene(new Group(), WIDTH, HEIGHT);
    selectLanguageScene =
        new SelectLanguageScene(new Group(), WIDTH, HEIGHT);
    selectStyleScene = new SelectStyleScene(new Group(), WIDTH, HEIGHT);
    controlsScene = new ControlsScene(new Group(), WIDTH, HEIGHT);
    selectGameScene = new SelectGameScene(new Group(), WIDTH, HEIGHT);
  }

  /**
   * Builds the list of game scenes used in this view
   */
  private void buildScenesList() {
    gameScenes = new ArrayList<>();
    gameScenes.add(menuScene);
    gameScenes.add(playGameScene);
    gameScenes.add(controlsScene);
    gameScenes.add(selectLanguageScene);
    gameScenes.add(selectStyleScene);
    gameScenes.add(selectGameScene);
  }

  /**
   * Prepares the model that the view will update with an animation timer and display
   */
  private void buildModel() {
    try {
      model = new GameModel(new GameConfiguration(configPath));
      inputter = new KeyInputter(model);
      texturer = new Texturer(WIDTH, HEIGHT, model.getTexturesPath(),
          (Group)playGameScene.lookup("#" + TEXTURES));
    } catch (InvalidFileException ife) {
      endGame();
    }
  }

  /**
   * Updates the view
   */
  private void update() {
    if (currentScene.equals(playGameScene)) {
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
      for (GameScene scene : gameScenes) {
        GameController cont = scene.getGameController();
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
  void keyPressed(String key) {
    inputter.keyPressed(key);
    if (currentScene.equals(menuScene) && key.equals("T")) {
      configPath = SECRET_CONFIG_PATH;
      buildModel();
    }
  }

  /**
   * Handles the event of a key release
   * @param key the key that has been pressed
   */
  void keyReleased(String key) {
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
      }
  }

  /**
   * Switches the stylesheets of all scenes to the stylesheet referenced by name
   * @param name the name of the stylesheet (i.e. dark/light)
   */
  public void switchStylesheet(String name) {
    for (GameScene scene : gameScenes) {
      scene.updateStylesheet(name);
    }
  }

  /**
   * Updates the language bundles that writes to all of the buttons
   * @param name the name of the resourcebundle
   */
  public void switchLanguage(String name) {
    for (GameScene scene : gameScenes) {
      scene.updateResources(name);
    }
  }

  /**
   * Switches the scene to the viewName indexed by view
   * @param scene the scene to become the new scene
   */
  private void setScene(Scene scene) {
    lastScene = currentScene;
    stage.setScene(scene);
    currentScene = scene;
  }

  /**
   * Switches to the menu screen
   */
  public void switchToHomeScreen() {
    setScene(menuScene);
  }

  /**
   * Switches to Css Stylesheet Selection Screen
   */
  public void switchToSelectCssStylesheetScreen() {
    setScene(selectStyleScene);
  }

  /**
   * Switches to Select Language Screen
   */
  public void switchToSelectLanguageScreen() {setScene(selectLanguageScene);
  }

  /**
   * switches to Select Game Type Screen
   */
  public void selectGameTypeScreen() {setScene(selectGameScene);}

  /**
   * Launches a save box to save the current state of the level in a csv file
   */
  public void saveGame() {
    playGameScene.launchSave(model.getLevel());
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
    KeyBinder binder = (KeyBinder)controlsScene.lookupElementInRoot(
        "KeyBinder");
    binder.updateKeyInputScreen(inputter);
    setScene(controlsScene);
  }

  /**
   * Resets the current level in the model by calling model.resetLevel()
   */
  public void resetLevel() {
    try {
      model.resetLevel();
    }
    catch (InvalidFileException ife) {
      System.out.println("invalid file");
    }
  }

  /**
   * Switches back to the last view
   */
  public void back() { setScene(lastScene); }

  /**
   * Starts the game
   */
  public void start() { setScene(playGameScene); }

  /**
   * Returns to the home screen
   */
  public void homeScreen() { setScene(menuScene); }

  /**
   * For testing - return the GameModel
   * @return model
   */
  GameModel getModel() {
    return model;
  }

  /**
   * For testing - return the config path
   * @return configPath
   */
  String getConfigPath() { return configPath; }

  /**
   * Launches the application
   * @param args any arguments to be passed in
   */
  public static void beginOoga(String[] args) {
    launch(args);
  }
}
