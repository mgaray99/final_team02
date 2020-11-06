package controller;

import model.GameModel;
import model.KeyPressFunctions;

public class KeyInputterMethodCaller {
  private GameModel model;
  private KeyPressFunctions functions;

  public KeyInputterMethodCaller(GameModel mo) {
    model = mo;
    functions = model.getKeyPressFunctions();
  }

  /**
   * Tells the model to move the player left
   */
  public void left() {
    functions.startMovingPlayerLeft();
  }

  /**
   * Tells the model to move right
   */
  public void right() {
    functions.startMovingPlayerRight();
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  public void up() {
    functions.startPlayerJumping();
  }

  /**
   * Tells the model to move down (i.e. crouch)
   */
  public void down() {
    System.out.println("moving down");
  }

  /**
   * Tells the model to pause
   */
  public void pause() {
    if (functions.isPaused()) {
      functions.resumeGame();
    }
    else {
      functions.pauseGame();
    }
  }

  /**
   * Tells the model to move the player left
   */
  public void leftRelease() {
    functions.stopMovingPlayerLeft();
  }

  /**
   * Tells the model to move right
   */
  public void rightRelease() {
    functions.stopMovingPlayerRight();
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  public void upRelease() {
    functions.stopPlayerJumping();
  }

  /**
   * Tells the model to move down (i.e. crouch)
   */
  public void downRelease() {
    System.out.println("releasing down");
  }

  /**
   * Tells the model to pause
   */
  public void pauseRelease() {
    System.out.println("releasing pause");
  }
}
