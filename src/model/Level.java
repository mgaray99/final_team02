package model;

import java.util.ArrayList;
import java.util.List;
import model.configuration.LevelLoader;
import model.entity.*;
import model.scroll.AutoScroller;
import model.scroll.HorizontalGenerationScroller;
import model.scroll.ManualScroller;
import model.scroll.Scroller;

public class Level {

  private final List<Entity> allEntities = new ArrayList<>();
  private PlayerEntity playerEntity;
  private List<EnemyEntity> enemyEntities;
  public KeyPressFunctions keyPressFunctions = new KeyPressFunctions();
  private Scroller scroller;
  private final int MOVEMENT_SPEED = 1;
  private final int JUMP_SPEED = 3;
  private static final int STARTX = 50;
  private static final int STARTY = 600;
  private static final int START_HEALTH = 10;
  private static final String GENERATION_PATH = "resources/game_configuration/autoflappy.txt";
  private int levelLength;
  private int levelWidth;


  private float gravityFactor = 0.1f;

  public Level(LevelLoader levelLoader) {
    playerEntity = new PlayerEntity(STARTX, STARTY, START_HEALTH);
    levelLength = levelLoader.getMaxArrayLength();
    levelWidth = levelLoader.getMaxArrayWidth();
    scroller = new HorizontalGenerationScroller(-0.10, 0, 15, GENERATION_PATH);
    this.buildEntityList(levelLoader.getLevelMatrix());
  }

  public int getLevelLength() {
    return this.levelLength;
  }

  public int getLevelWidth() {
    return this.levelWidth;
  }

  private void buildEntityList(ArrayList<ArrayList<IEntityType>> levelMatrix){
    for(int i = 0; i < levelMatrix.size(); i++){
      ArrayList<IEntityType> currentRow = levelMatrix.get(i);
      for(int j = 0; j < currentRow.size(); j++){
        IEntityType entityValue = currentRow.get(j);
        EntityFactory entityFactory = new EntityFactory();
        Entity entity = entityFactory.createEntity(entityValue, j, i);
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
    if (entity.getTypeId().equals(EntityType.EMPTY.toString())) {
      return;
    }


    if (entity.getTypeId().equals(EntityType.PLAYER.toString())) {
      playerEntity = (PlayerEntity) entity;
    }

    this.allEntities.add(entity);
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
      scroll();
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

  /**
   * Moves the entities in the level based on data from the List<Entity> and the player
   */
  private void scroll() {
    scroller.scroll(allEntities, playerEntity);
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
