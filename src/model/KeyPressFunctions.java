package model;

public class KeyPressFunctions {

  private boolean isPaused = false;
  private boolean movePlayerLeft = false;
  private boolean movePlayerRight = false;
  private boolean jumpPlayer = false;

  protected boolean isPaused() {
    return isPaused;
  }

  protected boolean isPlayerMovingLeft() {
    return movePlayerLeft;
  }

  protected boolean isPlayerMovingRight() {
    return movePlayerRight;
  }

  protected boolean isPlayerJumping() {
    return jumpPlayer;
  }

  public void pauseGame() {
    isPaused = true;
  }

  public void resumeGame() {
    isPaused = false;
  }

  public void startMovingPlayerLeft() {
    movePlayerLeft = true;
  }

  public void stopMovingPlayerLeft() {
    movePlayerLeft = false;
  }

  public void startMovingPlayerRight() {
    movePlayerRight = true;
  }

  public void stopMovingPlayerRight() {
    movePlayerRight = false;
  }

  public void startPlayerJumping() {
    jumpPlayer = true;
  }

  public void stopPlayerJumping() {
    jumpPlayer = false;
  }
}
