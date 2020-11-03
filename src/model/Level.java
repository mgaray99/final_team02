package model;

import java.util.ArrayList;
import java.util.List;
import model.configuration.LevelLoader;
import model.entity.*;

// Hey guys Alex here -> I changed 2 things (I added an else statement at line 111 to
// stop the player from moving indefinitely when left or right is pressed and I created a method
// placeEntity which checks to see if an entity is a player and if so makes playerEntity equal it
public class Level {

  private final List<Entity> allEntities = new ArrayList<>();
  private PlayerEntity playerEntity;
  private List<EnemyEntity> enemyEntities;
  public KeyPressFunctions keyPressFunctions = new KeyPressFunctions();
  private final int MOVEMENT_SPEED = 1;
  private final int JUMP_SPEED = 3;
  private static final int STARTX = 50;
  private static final int STARTY = 600;
  private static final int START_HEALTH = 10;


  private float gravityFactor = 0.1f;

  public Level(LevelLoader levelLoader) {
    playerEntity = new PlayerEntity(STARTX, STARTY, START_HEALTH);
    this.buildEntityList(levelLoader.getLevelMatrix());
  }

  private void buildEntityList(ArrayList<ArrayList<IEntityType>> levelMatrix){
    for(int i = 0; i < levelMatrix.size(); i++){
      ArrayList<IEntityType> currentRow = levelMatrix.get(i);
      for(int j = 0; j < currentRow.size(); j++){
        IEntityType entityValue = currentRow.get(j);
        EntityFactory entityFactory = new EntityFactory();
        Entity entity = entityFactory.createEntity(entityValue, j, i);
        this.allEntities.add(entity);
        placeEntity(entity);
      }
    }
  }

  /**
   * Checks the entity to see what type it is and then inserts it into the correct list based
   * on that type (i.e. an entity of ENEMY goes into enemyEntities)
   * @param entity
   */
  private void placeEntity(Entity entity) {
    if (entity.getTypeId().equals(EntityType.PLAYER.toString())) {
      playerEntity = (PlayerEntity)entity;
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
    checkForKeyPresses();
    applyGravity();

  }


  private void checkForKeyPresses() {
    if (keyPressFunctions.isPlayerMovingRight()) {
      playerEntity.setXVel(MOVEMENT_SPEED);
    } else if (keyPressFunctions.isPlayerMovingLeft()) {
      playerEntity.setXVel(MOVEMENT_SPEED * -1);
    } else {
      playerEntity.setXVel(0);
    }
    if (keyPressFunctions.isPlayerJumping()) {
      playerEntity.setYVel(JUMP_SPEED);
    }
  }

  public void applyGravity() {
    for (Entity entity : allEntities) {
      if (entity.affectedByGravity() && !entity.isGrounded()) {
        entity.setYVel(entity.getYVel() - gravityFactor);
      }
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

  /**
   * Returns all of the entities in the Level - however, we defensively copy them into a separate
   * list to avoid aliasing issues
   * @return a defensive copy of allEntities
   */
  public List<Entity> getAllEntities() {
    List<Entity> defensiveCopyOfEntities = new ArrayList<>();
    defensiveCopyOfEntities.addAll(allEntities);
    return defensiveCopyOfEntities;
  }

}
