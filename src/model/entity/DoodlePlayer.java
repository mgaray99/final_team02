package model.entity;

import model.collision.CollisionDirections;
import model.collision.Direction;

public class DoodlePlayer extends Player {

  private static final double DOODLE_JUMP_SPEED = -0.4;
  private final double MOVEMENT_SPEED = 0.2;

  public DoodlePlayer(double x, double y) {
    super(x, y);
    this.setGrounded(false);
  }

  public void processCurrentCollision(IEntity otherEntity, CollisionDirections directions) {
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
}
