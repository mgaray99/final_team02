package controller;

import api.controller.IKeyInputterMethodCaller;
import api.model.IKeyPressFunctions;
import api.model.IGameModel;

public class KeyInputterMethodCaller implements IKeyInputterMethodCaller {
  private IGameModel model;
  private IKeyPressFunctions functions;

  public KeyInputterMethodCaller(IGameModel mo) {
    model = mo;
    functions = model.getKeyPressFunctions();
  }

  /**
   * Tells the model to move the player left
   */
  @Override
  public void left() {
    functions.startMovingPlayerLeft();
  }

  /**
   * Tells the model to move right
   */
  @Override
  public void right() {
    functions.startMovingPlayerRight();
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  @Override
  public void up() {
    functions.startPlayerJumping();
  }

  /**
   * Tells the model to move down (i.e. crouch)
   */
  @Override
  public void down() {
    // DO NOTHING
  }

  /**
   * Tells the model to pause
   */
  @Override
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
  @Override
  public void leftRelease() {
    functions.stopMovingPlayerLeft();
  }

  /**
   * Tells the model to move right
   */
  @Override
  public void rightRelease() {
    functions.stopMovingPlayerRight();
  }

  /**
   * Tells the model to move up (i.e. jump)
   */
  @Override
  public void upRelease() {
    functions.stopPlayerJumping();
  }

  /**
   * Tells the model to move down (i.e. crouch)
   */
  @Override
  public void downRelease() {
    // DO NOTHING
  }

  /**
   * Tells the model to pause
   */
  @Override
  public void pauseRelease() {
    // DO NOTHING
  }

  /**
   * Resets the game
   */
  @Override
  public void reset() {
    model.getLevel().reinitialize();
  }

  /**
   * Player has released the reset key
   */
  @Override
  public void resetRelease() {
    // DO NOTHING
  }
}
