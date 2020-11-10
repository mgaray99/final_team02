package model;

import java.util.List;
import model.configuration.LevelLoader;
import model.entity.Block;
import model.entity.Enemy;
import model.entity.IEntity;
import model.entity.Player;
import model.entity.PowerUp;
import model.scroll.DoodleGenerationScroller;
import model.scroll.Scroller;
import org.jetbrains.annotations.Nullable;

public class Level {

  public KeyPressFunctions keyPressFunctions = new KeyPressFunctions();

  private Scroller scroller;
  private final double MOVEMENT_SPEED = 0.2;
  private final float JUMP_SPEED = -0.4f;
  private final float gravityFactor = 0.015f;
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

  public Level(LevelLoader levelLoader) {

    this.playerList = levelLoader.getPlayerList();
    this.enemyList = levelLoader.getEnemyList();
    this.blockList = levelLoader.getBlockList();
    this.powerUpList = levelLoader.getPowerUpList();
    this.entityList = levelLoader.getEntityList();
    this.levelLength = levelLoader.getLevelLength();
    this.levelWidth = levelLoader.getLevelWidth();

    scroller = new DoodleGenerationScroller(NO_SCROLL, NO_SCROLL, 3, NO_SCROLL, -7, GENERATION_PATH);
  }

  public int getLevelLength() {
    return this.levelLength;
  }

  public int getLevelWidth() {
    return this.levelWidth;
  }


  private void addEntity(IEntity entity) {
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
      applyGravity();
      moveEntities();
      checkCollisions();
      checkWinCondition();
      scroll();
    }
  }


  public void checkCollisions(){
    for(Player player : this.playerList){
      for(IEntity otherEntity : this.entityList){
        if(!player.equals(otherEntity)){
          player.checkCollision(otherEntity);
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
        playerEntity.setYVel(JUMP_SPEED);
        playerEntity.setGrounded(false);
      }
    }
    else{
      // TODO: Missing player exception?
    }
  }

  public void applyGravity() {
    //note: add enemies to this later
    for(Player player : this.playerList){
      if(!player.getGrounded()){
        if (player.getGracePeriodBeforeFalling()) {
          player.setGracePeriodBeforeFalling(false);
        } else {
          player.setYVel(player.getYVel() + gravityFactor);
        }
      }
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
   * Moves the entities in the level based on data from the List<Entity> and the player
   */
  private void scroll() {
    scroller.scroll(entityList, playerList.get(0));
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

  public List<IEntity> getAllEntities() {
    return entityList;
  }

  public List<Player> getPlayerList() {return playerList;}

}
