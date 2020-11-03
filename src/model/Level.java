package model;

import java.util.ArrayList;
import java.util.List;
import model.configuration.LevelLoader;
import model.entity.*;

public class Level {

  private final List<Entity> allEntities = new ArrayList<>();
  private PlayerEntity playerEntity;
  private List<EnemyEntity> enemyEntities;
  public KeyPressFunctions keyPressFunctions = new KeyPressFunctions();
  private final int MOVEMENT_SPEED = 1;
  private final int JUMP_SPEED = 3;

  public Level(LevelLoader levelLoader) {
    this.buildEntityList(levelLoader.getLevelMatrix());
  }

  private void buildEntityList(ArrayList<ArrayList<IEntityType>> levelMatrix){
    for(int i = 0; i < levelMatrix.size(); i++){
      ArrayList<IEntityType> currentRow = levelMatrix.get(i);
      for(int j = 0; j < currentRow.size(); j++){
        IEntityType entityValue = currentRow.get(j);
        EntityFactory entityFactory = new EntityFactory();
        Entity entity = entityFactory.createEntity(entityValue, i, j);
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
    if (!keyPressFunctions.isPaused()) {
      checkCollisions();
      updateEntities();
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

  private void updateEntities() {
    checkForPlayerMovement();
  }


  private void checkForPlayerMovement() {
    if (keyPressFunctions.isPlayerMovingRight()) {
      playerEntity.setXVel(MOVEMENT_SPEED);
    } else if (keyPressFunctions.isPlayerMovingLeft()) {
      playerEntity.setXVel(MOVEMENT_SPEED * -1);
    }
    if (keyPressFunctions.isPlayerJumping()) {
      playerEntity.setYVel(JUMP_SPEED);
    }
  }

  private void moveEntities(){
    playerEntity.moveOneStep();
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

  public KeyPressFunctions getKeyPressFunctions() {
    return keyPressFunctions;
  }

}
