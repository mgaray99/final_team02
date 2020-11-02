package controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import model.GameModel;

public class KeyInputter {

  private GameModel model;
  private Map<String, String> keyToMethodMap;
  private static final String DEFAULT_KEY_INPUT_PATH = "resources/keyinputs/mariokeyinputs.txt";
  private String lastMethodFromKeyPress;

  public KeyInputter(GameModel myModel) {
    model = myModel;
    lastMethodFromKeyPress = "";
    keyToMethodMap = new HashMap<>();

    loadKeyInputsFromFile(DEFAULT_KEY_INPUT_PATH);
  }

  /**
   * Switches the key inputs to those specified by the .txt file specified by path
   *
   * @param path the filepath of the new .txt file
   */
  public void loadKeyInputsFromFile(String path) {
    try {
      KeyInputBuilder builder = new KeyInputBuilder(path);

      List<Pair<String, String>> keyPairs = new ArrayList<>();
      keyPairs.addAll(builder.getKeyPairs());
      keyToMethodMap.clear();

      for (Pair<String, String> pair : keyPairs) {
        keyToMethodMap.put(pair.getKey(), pair.getValue());
      }
    }
    catch (KeyInputBuilderInstantiationException kibie) {
      System.out.println(kibie.getMessage());
    }
  }

  /**
   * Swaps replacementKey for currentKey in the key -> method map. (i.e. if currentKey -> down and
   * replacementKey -> null, then afterwards, replacementKey -> down and currentKey -> null
   *
   * @param currentKey the key currently associated with the method
   * @param replacementKey the key that you want to replace currentKey as being associated with that
   *                       method
   */
  public void swapKeyInput(String currentKey, String replacementKey) {
    if (keyToMethodMap.containsKey(currentKey) && !keyToMethodMap.containsKey(replacementKey)) {
      String correspondingMethod = keyToMethodMap.get(currentKey);
      keyToMethodMap.remove(currentKey);
      keyToMethodMap.put(replacementKey, correspondingMethod);
    }
  }

  /**
   * Handles the event that a key has been input - checks to make sure there is a method to call on
   * that key press and if so calls keyPressed
   *
   * @param press the String representation of the key that has been pressed
   */
  public void keyInput(String press) {
    if (keyToMethodMap.containsKey(press)) {
      keyPressed(press);
    }
    else {
      lastMethodFromKeyPress = "";
    }
  }

  /**
   * Handles the event where a key has been pressed (invariant - we know that key corresponds to
   * a method) and then invokes the method stored in the map
   *
   * @param press the key that has been pressed
   */
  private void keyPressed(String press) {
    try {
      String methodPath = keyToMethodMap.get(press);
      Method method = this.getClass().getDeclaredMethod(methodPath);
      method.invoke(this);
      lastMethodFromKeyPress = methodPath;
    } catch (Exception e) {
      System.out.println("the method attached to that key input broke");
    }
  }

  /**
   * Tells the model to move the player left
   */
  private void left() {
    System.out.println("moving left");
  }

  /**
   * Tells the model to move right
   */
  private void right() {
    System.out.println("moving right");
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  private void up() {
    System.out.println("moving up");
  }

  /**
   * Tells the model to move down (i.e. crouch)
   */
  private void down() {
    System.out.println("moving down");
  }

  /**
   * Tells the model to pause
   */
  private void pause() {
    System.out.println("pausing");
  }

  /**
   * For testing - return the String representation of the last method to occur out of a key press
   * @return the String representation of the last method to be called
   */
  String getLastPush() {
    return lastMethodFromKeyPress;
  }
}
