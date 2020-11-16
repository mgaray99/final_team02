package model.entity;

import model.collision.CollisionDirections;
import model.collision.Direction;

public class MarioPlayer extends Player {

  private static final double MARIO_JUMP_SPEED = -0.35;
  private static final double MARIO_MOVE_SPEED = 0.15;
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

  @Override
  public void updatePosition() {
    if (!this.getCurrentCollision().contains(Direction.BOTTOM)) {
      this.applyGravity();
    }
    if (leftKey == rightKey) {this.setXVel(0);}
    this.translateHitBox();
  }

}
