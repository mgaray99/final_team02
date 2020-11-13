package model.entity;

import model.collision.CollisionDirection;

public class DoodlePlayer extends Player {

  private static final double DOODLE_JUMP_SPEED = -6;

  public DoodlePlayer(double x, double y) {
    super(x, y);
    this.setGrounded(false);
  }

  public void checkCollision(IEntity entity) {
    CollisionDirection collision = hitBox.getCollisionDirection(entity.getHitBox());
    if (collision != CollisionDirection.NONE) {
      this.setCurrentCollision(collision);
    }

    /*if (collision == CollisionDirection.BOTTOM || this.getYVel() < 0) {
      this.jump(DOODLE_JUMP_SPEED);
    }*/
    this.checkGravity(entity, collision);
    if (entity instanceof IDamageable && collision != CollisionDirection.NONE && this
        .canApplyDamage(collision)) {
      this.attemptApplyDamage((IDamageable) entity, collision);
    }
    if (entity instanceof IEmpowering && collision != CollisionDirection.NONE) {
      IEmpowering empowering = (IEmpowering) entity;
      if (!empowering.hasAppliedModifier()) {
        this.applyModifier(empowering.getModifier());
        empowering.setHasAppliedModifier(true);
      }
    }
    if (entity instanceof ISpawner && collision != CollisionDirection.NONE) {
      ISpawner spawner = (ISpawner) entity;
      spawner.attemptCreateAndAddSpawn(collision);
    }
  }


  @Override
  public void checkGravity(IEntity entity, CollisionDirection collision){
    if (collision == CollisionDirection.BOTTOM) {
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

}
