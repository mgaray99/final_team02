package ooga.controller;

import java.io.File;
import java.lang.reflect.Method;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import jdk.jfr.Event;

/**
 * Serves as the controller of our MVC model - handles button pushes and key inputs and keys them
 * to our view and model
 */
public class GameController extends Group {

  private static double WIDTH = 800;
  private static double HEIGHT = 800;
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

  public void addOptionsSelectorFromFolder(String folder, String extension, Method method) {
    FolderParser parser = new FolderParser(folder,
        extension);
    OptionsSelector selector = new OptionsSelector(WIDTH, HEIGHT, parser.getFilenamesFromFolder());
    selector.addEventHandler(EventType.ROOT, event->
        callMethodOnOptionSelector(event, method, selector.getTextInBuffer()));
    getChildren().add(selector);
  }

  private void callMethodOnOptionSelector(Event event, Method method, String text) {
    if (event.)
    try {
      System.out.println(text);
      method.invoke(view, text);
    }
    catch (Exception e){
      System.out.println("invalid method");
    }
  }

}
