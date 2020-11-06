package controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javafx.util.Pair;
import model.GameModel;

public class KeyInputter {

  private KeyInputterMethodCaller methodCaller;
  private Map<String, String> keyToMethodMap;
  private static final String DEFAULT_KEY_INPUT_PATH = "resources/keyinputs/mariokeyinputs.properties";
  private String lastMethodFromKeyPress;
  private static final String[] bannedKeys = {"ENTER", "ESC", "TAB"};

  public KeyInputter(GameModel model) {
    methodCaller = new KeyInputterMethodCaller(model);
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
      Properties properties = new Properties();
      InputStream stream =  getClass().getClassLoader().getResourceAsStream(
          path);
      properties.load(stream);
      Map<String, String> loadedMap = new TreeMap(properties);

      keyToMethodMap.clear();
      loadedMap.keySet().forEach(key -> keyToMethodMap.put(key, loadedMap.get(key)));


    }
    catch (Exception e) {
      System.out.println(e.getMessage());
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
  public void keyPressed(String press) {
    if (keyToMethodMap.containsKey(press)) {
      String methodPath = keyToMethodMap.get(press);
      invokeMethod(methodPath);
    }
  }

  /**
   * Handles the event that a key has been released - checks to make sure there is a method to call on
   * that key press and if so calls keyPressed
   *
   * @param press the String representation of the key that has been pressed
   */
  public void keyReleased(String press) {
    if (keyToMethodMap.containsKey(press)) {
      String methodPath = keyToMethodMap.get(press) + "Release";
      invokeMethod(methodPath);
    }
  }

  /**
   * Handles the event where a key has been pressed (invariant - we know that key corresponds to
   * a method) and then invokes the method stored in the map
   *
   * @param methodPath the String representation of the method to be called
   */
  private void invokeMethod(String methodPath) {
    try {
      Method method = methodCaller.getClass().getDeclaredMethod(methodPath);
      method.invoke(methodCaller);
      lastMethodFromKeyPress = methodPath;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("the method attached to that key input broke");
    }
  }

  /**
   * Returns a mapping of keys to methods (defensively create rather than return existing map
   * @return a mapping of String keys to String methods
   */
  public List<Pair<String, String>> getKeyMethodPairings() {
    List<Pair<String, String>> pairings = new ArrayList<>();
    keyToMethodMap.keySet().forEach(key -> pairings.add(new Pair<>(key, keyToMethodMap.get(key))));
    return pairings;
  }

  /**
   * Checks to make sure that a key is valid
   * @param key the String key
   * @return a boolean revealing whether or not the key is valid
   */
  public boolean isValidKey(String key) {
    List<String> blockedKeysList = Arrays.asList(bannedKeys);
    return !keyToMethodMap.containsKey(key) && !blockedKeysList.contains(key);
  }

  /**
   * For testing - return the String representation of the last method to occur out of a key press
   * @return the String representation of the last method to be called
   */
  String getLastPush() {
    String tempPush = lastMethodFromKeyPress;
    lastMethodFromKeyPress = "";
    return tempPush;
  }
}
