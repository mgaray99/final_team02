package model;

public class Level {

  List<Entity>  allEntities;

  public Level() {}

  int[][] getGrid() {return null;}

  public Entity getEntity(int xCoordinate, int yCoordinate) {
    return null;
  }

  public void step() {
    checkCollisions();
    checkWinCondition();
    moveEntities();
  }

  private void checkCollisions(){};
  private void moveEntities(){};

  private void checkWinCondition(){};


  void isLevelWon(boolean isLevelWon) {
    if (isLevelWon) {
      return;
    }
  }

  void isLevelLost(boolean isLevelLost) {
    if (isLevelLost) {
      return;
    }
  }

}
