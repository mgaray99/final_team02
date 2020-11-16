package model.entity;

import model.collision.CollisionDirections;

public class FlappyPlayer extends Player {

  private static final double FLAPPY_JUMP_SPEED = -0.25;

  public FlappyPlayer(double x, double y) {
    super(x, y);
  }

  private boolean heldDownJumpKey = false;
  private boolean immobilized = false;

  @Override
  public void processCurrentCollision(IEntity otherEntity, CollisionDirections directions) {
    if (directions.doesCollide()) {
      this.immobilized = true;
    }
  }

  @Override
  public void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey) {
    if (immobilized) {return;}
    if (jumpKey && !heldDownJumpKey)  {
      this.setYVel(FLAPPY_JUMP_SPEED);
      heldDownJumpKey = true;
    } else if (!jumpKey){
      heldDownJumpKey = false;
    }
  }

  public void updatePosition(){
    this.applyGravity();
    this.translateHitBox();
  }
}
