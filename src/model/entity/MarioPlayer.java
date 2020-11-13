package model.entity;

import model.collision.CollisionDirections;
import model.collision.Direction;

public class MarioPlayer extends Player {



  public MarioPlayer(double x, double y) {
    super(x, y);
  }

  public void processCurrentCollision(IEntity entity, CollisionDirections collision){
    if (collision.contains(Direction.BOTTOM)) {
      System.out.print("Bottom");
      this.setGrounded(true);
      this.resetGracePeriodBeforeFalling();
      if (this.getYVel() > 0) {
        this.setCanMoveUp(false);
      }
      //this.getHitBox().setYTop(entity.getHitBox().getYTop() - this.getHitBox().getYSize());
    }
    if (collision.contains(Direction.TOP)) {
      System.out.print("Top");
      if (this.getYVel() < 0) {
        this.setCanMoveUp(false);
      }
      //this.getHitBox().setYTop(entity.getHitBox().getYBottom());
    }
    if (collision.contains(Direction.LEFT)) {
      System.out.print("Left");
      if (this.getXVel() != 0) {
        this.setCanMoveRight(false);
      }
      //this.getHitBox().setXLeft(entity.getHitBox().getXRight());
    }
    if (collision.contains(Direction.RIGHT)) {
      System.out.print("Right");
      if (this.getXVel() > 0) {
        this.setCanMoveRight(false);
      }
      //this.getHitBox().setXLeft(entity.getHitBox().getXLeft() - this.getHitBox().getXSize());
    }
  }

  public void checkFutureCollision(IEntity entity) {
    CollisionDirections collision = hitBox.getFutureCollisionDirection(entity.getHitBox(), this.getXVel(), this.getYVel());

    //this if statement is for testing - will be removed
    //if (!collision.contains(CollisionDirection.NONE)) {
    //    yVel = 0;
    //}
    if (entity instanceof IDamageable && collision.doesCollide() && this
        .canApplyDamage(collision)) {
      this.attemptApplyDamage((IDamageable) entity, collision);
    }
    if (entity instanceof IEmpowering && collision.doesCollide()) {
      IEmpowering empowering = (IEmpowering) entity;
      if (!empowering.hasAppliedModifier()) {
        this.applyModifier(empowering.getModifier());
        empowering.setHasAppliedModifier(true);
      }
    }
    if (entity instanceof ISpawner && collision.doesCollide()) {
      ISpawner spawner = (ISpawner) entity;
      spawner.attemptCreateAndAddSpawn(collision);
    }
    processCurrentCollision(entity, collision);
  }

  public void checkCollision(IEntity entity) {}
}
