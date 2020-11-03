package controller;

import model.GameModel;
import model.KeyPressFunctions;

public class KeyInputterMethodCaller {
  private GameModel model;
  private KeyPressFunctions functions;

  public KeyInputterMethodCaller(GameModel mo) {
    model = mo;
    //functions = model.getKeyPressFunctions();
    functions = new KeyPressFunctions();
  }

  /**
   * Tells the model to move the player left
   */
  public void left() {
    functions.startMovingPlayerLeft();
    System.out.println("moving left");
  }

  /**
   * Tells the model to move right
   */
  public void right() {
    functions.startMovingPlayerRight();
    System.out.println("moving right");
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  public void up() {
    functions.startPlayerJump();
    System.out.println("moving up");
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
      System.out.println("resuming");
    }
    else {
      functions.pauseGame();
      System.out.println("pausing");
    }
  }

  /**
   * Tells the model to move the player left
   */
  public void leftRelease() {
    functions.stopMovingPlayerLeft();
    System.out.println("releasing left");
  }

  /**
   * Tells the model to move right
   */
  public void rightRelease() {
    functions.stopMovingPlayerRight();
    System.out.println("releasing right");
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  public void upRelease() {
    functions.stopPlayerJump();
    System.out.println("releasing up");
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
