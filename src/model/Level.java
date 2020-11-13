package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.configuration.LevelLoader;
import model.entity.*;
import model.scroll.AutoScroller;
import model.scroll.Scroller;
import org.jetbrains.annotations.Nullable;

public class Level {

  public static final int FRAMES_PER_SECOND = 60;
  public static final int MODIFIER_DURATION = 10;
  public static final double MODIFIER_VALUE = 1.5;
  public static final double DEFAULT_GRAVITY_MODIFIER = 1;
  public KeyPressFunctions keyPressFunctions = new KeyPressFunctions();

  private Scroller scroller;
  private final double MOVEMENT_SPEED = 0.2;
  private final float JUMP_SPEED = -0.4f;
  private final float GRAVITY_FACTOR = 0.015f;
  private final double ENEMY_MOVEMENT_SPEED = 0.1;
  private static final int STARTX = 50;
  private static final int STARTY = 600;
  private static final int START_HEALTH = 10;

  private static final int NO_SCROLL = -1;
  private static final int ALWAYS_SCROLL = 0;
  private static final String GENERATION_PATH = "resources/game_configuration/auto/autodoodle.xml";

  private List<Player> playerList;
  private List<Enemy> enemyList;
  private List<IMovable> movableEntityList = new ArrayList<>();
  private List<PowerUp> powerUpList;
  private List<Block> blockList;
  private List<IEntity> entityList;

  private int levelLength;
  private int levelWidth;
  private boolean levelLost;
  private boolean levelWon;

  public Level(LevelLoader levelLoader) {
    this.setOrResetLevel(levelLoader);
  }


  public void step() {
    if (!keyPressFunctions.isPaused()) {
      this.removeEntitiesAsNeeded();
      this.spawnEntitiesAsNeeded();
      this.updateModifiers();
      this.checkForKeyPresses();
      this.applyGravity();
      this.checkIfEnemiesMove();
      this.checkCollisions();
      this.moveEntities();
      this.checkWinCondition();
      this.scroll();
    }
  }

  public void removeEntitiesAsNeeded() {
    List<IEntity> entitiesToRemove = new ArrayList<>();

    for (Player player : playerList) {
      if (player.isDead()) {
        entitiesToRemove.add(player);
        this.setLevelLost(true);
      }
    }

    for (IMovable movable : this.movableEntityList) {
      if (movable.isDead()) {
        entitiesToRemove.add(movable);
      }
    }

    for(PowerUp powerUp : this.powerUpList){
      if(powerUp.hasAppliedModifier()){
        entitiesToRemove.add(powerUp);
      }
    }

    // removes all entities from the level in the removal list
    // this should be done only if all entity iterations in this method have been completed
    for(IEntity entity : entitiesToRemove){
      this.removeEntity(entity);
    }
  }

  public void spawnEntitiesAsNeeded() {
    for(Block block : this.blockList){
      if(block instanceof ISpawner){
        ISpawner spawner = (ISpawner)block;
        Optional<IEntity> optionalIEntity = spawner.attemptSpawnEntity();
        optionalIEntity.ifPresent(this::addEntity);
      }
    }
  }

  public void updateModifiers() {
    for (Player player : playerList) {
      player.updateModifiers();
    }
  }

  private void checkForKeyPresses() {
    if(!playerList.isEmpty()){
      Player playerEntity = playerList.get(0);

      double movementSpeedModifier = 1;
      if(playerEntity.getModifiers().containsKey(Modifier.ModifierType.MOVEMENT_SPEED)){
        movementSpeedModifier = playerEntity.getModifiers().get(Modifier.ModifierType.MOVEMENT_SPEED).getValue();
      }
      double jumpSpeedModifier = 1;
      if(playerEntity.getModifiers().containsKey(Modifier.ModifierType.JUMP_SPEED)){
        jumpSpeedModifier = playerEntity.getModifiers().get(Modifier.ModifierType.JUMP_SPEED).getValue();
      }
      if (keyPressFunctions.isPlayerMovingRight()) {
        playerEntity.setXVel(this.MOVEMENT_SPEED * movementSpeedModifier);
      } else if (keyPressFunctions.isPlayerMovingLeft()) {
        playerEntity.setXVel(this.MOVEMENT_SPEED * -1 * movementSpeedModifier);
      } else {
        playerEntity.setXVel(0);
      }
      if (keyPressFunctions.isPlayerJumping() && playerEntity.getGrounded()) {
        playerEntity.jump(this.JUMP_SPEED * jumpSpeedModifier);
      }
    }
  }

