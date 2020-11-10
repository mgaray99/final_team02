package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.configuration.LevelLoader;
import model.entity.*;
import model.scroll.AutoScroller;
import model.scroll.Scroller;
import org.jetbrains.annotations.Nullable;

public class Level {

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
  private static final String GENERATION_PATH = "resources/game_configuration/auto/autodoodle.txt";

  private List<Player> playerList;
  private List<Enemy> enemyList;
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

  public int getLevelLength() {
    return this.levelLength;
  }

  public int getLevelWidth() {
    return this.levelWidth;
  }

  public void setOrResetLevel(LevelLoader levelLoader){
    this.playerList = levelLoader.getCopyOfPlayerList();
    this.enemyList = levelLoader.getCopyOfEnemyList();
    this.blockList = levelLoader.getCopyOfBlockList();
    this.powerUpList = levelLoader.getCopyOfPowerUpList();
    this.entityList = levelLoader.getCopyOfEntityList();
    this.levelLength = levelLoader.getLevelLength();
    this.levelWidth = levelLoader.getLevelWidth();
    scroller = new AutoScroller(0,0);
  }

  public void addEntity(IEntity entity) {
    if (entity!=null) {
      entityList.add(entity);
    }

    if (entity instanceof Block) {
      blockList.add((Block)entity);
    }
    if (entity instanceof Enemy) {
      enemyList.add((Enemy)entity);
    }
    if (entity instanceof Player) {
      playerList.add((Player)entity);
    }
    if (entity instanceof PowerUp) {
      powerUpList.add((PowerUp)entity);
    }
  }

  private void removeEntity(IEntity entity) {
    entityList.remove(entity);
    if (entity instanceof Block) {
      blockList.remove(entity);
    }
    if (entity instanceof Enemy) {
      enemyList.remove(entity);
    }
    if (entity instanceof Player) {
      playerList.remove(entity);
    }
    if (entity instanceof PowerUp) {
      powerUpList.remove(entity);
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

  public void step() {
    if (!keyPressFunctions.isPaused()) {
      checkForKeyPresses();
      updateEntities();
      moveEntities();
      checkCollisions();
      checkWinCondition();
      scroll();
    }
  }


  public void checkCollisions(){
    if(!playerList.isEmpty()){
      Player player = playerList.get(0);
      for(IEntity otherEntity : this.entityList){
        if(!player.equals(otherEntity)){
          player.checkCollision(otherEntity);
        }
      }
    }
    for(Enemy enemy : this.enemyList){
      for(IEntity otherEntity : this.entityList){
        if (!enemy.equals(otherEntity)) {
          enemy.checkCollision(otherEntity);
        }
      }
    }
  }


  private void checkForKeyPresses() {
    if(!playerList.isEmpty()){
      Player playerEntity = playerList.get(0);
      if (keyPressFunctions.isPlayerMovingRight()) {
        playerEntity.setXVel(MOVEMENT_SPEED);
      } else if (keyPressFunctions.isPlayerMovingLeft()) {
        playerEntity.setXVel(MOVEMENT_SPEED * -1);
      } else {
        playerEntity.setXVel(0);
      }
      if (keyPressFunctions.isPlayerJumping() && playerEntity.getGrounded()) {
        playerEntity.jump(JUMP_SPEED);
      }
    }
    else{
      // TODO: Missing player exception?
    }
  }

  public void updateEntities() {
    // a list of entities to remove if necessary
    List<IEntity> entitiesToRemove = new ArrayList<>();

    if(!this.playerList.isEmpty()){
      Player player = playerList.get(0);
      player.updateGravity(this.GRAVITY_FACTOR);
      if(player.isDead()){
        //this.removeEntity(player);
        System.out.println("Player should be dead");
        this.setLevelLost(true);
      }
    }
    for (Enemy enemy : this.enemyList) {
      enemy.updateGravity(this.GRAVITY_FACTOR);
      if (enemy.isDead()) {
        entitiesToRemove.add(enemy);
      }
    }

    // removes all entities from the level in the removal list
    // this should be done only if all entity iterations in this method have been completed
    for(IEntity entity : entitiesToRemove){
      removeEntity(entity);
    }
  }



  private void moveEntities(){
    if(!playerList.isEmpty()) {
      Player playerEntity = playerList.get(0);
      playerEntity.moveOneStep();
      if(!enemyList.isEmpty()){
        for(Enemy enemy : enemyList){
          if(playerEntity.getHitBox().getXLeft() < enemy.getHitBox().xLeft){
            enemy.setXVel(ENEMY_MOVEMENT_SPEED * -1);
          }
          else if(playerEntity.getHitBox().getXLeft() > enemy.getHitBox().xLeft){
            enemy.setXVel(ENEMY_MOVEMENT_SPEED);
          }
          enemy.moveOneStep();
        }
      }
    }
  }

  /**
   * Moves the entities in the level based on data on this level and the player
   */
  private void scroll() {
    scroller.scroll(this, playerList.get(0));
  }

  /**
   * Sets the scroller of the level equal to the Scroller passed in
   * @param configScroller the Scroller that will serve as this level's new Scroller
   */
  public void setScroller(Scroller configScroller) {
    scroller = configScroller;
  }

  private void checkWinCondition(){};


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
    return new ArrayList<IEntity>(entityList);
  }

  public List<Player> getPlayerList() {return playerList;}

}
