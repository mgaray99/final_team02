package controller;

import model.GameModel;

public class KeyInputterMethodCaller {
  private GameModel model;

  public KeyInputterMethodCaller(GameModel mo) {
    model = mo;
  }

  /**
   * Tells the model to move the player left
   */
  public void left() {
    System.out.println("moving left");
  }

  /**
   * Tells the model to move right
   */
  public void right() {
    System.out.println("moving right");
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  public void up() {
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
    System.out.println("pausing");
  }

  /**
   * Tells the model to move the player left
   */
  public void leftRelease() {
    System.out.println("releasing left");
  }

  /**
   * Tells the model to move right
   */
  public void rightRelease() {
    System.out.println("releasing right");
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  public void upRelease() {
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
