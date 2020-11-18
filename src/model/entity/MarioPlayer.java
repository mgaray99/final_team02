package model.entity;

import model.collision.Direction;

public class MarioPlayer extends Player {

  private static final double MARIO_JUMP_SPEED = -0.35;
  private static final double MAX_MOVE_SPEED = 0.15;
  private static final double MOVE_ACCELERATION = 0.015;
  private boolean GRACE_BEFORE_JUMP = true;

  public MarioPlayer(double x, double y) {
    super(x, y);
  }



  @Override
  public void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey) {
    if (jumpKey && this.getGrounded()) {
      if (GRACE_BEFORE_JUMP) {
        this.setYVel(-MOVE_ACCELERATION);
        GRACE_BEFORE_JUMP = false;
      }
      else {
        this.setYVel(MARIO_JUMP_SPEED);
        this.setGrounded(false);
        GRACE_BEFORE_JUMP = true;
      }
    }
    else {
      GRACE_BEFORE_JUMP = true;
    }
    if (rightKey && this.getXVel() < MAX_MOVE_SPEED) {
      accelerateRight();
    }
    if (leftKey && this.getXVel() > -MAX_MOVE_SPEED) {
      accelerateLeft();
    }
    if (rightKey == leftKey && this.getXVel() < 0) {
      accelerateRight();
    }
    if (rightKey == leftKey && this.getXVel() > 0) {
      accelerateLeft();
    }

    if (Math.abs(this.getXVel()) < MOVE_ACCELERATION) {
      this.setXVel(0);
    }
  }

  private void accelerateRight() {
    this.setXVel(this.getXVel() + MOVE_ACCELERATION);
  }

  private void accelerateLeft() {
    this.setXVel(this.getXVel() - MOVE_ACCELERATION);
  }

  @Override
  public void updatePosition() {
    if (!this.getCurrentCollision().contains(Direction.BOTTOM)) {
      this.applyGravity();
    }
    this.translateHitBox();
  }

}
