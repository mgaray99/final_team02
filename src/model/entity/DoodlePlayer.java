package model.entity;

import model.collision.Direction;
import api.model.collision.ICollisionHandler;
import api.model.entity.IEntity;

public class DoodlePlayer extends Player {

  private static final double DOODLE_JUMP_SPEED = -0.4;
  private static final double POWER_JUMP_SPEED = -0.6;
  private final double MOVEMENT_SPEED = 0.2;

  public DoodlePlayer(double x, double y) {
    super(x, y);
    this.setGrounded(false);
  }

  @Override
  public void processCurrentCollision(IEntity otherEntity, ICollisionHandler directions) {
    if (directions.contains(Direction.BOTTOM) && this.getYVel() > 0) {
      this.getHitBox().setYBottom(otherEntity.getHitBox().getYTop());
    }
  }

  @Override
  public void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey) {
    if (rightKey && this.getXVel() <= 0) {this.setXVel(MOVEMENT_SPEED);}
    if (leftKey && this.getXVel() >= 0) {this.setXVel(-MOVEMENT_SPEED);}
    if (rightKey == leftKey) {this.setXVel(0);}
    this.applyGravity();
  }

  @Override
  public void updatePosition() {
    if (this.getCurrentCollision().contains(Direction.BOTTOM) && this.getYVel() > 0) {
      this.setYVel(DOODLE_JUMP_SPEED);
    }
    this.translateHitBox();
  }

  @Override
  public void applyModifier(Modifier modifier){
    this.setYVel(POWER_JUMP_SPEED);
  }
}
