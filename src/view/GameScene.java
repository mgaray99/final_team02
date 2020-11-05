package view;

import controller.FolderParser;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import controller.GameController;

/**
 * Represents one scene in our GameView - holds data and methods beyond that of a typical Scene
 * object like a GameController variable and methods to update the text of all of its
 * children node
 *
 * @author Alex Lu & Edem Ahorlu
 */
public class GameScene extends Scene {
  private Group root;
  private String sceneId;
  private final double WIDTH;
  private final double HEIGHT;
  private GameController controller;
  private static final String CONTROLLER = "controller";
  private static final String BACKGROUND = "background";
  private static final String DEFAULT_CSS_FILEPATH = "resources/cssstylesheets/default.css";
  private static final String STYLESHEET_PATH = "resources/cssstylesheets/";
  private static final String LANGUAGE_FOLDERPATH_LONG = "./src/resources/resourcebundles";
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String STYLESHEET_PATH_LONG = "./src/resources/cssstylesheets";
  private static final String CSS_EXTENSION = ".css";


  public GameScene(Group myRoot, String id, double width, double height) {
    super(myRoot, width, height);
    root = myRoot;
    WIDTH = width;
    HEIGHT = height;
    sceneId = id;

    Rectangle background = new Rectangle(WIDTH,HEIGHT, Color.WHITE);

    background.setId(BACKGROUND);
    root.getChildren().add(background);
    getStylesheets().add(DEFAULT_CSS_FILEPATH);
  }


  /**
   * Sets the controller associated with this particular scene
   * @param cont the controller to serve as the game scene's controller
   */
  public void setGameController(GameController cont) {
    controller = cont;
    controller.setId("#" + CONTROLLER);
    root.getChildren().add(controller);
  }

  /**
   * Adds buttons from a file to the controller
   * @param file the file containing the buttons to be included
   */
  public void addButtonsToControllerFromFile(String file) {
    controller.addButtonsFromFile(file);
  }

  /**
   * Builds an option selector for the controller associated with the scene
   * @param folder the folder containing the list of options (i.e. "./ooga/resources/buttons")
   * @param extension the allowed extension for each option (i.e. include if ".jpeg")
   * @param method the method to be called by the OptionsSelector
   */
  public void buildOptionsSelectorFromFolderForController(String folder, String extension,
      String method) {
    controller.addOptionsSelectorFromFolder(folder, extension, method);
  }

  public void buildOptionsSelectorFromListForController(List<String> choices, String method) {
    controller.buildOptionsSelector(choices, method);
  }

  /**
   * Adds a node element to the root node of the GameScene (i.e. button, controller, etc.)
   * @param toBeAdded the Node to be inserted
   */
  public void addElementToRoot(Node toBeAdded) {
    root.getChildren().add(toBeAdded);
  }

  /**
   * Returns the controller associated with this GameScene
   * @return controller
   */
  public GameController getGameController() {
    return controller;
  }

  /**
   * Removes a node element from the root node of the GameScene (i.e. button, controller, etc.)
   * @param toBeRemoved the Node to be removed
   */
  public void removeElementFromRoot(Node toBeRemoved) {
    root.getChildren().remove(toBeRemoved);
  }

  /**
   * Looks up an element in the GameScene and returns it if found, otherwise throws a
   * NullPointerException
   * @param id the id to be looked up
   * @return the node if it exists in the GameScene
   */
  public Node lookupElementInRoot(String id) {
    Node element = root.lookup("#" + id);
    if (!element.equals(null)) {
      return element;
    }
    throw new NullPointerException("Node not found!");
  }

  /**
   * Updates the resource bundle displaying text for each scene
   * @param name the name of the resource bundle
   */
  public void updateResources(String name) {
    FolderParser parser = new FolderParser(LANGUAGE_FOLDERPATH_LONG, PROPERTIES_EXTENSION);
    if (parser.getFilenamesFromFolder().contains(name)) {
      controller.updateResources(name);
    }
  }

  /**
   * Updates the stylesheet of this GameScene
   * @param name the name of the new stylesheet
   */
  public void updateStylesheet(String name) {
      FolderParser parser = new FolderParser(STYLESHEET_PATH_LONG, CSS_EXTENSION);
      if (parser.getFilenamesFromFolder().contains(name)) {
        getStylesheets().clear();
        getStylesheets().add(STYLESHEET_PATH + name + CSS_EXTENSION);
      }
  }

  /**
   * Returns the scene id
   * @return sceneId
   */
  public String getSceneId() {
    return sceneId;
  }
}
