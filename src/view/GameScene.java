package ooga.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * Represents one scene in a game
 */
public class GameScene extends Scene {
  private Group root;
  private final double WIDTH;
  private final double HEIGHT;


  public GameScene(Group myRoot, double width, double height) {
    super(myRoot, width, height);
    root = myRoot;
    WIDTH = width;
    HEIGHT = height;
  }

  /**
   * Adds a node element to the root node of the GameScene (i.e. button, controller, etc.)
   * @param toBeAdded the Node to be inserted
   */
  public void addElementToRoot(Node toBeAdded) {
    root.getChildren().add(toBeAdded);
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
