package model.level;

public class KeyPressFunctions {

  private boolean isPaused;
  private boolean movePlayerLeft;
  private boolean movePlayerRight;
  private boolean jumpPlayer;

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

  public void startPlayerJump() {
    jumpPlayer = true;
  }

  public void stopPlayerJump() {
    jumpPlayer = false;
  }
}
