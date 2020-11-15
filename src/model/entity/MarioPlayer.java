package model.entity;

import model.collision.CollisionDirections;
import model.collision.Direction;

public class MarioPlayer extends Player {

  private static final double MARIO_JUMP_SPEED = -0.4;
  private static final double MARIO_MOVE_SPEED = 0.2;
  private boolean leftKey = false;
  private boolean rightKey = false;


  public MarioPlayer(double x, double y) {
    super(x, y);
  }



  @Override
  public void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey) {
    this.leftKey = leftKey;
    this.rightKey = rightKey;
    if (jumpKey && this.getGrounded()) {
      this.setYVel(MARIO_JUMP_SPEED);
      this.setGrounded(false);
    }
    if (rightKey) {this.setXVel(MARIO_MOVE_SPEED);}
    if (leftKey) {this.setXVel(-MARIO_MOVE_SPEED);}
    if (rightKey == leftKey) {this.setXVel(0);}
  }

  public void processCurrentCollision(IEntity otherEntity, CollisionDirections directions) {
    if (directions.contains(Direction.BOTTOM)) {
      this.setGrounded(true);
      this.getHitBox().setYBottom(otherEntity.getHitBox().getYTop());
      if (this.getYVel() > 0) {this.setYVel(0);}
    }

    if (directions.contains(Direction.TOP)) {
      this.getHitBox().setYTop(otherEntity.getHitBox().getYBottom());
      if (this.getYVel() < 0) {this.setYVel(0);}
    }

    if (directions.contains(Direction.RIGHT)) {
      this.getHitBox().setXRight(otherEntity.getHitBox().getXLeft());
      if (this.getXVel() > 0) {this.setXVel(0);}
    }

    if (directions.contains(Direction.LEFT)) {
      this.getHitBox().setXLeft(otherEntity.getHitBox().getXRight());
      if (this.getXVel() < 0) {this.setXVel(0);}
    }
  }

  @Override
  public void updatePosition() {
    if (!this.getCurrentCollision().contains(Direction.BOTTOM)) {
      this.applyGravity();
    }
    if (leftKey == rightKey) {this.setXVel(0);}
    this.translateHitBox();
  }

}
