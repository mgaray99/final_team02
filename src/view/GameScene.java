package view;

import controller.FolderParser;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import controller.GameController;
import javafx.scene.text.Text;

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
  protected final double WIDTH;
  protected final double HEIGHT;
  private GameController controller;
  private Text errorLabel;
  private ResourceBundle bundle;
  private static final String CONTROLLER = "controller";
  protected static final String BACKGROUND = "background";
  private static final String DEFAULT_CSS_FILEPATH = "resources/cssstylesheets/default.css";
  private static final String STYLESHEET_PATH = "resources/cssstylesheets/";
  private static final String LANGUAGE_FOLDERPATH_LONG = "./src/resources/resourcebundles";
  private static final String LANGUAGE_FOLDERPATH = "resources/resourcebundles.";
  private static final String PROPERTIES_EXTENSION = ".properties";
  private static final String STYLESHEET_PATH_LONG = "./src/resources/cssstylesheets";
  private static final String CSS_EXTENSION = ".css";
  private static final String DEFAULT_LANGUAGE = "English";


  public GameScene(Group myRoot, String id, double width, double height) {
    super(myRoot, width, height);
    root = myRoot;
    WIDTH = width;
    HEIGHT = height;
    sceneId = id;

    bundle = ResourceBundle.getBundle(LANGUAGE_FOLDERPATH + DEFAULT_LANGUAGE);
    setGameController(new GameController());

    makeBackground();
    makeErrorText();

    getStylesheets().add(DEFAULT_CSS_FILEPATH);
  }

  /**
   * Sets the background Rectangle for the game
   */
  private void makeBackground() {
    Rectangle background = new Rectangle(WIDTH,HEIGHT, Color.WHITE);
    background.setId(BACKGROUND);
    root.getChildren().add(background);
    background.toBack();
  }

  /**
   * Makes the error text label that will appear at the top of the screen
   */
  private void makeErrorText() {
    errorLabel = new Text();
    errorLabel.setText("");
    errorLabel.setLayoutX(WIDTH / 2 - errorLabel.getLayoutBounds().getWidth() / 2);
    errorLabel.setLayoutY(HEIGHT/20);
    root.getChildren().add(errorLabel);
  }

  /**
   * Updates the error text label that will appear at the top of the screen
   * @param newText the new text to fill that label
   */
  public void updateErrorText(String newText) {
    errorLabel.setText(newText);
    errorLabel.setId("scoreStyle");
    errorLabel.getStyleClass().add("scoreStyle");


    errorLabel.setVisible(true);
    errorLabel.setLayoutX(WIDTH / 2 - errorLabel.getLayoutBounds().getWidth() / 2);
  }

  /**
   * Hides the error text from view
   */
  public void hideErrorText() {
    errorLabel.setVisible(false);
  }

  /**
   * Returns the text in the error label
   * @return the text of errorLabel
   */
  public String getErrorText() {
    return errorLabel.getText();
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
    } else {
      updateErrorText(getValueFromBundle("UPDATE_RESOURCES_ERROR"));
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
      } else {
        updateErrorText(getValueFromBundle("UPDATE_STYLE_SHEET_ERROR"));
      }
  }

  /**
   * Returns the scene id
   * @return sceneId
   */
  public String getSceneId() {
    return sceneId;
  }

  /**
   * Returns the value corresponding to key in the resouce bundle
   * @param key the key in resourceBundle
   * @return the value in resourceBundle
   */
  public String getValueFromBundle(String key) {
    String value = bundle.getString(key);
    if (value!=null) {
      return value;
    }
    return "";
  }
}
