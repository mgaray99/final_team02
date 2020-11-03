package model;

import model.entity.Entity;
import java.util.*;
import model.entity.LivingEntity;
import model.entity.MobileEntity;

public class Level {

  List<Entity> allEntities;
  List<MobileEntity> mobileEntities;
  List<LivingEntity> livingEntities;

  public Level(List<Entity> allEntities) {
    this.allEntities = allEntities;
    for (Entity entity : allEntities) {
      if (entity instanceof LivingEntity) {

      }
    }
  }

  int[][] getGrid() {return null;}

  public Entity getEntity(int xCoordinate, int yCoordinate) {
    return null;
  }

  public void step() {
    checkCollisions();
    checkWinCondition();
    moveEntities();
  }

  private void checkCollisions(){
    for (LivingEntity livingEntity : livingEntities) {
      for (Entity anyEntity : allEntities) {
        livingEntity.checkCollision(anyEntity);
      }
    }
  };

  private void moveEntities(){
    for (MobileEntity mobileEntity : mobileEntities) {
      mobileEntity.moveOneStep();
    }
  };

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
