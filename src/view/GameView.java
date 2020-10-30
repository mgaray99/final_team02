package view;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameView extends Application {

  private enum viewName {HOME_SCREEN, SELECT_RESOURCE_BUNDLE, SELECT_CSS_STYLESHEET};
  private Map<viewName, Scene> mapOfScenes;

  private static final double ANIMATION_SPEED = 1/60.0;
  private static final double WIDTH = 800;
  private static final double HEIGHT = 800;

  /**
   * Begins our view, (i.e. builds the scene and group objects responsible for showing our project)
   * @param stage the main stage of the program
   */
  public void start(Stage stage) {
    buildMapOfScenes();
    stage.setScene(mapOfScenes.get(viewName.HOME_SCREEN));
    stage.show();

    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(ANIMATION_SPEED), e -> update(ANIMATION_SPEED));
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Builds the map containing each of the scenes that we will display during our project
   * First instantiates the "mapOfScenes" variable as a HashMap.
   *
   * Then for each possible "view" we have declared in the enum viewName (i.e. HOME_SCREEN, etc.)
   * builds a "GameScene" object corresponding to that "view" that will serve as the view's
   * scene
   */
  private void buildMapOfScenes() {
    mapOfScenes = new HashMap<>();
    for (viewName view : viewName.values()) {
      mapOfScenes.put(view, new GameScene(new Group(), WIDTH, HEIGHT));
    }
  }

  /**
   * Updates the view given that time has passed
   * @param timeElapsed the amount of time that has passed since the last update
   */
  private void update(double timeElapsed) {

  }

  /**
   * Switches the scene to the viewName indexed by view
   * @param view the viewName (i.e. HOME_SCREEN, SELECT_CSS_STYLESHEET) to become the new scene
   */
  public void setScene(viewName view) {
    setScene(view);
  }

  /**
   * Launches the application
   * @param args any arguments to be passed in
   */
  public static void beginOoga(String[] args) {
    launch(args);
  }
}
