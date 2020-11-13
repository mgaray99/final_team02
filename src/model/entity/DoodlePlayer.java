package model.entity;

import model.collision.CollisionDirections;
import model.collision.Direction;

public class DoodlePlayer extends Player {

  private static final double DOODLE_JUMP_SPEED = -6;

  public DoodlePlayer(double x, double y) {
    super(x, y);
    this.setGrounded(false);
  }

  public void checkFutureCollision(IEntity entity) {
    CollisionDirections collision = hitBox.getFutureCollisionDirection(entity.getHitBox(), this.getXVel(), this.getYVel());

    /*if (collision == CollisionDirection.BOTTOM || this.getYVel() < 0) {
      this.jump(DOODLE_JUMP_SPEED);
    }*/
    this.processCurrentCollision(entity, collision);
    if (entity instanceof IDamageable && !collision.isEmpty() && this
        .canApplyDamage(collision)) {
      this.attemptApplyDamage((IDamageable) entity, collision);
    }
    if (entity instanceof IEmpowering && !collision.isEmpty()) {
      IEmpowering empowering = (IEmpowering) entity;
      if (!empowering.hasAppliedModifier()) {
        this.applyModifier(empowering.getModifier());
        empowering.setHasAppliedModifier(true);
      }
    }
    if (entity instanceof ISpawner && !collision.isEmpty()) {
      ISpawner spawner = (ISpawner) entity;
      spawner.attemptCreateAndAddSpawn(collision);
    }
  }


  @Override
  public void processCurrentCollision(IEntity entity, CollisionDirections collision){
    if (collision.contains(Direction.BOTTOM)) {
      System.out.print("Bottom");
      this.setGrounded(false);
      this.resetGracePeriodBeforeFalling();
      if (this.getYVel() > 0) {
        this.setYVel(DOODLE_JUMP_SPEED);
      }
      this.getHitBox().setYTop(entity.getHitBox().getYTop() - this.getHitBox().getYSize());
    }
    /*if (collision == CollisionDirection.TOP) {
      System.out.print("Top");
      if (this.getYVel() < 0) {
        this.setYVel(0);
      }
      this.getHitBox().setYTop(entity.getHitBox().getYBottom());
    }
    if (collision == CollisionDirection.LEFT) {
      System.out.print("Left");
      if (this.getXVel() < 0) {
        this.setXVel(0);
      }
      this.getHitBox().setXLeft(entity.getHitBox().getXRight());
    }
    if (collision == CollisionDirection.RIGHT) {
      System.out.print("Right");
      if (this.getXVel() > 0) {
        this.setXVel(0);
      }
      this.getHitBox().setXLeft(entity.getHitBox().getXLeft() - this.getHitBox().getXSize());
    }*/
  }

  public void checkCollision(IEntity entity){};

}
