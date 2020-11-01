package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.controller.GameController;

/**
 * Represents one scene in a game
 */
public class GameScene extends Scene {
  private Group root;
  private final double WIDTH;
  private final double HEIGHT;
  private GameController controller;
  private static final String CONTROLLER = "controller";
  private static final String BACKGROUND = "background";
  private static final EventType SCENE_EVENT = new EventType("scene");


  public GameScene(Group myRoot, double width, double height) {
    super(myRoot, width, height);
    root = myRoot;
    WIDTH = width;
    HEIGHT = height;

    Rectangle background = new Rectangle(WIDTH,HEIGHT, Color.WHITE);
    background.setId(BACKGROUND);
    root.getChildren().add(background);
  }

  /**
   * Sets the controller associated with this particular scene
   * @param cont the controller to serve as the game scene's controller
   */
  public void setController(GameController cont) {
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
  public void buildOptionsSelectorForController(String folder, String extension,
      String method) {
    controller.addOptionsSelectorFromFolder(folder, extension, method);
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
  public GameController getController() {
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

}