  private void applyGravity() {
    for (Player player : playerList) {
      double gravityModifier = DEFAULT_GRAVITY_MODIFIER;
      if(player.getModifiers().containsKey(Modifier.ModifierType.GRAVITY)){
        gravityModifier = player.getModifiers().get(Modifier.ModifierType.GRAVITY).getValue();
      }
      player.applyGravity(this.GRAVITY_FACTOR * gravityModifier);
    }
    for (Enemy enemy : enemyList) {
      enemy.applyGravity(this.GRAVITY_FACTOR);
    }
  }

  private void checkIfEnemiesMove() {
    for (Player player : playerList) {
      //player.moveOneStep();
      for(Enemy enemy : enemyList){
        if(player.getHitBox().getXLeft() < enemy.getHitBox().xLeft){
          enemy.setXVel(this.ENEMY_MOVEMENT_SPEED * -1);
        }
        else if(player.getHitBox().getXLeft() > enemy.getHitBox().xLeft){
          enemy.setXVel(this.ENEMY_MOVEMENT_SPEED);
        }
        else{
          enemy.setXVel(0);
        }
        //enemy.moveOneStep();
      }
    }
  }

  public void checkCollisions(){
    for (IMovable movable : this.movableEntityList) {
      for(IEntity otherEntity : this.entityList){
        if(!movable.equals(otherEntity)){
          movable.checkFutureCollision(otherEntity);
        }
      }
    }
  }

  private void moveEntities(){
    for (IMovable movable : this.movableEntityList) {
      movable.moveOneStep();
    }
  }


  /**
   * Moves the entities in the level based on data on this level and the player
   */
  private void scroll() {
    if(!playerList.isEmpty()){
      scroller.scroll(this, playerList.get(0));
    }
  }

  /**
   * Sets the scroller of the level equal to the Scroller passed in
   * @param configScroller the Scroller that will serve as this level's new Scroller
   */
  public void setScroller(Scroller configScroller) {
    scroller = configScroller;
  }


  public int getLevelLength() {
    return this.levelLength;
  }

  public int getLevelWidth() {
    return this.levelWidth;
  }

  public void setOrResetLevel(LevelLoader levelLoader){
    this.playerList = levelLoader.getCopyOfPlayerList();
    this.enemyList = levelLoader.getCopyOfEnemyList();
    this.movableEntityList = levelLoader.getCopyOfMovableEntityList();
    this.blockList = levelLoader.getCopyOfBlockList();
    this.powerUpList = levelLoader.getCopyOfPowerUpList();
    this.entityList = levelLoader.getCopyOfEntityList();
    this.levelLength = levelLoader.getLevelLength();
    this.levelWidth = levelLoader.getLevelWidth();
    this.scroller = new AutoScroller(0,0, false);
  }

  public void addEntity(IEntity entity) {
    if (entity!=null) {
      this.entityList.add(entity);
    }

    if (entity instanceof Block) {
      this.blockList.add((Block)entity);
    }
    if (entity instanceof Enemy) {
      this.enemyList.add((Enemy)entity);
    }
    if (entity instanceof IMovable) {
      this.movableEntityList.add((IMovable)entity);
    }
    if (entity instanceof Player) {
      this.playerList.add((Player)entity);
    }
    if (entity instanceof PowerUp) {
      PowerUp powerUp = (PowerUp) entity;
      powerUp.setRandomModifier(MODIFIER_VALUE, MODIFIER_DURATION * FRAMES_PER_SECOND);
      this.powerUpList.add(powerUp);
    }
  }

  private void removeEntity(IEntity entity) {
    if(entity != null){
      this.entityList.remove(entity);
    }
    if (entity instanceof Block) {
      this.blockList.remove(entity);
    }
    if (entity instanceof Enemy) {
      this.enemyList.remove(entity);
    }
    if (entity instanceof Player) {
      this.playerList.remove(entity);
    }
    if (entity instanceof PowerUp) {
      this.powerUpList.remove(entity);
    }
    if (entity instanceof IMovable) {
      this.movableEntityList.remove(entity);
    }
  }

  @Nullable
  public IEntity getEntityAt(int xCoordinate, int yCoordinate) {
    for(IEntity entity : entityList){
      if((int)entity.getHitBox().getXLeft() == xCoordinate && (int)entity.getHitBox().getYTop() == yCoordinate){
        return entity;
      }
    }
    return null;
  }

  private void checkWinCondition(){
    if (playerList.size() == 0) {
      setLevelLost(true);
    }
  };


  void setLevelWon(boolean isLevelWon) {
    this.levelWon = isLevelWon;
  }

  void setLevelLost(boolean isLevelLost) {
    this.levelLost = isLevelLost;
  }

  public KeyPressFunctions getKeyPressFunctions() {
    return keyPressFunctions;
  }

  public List<IEntity> getCopyOfEntityList() {
    return new ArrayList<>(entityList);
  }

  public List<IEntity> getAllEntities() { return entityList; }

  public List<Player> getPlayerList() {return playerList;}

}
