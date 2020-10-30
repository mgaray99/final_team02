package ooga.controller;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.event.Event;
import javafx.scene.control.Button;

/**
 * Serves as the controller of our MVC model - handles button pushes and key inputs and keys them
 * to our view and model
 */
public class GameController extends Group implements ButtonPushHandler {

  private static double WIDTH = 800;
  private static double HEIGHT = 800;
  private static String OPTIONS_SELECTOR_EVENTTYPE = "push";
  private Object view;

  public GameController(Object v) {
    view = v;
    addGameControllerElements();
  }

  /**
   * Is responsible for building the different elements (i.e. Button, OptionsSelector, that will
   * be in our controller
   */
  private void addGameControllerElements() {

  }

  /**
   *
   * @param folder the folder containing the list of options (i.e. "./ooga/resources/buttons")
   * @param extension the allowed extension for each option (i.e. include if ".jpeg")
   * @param method the method to be called by the OptionsSelector
   */
  public void addOptionsSelectorFromFolder(String folder, String extension, Method method) {
    FolderParser parser = new FolderParser(folder,
        extension);
    OptionsSelector selector = new OptionsSelector(WIDTH, HEIGHT, parser.getFilenamesFromFolder());
    selector.addEventHandler(EventType.ROOT, event->
        callMethodOnOptionSelector(event, method, selector.getTextInBuffer()));
    getChildren().add(selector);
  }

  /**
   *
   *
   * @param event the event that has occurred
   * @param method the method to be called if event matches OPTIONS_SELECTOR_EVENTTYPE
   * @param text the String parameter to be inserted into the method if event matches
   *             OPTIONS_SELECTOR_EVENTTYPE
   */
  private void callMethodOnOptionSelector(Event event, Method method, String text) {
    if (event.getEventType().getName().equals(OPTIONS_SELECTOR_EVENTTYPE) && !text.equals("")) {
        String[] methodArgs = {text};
        invokeMethod(method, methodArgs);
    }
  }

  /**
   * Calls the method "method" with the parameter "text"
   *
   * @param method the method to be invoked
   * @param text the value of the string
   */
  private void invokeMethod(Method method, String[] text) {
    try {
      method.invoke(view, text);
    } catch (Exception e) {
      System.out.println("invalid method");
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
    try {
      Method method = view.getClass().getDeclaredMethod(methodName);
      method.invoke(view);
    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("invalid method view");
    }
  }

}
