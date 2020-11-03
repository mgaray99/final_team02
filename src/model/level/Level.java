package model.level;

import model.configuration.GameConfiguration;
import model.configuration.LevelLoader;
import model.entity.*;

import java.io.File;
import java.util.*;

public class Level {

  private final List<Entity> allEntities = new ArrayList<>();

  public Level(LevelLoader levelLoader) {
    this.buildEntityList(levelLoader.getLevelMatrix());
  }

  private void buildEntityList(int[][] levelMatrix){
    for(int i = 0; i < levelMatrix.length; i++){
      int[] currentRow = levelMatrix[i];
      for(int j = 0; j < currentRow.length; j++){
        int entityValue = currentRow[j];
        Entity entity;
        switch (entityValue) {
          case 1 -> entity = new BarrierBlockEntity(i, j);
          case 2 -> entity = new BreakableBlockEntity(i, j);
          case 3 -> entity = new DamagingBlockEntity(i, j);
          case 4 -> entity = new EnemyEntity(i, j, 100);
          case 5 -> entity = new GoalEntity(i, j);
          case 6 -> entity = new PlayerEntity(i, j, 100);
          case 7 -> entity = new PowerUpEntity(i, j);
          case 8 -> entity = new PowerUpBlock(i, j);
          default -> entity = EmptyEntity.INSTANCE;
        }
        this.allEntities.add(entity);
      }
    }
  }

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
