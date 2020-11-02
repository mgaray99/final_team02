package model.level;

import model.configuration.GameConfiguration;
import model.entity.EmptyEntity;
import model.entity.Entity;
import model.entity.IEntityType;

import java.util.*;

public class Level {

  private final List<Entity> allEntities = new ArrayList<>();

  public Level() {
  }

  public static Level fromConfiguration(GameConfiguration gameConfiguration) {
    return new Level();
  }

  int[][] getGrid() {return null;}

  public Entity getEntity(int xCoordinate, int yCoordinate) {
    for(Entity entity : this.allEntities){
      if(entity.getHitBox().x == xCoordinate && entity.getHitBox().y == yCoordinate){
        return entity;
      }
    }
    return EmptyEntity.INSTANCE;
  }

  public Entity getEntity(IEntityType entityType, int xCoordinate, int yCoordinate){
    for(Entity entity : this.allEntities){
      if(entity.hasMatchingId(entityType, xCoordinate, yCoordinate)){
        return entity;
      }
    }
    return EmptyEntity.INSTANCE;
  }

  public void step() {
    checkCollisions();
    checkWinCondition();
    moveEntities();
  }

  private void checkCollisions(){
    for(int i = 0; i < this.allEntities.size(); i++){
      Entity currentEntity = this.allEntities.get(i);
      if(currentEntity.shouldCheckCollisions()){
        for(int j = 0; j < this.allEntities.size(); j++){
          boolean otherEntityIsCurrentEntity = j == i;
          if(otherEntityIsCurrentEntity) continue;
          Entity otherEntity = this.allEntities.get(i);
          currentEntity.checkCollision(otherEntity);
        }
      }
    }
  }

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
