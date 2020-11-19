package model;

import api.model.IKeyPressFunctions;

public class KeyPressFunctions implements IKeyPressFunctions {

  private boolean isPaused = false;
  private boolean movePlayerLeft = false;
  private boolean movePlayerRight = false;
  private boolean jumpPlayer = false;

  @Override
  public boolean isPaused() {
    return isPaused;
  }

  @Override
  public boolean isPlayerMovingLeft() {
    return movePlayerLeft;
  }

  @Override
  public boolean isPlayerMovingRight() {
    return movePlayerRight;
  }

  @Override
  public boolean isPlayerJumping() {
    return jumpPlayer;
  }

  @Override
  public void pauseGame() {
    isPaused = true;
  }

  @Override
  public void resumeGame() {
    isPaused = false;
  }

  @Override
  public void startMovingPlayerLeft() {
    movePlayerLeft = true;
  }

  @Override
  public void stopMovingPlayerLeft() {
    movePlayerLeft = false;
  }

  @Override
  public void startMovingPlayerRight() {
    movePlayerRight = true;
  }

  @Override
  public void stopMovingPlayerRight() {
    movePlayerRight = false;
  }

  @Override
  public void startPlayerJumping() {
    jumpPlayer = true;
  }

  @Override
  public void stopPlayerJumping() {
    jumpPlayer = false;
  }
}
