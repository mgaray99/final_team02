/**
 * The controller of our game
 */
class GameController {
  private static final String MOVE_LEFT = "move left";

  /**
   * Responds to a press of a key
   * @param code represents the key that the user pressed
   */
  public void KeyHandler(KeyCode code) {
    if (code.equals(KeyCode.LEFT)) {
      dispatchEvent(MOVE_LEFT);
    }
  }

  /**
   * Fires an event with a type of eventType to let the holder of the controller know that something
   * has happened
   *
   * @param eventType the type of event that has occured (i.e. "move right", "move left")
   */
  private void dispatchEvent(String eventType) {
    fireEvent(new Event(eventType));
  }
}

/**
 * The view of our game
 */
class GameView {

  private GameController currentController;
  private GameModel currentModel;
  private static final String MOVE_LEFT = "move left";

  /**
   * Instantiates the game controller that will run the game - build an EventHandler to catch
   * events coming from GameController
   */
  private void buildGameController() {
    currentController = new GameController();
    controller.addEventHandler(EventType.ROOT, event -> handleControllerEvent(
        event.getEventType().getName()));
  }

  /**
   * Responds to the situation where an event has happened in currentController
   * @param event the String representation of the event that has occurred
   */
  private void handleControllerEvent(String event) {
      if (event.equals(MOVE_LEFT)) {
        // Sets the player's next motion to be -1 in the x direction, 0 in the y direction
        currentModel.getPlayer().setMotion(new Vector2D(-1, 0));
      }
  }
}