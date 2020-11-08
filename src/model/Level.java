package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.configuration.LevelLoader;
import model.entity.EmptyEntity;
import model.entity.Entity;
import model.entity.EntityType;
import model.entity.IEntityType;

// Hey guys Alex here -> I changed 2 things (I added an else statement at line 111 to
// stop the player from moving indefinitely when left or right is pressed and I created a method
// placeEntity which checks to see if an entity is a player and if so makes playerEntity equal it
public class Level {

  //private final List<Entity> allEntities = new ArrayList<>();
  public KeyPressFunctions keyPressFunctions = new KeyPressFunctions();
  private final int MOVEMENT_SPEED = 1;
  private final float JUMP_SPEED = -2f;
  private static final int STARTX = 50;
  private static final int STARTY = 600;
  private static final int START_HEALTH = 10;
  private int levelLength;
  private int levelWidth;

  private Map<IEntityType, ArrayList<Entity>> allEntityMap = new HashMap<>();
  private List<Entity> collidableEntities = new ArrayList<>();
  private List<Entity> gravityEntities = new ArrayList<>();
  private List<Entity> allEntityList = new ArrayList<>();

  private float gravityFactor = 0.2f;

  public Level(LevelLoader levelLoader) {
    levelLength = levelLoader.getMaxArrayLength();
    levelWidth = levelLoader.getMaxArrayWidth();
    this.buildEntityMap(levelLoader.getLevelMatrix());
  }

  public int getLevelLength() {
    return this.levelLength;
  }

  public int getLevelWidth() {
    return this.levelWidth;
  }

  private void buildEntityMap(ArrayList<ArrayList<IEntityType>> levelMatrix){
    for(int i = 0; i < levelMatrix.size(); i++){
      ArrayList<IEntityType> currentRow = levelMatrix.get(i);
      for(int j = 0; j < currentRow.size(); j++){
        IEntityType entityValue = currentRow.get(j);
        EntityFactory entityFactory = new EntityFactory();
        Entity entity = entityFactory.createEntity(entityValue, j, i);

        ArrayList<Entity> entitiesOfType = allEntityMap.getOrDefault(entity.getEntityType(), new ArrayList<>());
        entitiesOfType.add(entity);
        this.allEntityMap.put(entity.getEntityType(), entitiesOfType);

        this.addEntity(entity);
      }
    }
  }

  private void addEntity(Entity entity) {
    this.allEntityList.add(entity);
    if(entity.getEntityType().shouldCheckCollisions()){
      this.collidableEntities.add(entity);
    }
    if(entity.getEntityType().isAffectedByGravity()){
      this.gravityEntities.add(entity);
    }
  }

  public void removeEntity(Entity entityToRemove){
    this.allEntityList.remove(entityToRemove);
    if(entityToRemove.getEntityType().shouldCheckCollisions()){
      this.collidableEntities.remove(entityToRemove);
    }
    if(entityToRemove.getEntityType().isAffectedByGravity()){
      this.gravityEntities.remove(entityToRemove);
    }
  }

  public Entity getEntityAt(int xCoordinate, int yCoordinate) {
    for(Entity entity : getAllEntitiesInMapAsList()){
      if(entity.getHitBox().x == xCoordinate && entity.getHitBox().y == yCoordinate){
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

  public void checkCollisions(){
    for(Entity entity : this.collidableEntities){
      for(Entity otherEntity : this.allEntityList){
        if(!entity.equals(otherEntity)){
          entity.checkCollision(otherEntity);
        }
      }
    }
  }

  private void updateEntities() {
    checkForKeyPresses();
    applyGravity();
  }


  private void checkForKeyPresses() {
    ArrayList<Entity> playerEntities = allEntityMap.get(EntityType.PLAYER);
    if(!playerEntities.isEmpty()){
      Entity playerEntity = playerEntities.get(0);
      if (keyPressFunctions.isPlayerMovingRight()) {
        playerEntity.setXVel(MOVEMENT_SPEED);
      } else if (keyPressFunctions.isPlayerMovingLeft()) {
        playerEntity.setXVel(MOVEMENT_SPEED * -1);
      } else {
        playerEntity.setXVel(0);
      }
      if (keyPressFunctions.isPlayerJumping() && playerEntity.isGrounded()) {
        playerEntity.setYVel(JUMP_SPEED);
        //playerEntity.setOnGround(false);
      }
    }
    else{
      // TODO: Missing player exception?
    }
  }

  public void applyGravity() {
    for(Entity entity : this.gravityEntities){
      if(!entity.isGrounded()){
        entity.setYVel(entity.getYVel() - gravityFactor);
      }
    }
  }

  private void moveEntities(){
    //playerEntity.moveOneStep();
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
  public List<Entity> getAllEntitiesInMapAsList() {
    List<Entity> allEntityList = new ArrayList<>();
    Collection<ArrayList<Entity>> entityLists = this.allEntityMap.values();
    for (ArrayList<Entity> entityList : entityLists) {
      allEntityList.addAll(entityList);
    }
    return allEntityList;
  }

  public List<Entity> getAllEntitiesOfType(List<IEntityType> desiredEntityTypes) {
    List<Entity> allEntitiesOfType = new ArrayList<>();
    for (IEntityType type : desiredEntityTypes) {
      allEntitiesOfType.addAll(allEntityMap.get(type));
    }
    return allEntitiesOfType;
  }

}
