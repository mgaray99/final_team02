package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Serves as the controller of our MVC model - handles button pushes and key inputs and keys them
 * to our view and model
 */
public class GameController extends Group implements ButtonPushHandler {

  private static double WIDTH = 800;
  private static double HEIGHT = 800;
  private static String OPTIONS_SELECTOR_EVENTTYPE = "push";
  private static final EventType CONTROLLER_EVENT = new EventType("controller");
  private List<String> buffer;

  public GameController() {
    buffer = new ArrayList<>();
  }

  /**
   *
   * @param folder the folder containing the list of options (i.e. "./ooga/resources/buttons")
   * @param extension the allowed extension for each option (i.e. include if ".jpeg")
   * @param method the method to be called by the OptionsSelector
   */
  public void addOptionsSelectorFromFolder(String folder, String extension, String method) {
    FolderParser parser = new FolderParser(folder,
        extension);
    buildOptionsSelector(parser.getFilenamesFromFolder(), method);
  }

  public void buildOptionsSelector(List<String> choices, String method) {
    List<String> defensiveChoices = new ArrayList<>();
    defensiveChoices.addAll(choices);
    OptionsSelector selector = new OptionsSelector(WIDTH, HEIGHT, defensiveChoices);
    selector.addEventHandler(EventType.ROOT, event->
        callMethodOnOptionSelector(event, method, selector.getTextInBuffer()));
    getChildren().add(selector);
  }

  /**
   *
   * @param event the event that has occurred
   * @param method the method to be called if event matches OPTIONS_SELECTOR_EVENTTYPE
   * @param text the String parameter to be inserted into the method if event matches
   *             OPTIONS_SELECTOR_EVENTTYPE
   */
  private void callMethodOnOptionSelector(Event event, String method, String text) {
    if (event.getEventType().getName().equals(OPTIONS_SELECTOR_EVENTTYPE) && !text.equals("")) {
        List<String> args = new ArrayList<>();
        args.add(text);
        fillBuffer(method, args);
    }
  }


  /**
   * Adds a set of buttons as specified by file to the controller - when they are pushed they will
   * execute handlePush(String methodName) which will use reflection to execute the method called
   * methodName on the view as specified by "view"
   * @param file the filepath of the button file
   */
  public void addButtonsFromFile(String file) {
      try {
        ButtonBuilder builder = new ButtonBuilder(WIDTH, HEIGHT, file, this);
        getChildren().addAll(builder.getFoundButtons());
      }
      catch (ButtonBuilderInstantiationException bbie) {
        System.out.println("couldn't build buttons");
      }
  }

  /**
   * Handles the event where a Button as built by ButtonBuilder has been pushed. When this happens
   * that button calls handlePush with the String type which is a String representation of a method,
   * i.e. back() which will then be called on "view" thus running a method in the view class
   * using reflection
   *
   * @param methodName a String representation of the method to be called on the view class
   */
  @Override
  public void handlePush(String methodName) {
    fillBuffer(methodName, new ArrayList<>());
  }

  /**
   * Adds a button to the controller
   * @param toBeAdded the button to be added
   */
  public void addButton(Button toBeAdded) {
    getChildren().add(toBeAdded);
  }

  public void updateResources(String name) {
    for (Node n : getChildren()) {
      if (n.getClass().getSimpleName().equals("Button")) {
        ((Button)n).setText(ResourceBundle.getBundle("resources/resourcebundles."
            + name).getString(n.getId()));
      }
      else if (n.getClass().getSimpleName().equals("OptionsSelector")) {
        ((OptionsSelector)n).updateBundle(name);
      }
    }
  }

  /**
   * Fires an event
   */
  private void dispatchEvent() {
      fireEvent(new Event(CONTROLLER_EVENT));
  }

  /**
   * Empties the buffer List and then fills it with methodName and a list of arguments
   * @param methodName the String representation of the method
   * @param args a list of arguments
   */
  private void fillBuffer(String methodName, List<String> args) {
    buffer.clear();
    buffer.add(methodName);
    buffer.addAll(args);
    dispatchEvent();
  }

  /**
   * Returns the elements in the buffer, having been defensively copied into bufferHolder
   * @return bufferHolder
   */
  public List<String> getBuffer() {
    List<String> bufferHolder = new ArrayList<>();
    bufferHolder.addAll(buffer);
    return bufferHolder;
  }

}
