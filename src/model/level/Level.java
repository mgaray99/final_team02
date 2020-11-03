package model.level;

import java.util.ArrayList;
import java.util.List;
import model.configuration.GameConfiguration;
import model.entity.EmptyEntity;
import model.entity.EnemyEntity;
import model.entity.Entity;
import model.entity.IEntityType;
import model.entity.PlayerEntity;

public class Level {

  private final List<Entity> allEntities = new ArrayList<>();
  private PlayerEntity playerEntity;
  private List<EnemyEntity> enemyEntities;
  private final KeyPressFunctions keyPressFunctions = new KeyPressFunctions();

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
    if (!keyPressFunctions.isPaused()) {
      checkCollisions();
      checkWinCondition();
      moveEntities();
    }
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

  private void moveEntities(){
    movePlayer();
  };

  private void movePlayer() {
    if (keyPressFunctions.isPlayerMovingRight()) {
      //move player right
    } else if (keyPressFunctions.isPlayerMovingLeft()) {
      // move player left
    }
    if (keyPressFunctions.isPlayerJumping()) {
      //jump player
    }
  }

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
