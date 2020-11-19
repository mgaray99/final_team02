package model;

import model.configuration.ILevelLoader;
import model.entity.*;
import model.scroll.AutoScroller;
import api.model.scroll.Scroller;
import api.model.IKeyPressFunctions;
import api.model.ILevel;
import api.model.entity.IEntity;
import api.model.entity.IMovable;
import api.model.entity.ISpawner;
import api.model.entity.IWinnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Level implements ILevel {

  public static final int FRAMES_PER_SECOND = 60;
  public static final int MODIFIER_DURATION = 10;
  public static final double MODIFIER_VALUE = 1.5;
  public api.model.IKeyPressFunctions IKeyPressFunctions = new KeyPressFunctions();

  private Scroller scroller;
  private List<Player> playerList;
  private List<Enemy> enemyList;
  private List<IMovable> movableEntityList = new ArrayList<>();
  private List<PowerUp> powerUpList;
  private List<Block> blockList;
  private List<IEntity> entityList;
  private List<IWinnable> winnableList;
  private ILevelLoader loader;

  private int levelLength;
  private int levelWidth;
  private int score;
  private boolean levelLost;
  private boolean levelWon;
  private boolean isSaving;

  public Level(ILevelLoader ILevelLoader) {
    this.scroller = new AutoScroller(0,0, false);
    this.setOrResetLevel(ILevelLoader);
  }


  @Override
  public void step() {
    if (!IKeyPressFunctions.isPaused()) {
      this.removeEntitiesAsNeeded();
      this.spawnEntitiesAsNeeded();
      this.updateModifiers();
      this.updateVelocities();
      this.checkCollisions();
      this.updatePositions();
      this.checkWinLoseConditions();
      this.checkFellOutOfLevel();
      this.scroll();
    }
  }

  @Override
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

    for(IEntity entity : entitiesToRemove){
      this.removeEntity(entity);
    }
  }

  @Override
  public void spawnEntitiesAsNeeded() {
    for(Block block : this.blockList){
      if(block instanceof ISpawner){
        ISpawner spawner = (ISpawner)block;
        Optional<IEntity> optionalIEntity = spawner.attemptSpawnEntity();
        optionalIEntity.ifPresent(this::addEntity);
      }
    }
  }

  @Override
  public void updateModifiers() {
    for (Player player : playerList) {
      player.updateModifiers();
    }
  }

  @Override
  public void updateVelocities() {
    for (Player player : playerList) {
      player.updateVelocity(IKeyPressFunctions.isPlayerMovingLeft(), IKeyPressFunctions.isPlayerMovingRight(), IKeyPressFunctions.isPlayerJumping());
    }
    for (Enemy enemy : enemyList) {
      if(!playerList.isEmpty()){
        enemy.updateVelocity(playerList.get(0));
      }
    }
  }

  @Override
  public void checkCollisions(){
    for (IMovable movable : this.movableEntityList) {
      for(IEntity otherEntity : this.entityList){
        if(!movable.equals(otherEntity)){
          movable.checkCollision(otherEntity);
        }
      }
    }
  }

  @Override
  public void updatePositions(){
    for (Player player : this.playerList) {
      player.updatePosition();
      keepPlayerInBounds(player);
    }
    for (Enemy enemy : this.enemyList) {
      enemy.updatePosition();
    }
  }


  /**
   * Moves the entities in the level based on data on this level and the player
   */
  @Override
  public void scroll() {
    if(!playerList.isEmpty()){
      scroller.scroll(this, playerList.get(0));
      score += scroller.getScoreFromScroll();
    }
  }

  /**
   * Moves all entities in the list by <xChange, yChange>
   * @param xChange the amount to scroll the entity in the x direction
   * @param yChange the amount to scroll the entity in the y direction
   */
  @Override
  public void translateAllEntities(double xChange, double yChange) {
    entityList.forEach(entity -> translateEntity(entity, xChange, yChange));
  }

  /**
   * Moves the Entity
   * @param entity the Entity to be scrolled
   * @param xChange the amount to scroll the entity in the x direction
   * @param yChange the amount to scroll the entity in the y direction
   */
  @Override
  public void translateEntity(IEntity entity, double xChange, double yChange) {
    HitBox hitBox = entity.getHitBox();
    hitBox.translateX(xChange);
    hitBox.translateY(yChange);
  }

  /**
   * Sets the scroller of the level equal to the Scroller passed in
   * @param configScroller the Scroller that will serve as this level's new Scroller
   */
  @Override
  public void setScroller(Scroller configScroller) {
    scroller = configScroller;
  }


  @Override
  public int getLevelLength() {
    return this.levelLength;
  }

  @Override
  public int getLevelWidth() {
    return this.levelWidth;
  }

  @Override
  public void setOrResetLevel(ILevelLoader ILevelLoader){
    loader = ILevelLoader;
    score = 0;

    this.playerList = ILevelLoader.getCopyOfPlayerList();
    this.enemyList = ILevelLoader.getCopyOfEnemyList();
    this.movableEntityList = ILevelLoader.getCopyOfMovableEntityList();
    this.blockList = ILevelLoader.getCopyOfBlockList();
    this.powerUpList = ILevelLoader.getCopyOfPowerUpList();
    this.winnableList = ILevelLoader.getCopyOfWinnableList();
    this.entityList = ILevelLoader.getCopyOfEntityList();
    this.levelLength = ILevelLoader.getLevelLength();
    this.levelWidth = ILevelLoader.getLevelWidth();
  }

  @Override
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
    if (entity instanceof IWinnable) {
      this.winnableList.add((IWinnable)entity);
    }
  }

  @Override
  public void removeEntity(IEntity entity) {
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
    if (entity instanceof IWinnable) {
      this.winnableList.remove(entity);
    }
  }

  @Override
  public Optional<IEntity> getEntityAt(int xCoordinate, int yCoordinate) {
    for(IEntity entity : entityList){
      if((int)entity.getHitBox().getXLeft() == xCoordinate && (int)entity.getHitBox().getYTop() == yCoordinate){
        return Optional.of(entity);
      }
    }
    return Optional.empty();
  }

  /**
   * Makes sure that the player is within bounds (i.e. xleft > 0 and xright <
   * scroller.NUM_BLOCKS or the num blocks wide and tall on display) and if
   * not, places that player within bounds
   *
   * @param player the player whose bounds will be checked
   */
  @Override
  public void keepPlayerInBounds(Player player) {
      if (player.getHitBox().getXLeft() < 0) {
        player.getHitBox().setXLeft(0);
      }
      else if (player.getHitBox().getXRight() > scroller.NUM_BLOCKS) {
        player.getHitBox().setXRight(scroller.NUM_BLOCKS);
      }
  }

  @Override
  public void checkWinLoseConditions(){
    if (playerList.size() == 0) {
      setLevelLost(true);
    }
    else{
      if(!winnableList.isEmpty()){
        int winCount = 0;
        for(IWinnable winnable : winnableList){
          if(winnable.getHasWon()){
            winCount++;
          }
        }
        if(winCount >= winnableList.size()){
          setLevelWon(true);
        }
      }
    }
  };


  /**
   * Checks to see if the player has lost the level (i.e. fell through
   * bottom of screen) and if so resets the level
   */
  @Override
  public void checkFellOutOfLevel() {
    if (playerList.size() > 0) {
      Player player = playerList.get(0);
      if (player.getHitBox().getYTop() > scroller.NUM_BLOCKS) {
        setLevelLost(true);
      }
    }
  }

  /**
   * Handles the situation where the player has fallen off of the screen
   */
  @Override
  public void reinitialize() {
    loader.reinitialize();
    setOrResetLevel(loader);

    scroller.reset();
    levelLost = false;
    levelWon = false;
  }

  @Override
  public void setLevelWon(boolean isLevelWon) {
    this.levelWon = isLevelWon;
  }

  @Override
  public void setLevelLost(boolean isLevelLost) {
    this.levelLost = isLevelLost;
  }

  @Override
  public boolean isLevelWon(){
    return levelWon;
  }

  @Override
  public IKeyPressFunctions getKeyPressFunctions() {
    return IKeyPressFunctions;
  }

  @Override
  public List<IEntity> getCopyOfEntityList() {
    return new ArrayList<>(entityList);
  }

  @Override
  public List<IEntity> getAllEntities() { return entityList; }

  @Override
  public List<Player> getPlayerList() {return playerList;}

  /**
   * Reveals the score of the player within the level
   * @return score
   */
  @Override
  public int getScore() {
    return score;
  }

  /**
   * Reveals if the level has lost
   * @return levelLost
   */
  @Override
  public boolean isLevelLost() {
    return levelLost;
  }

  /**
   * Reveals if we're saving the game
   * @return isSaving
   */
  @Override
  public boolean isSaving() {
    return isSaving;
  }

  /**
   * Sets a variable to show if we're saving the game
   * @param save the indicator for if we're saving
   */
  @Override
  public void setIsSaving(boolean save) {
    isSaving = save;
  }

}
