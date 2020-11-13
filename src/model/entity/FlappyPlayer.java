package model.entity;

import model.collision.CollisionDirections;

public class FlappyPlayer extends Player {

  public FlappyPlayer(double x, double y) {
    super(x, y);
  }

  @Override
  public void processCurrentCollision(IEntity entity, CollisionDirections collision) {

  }

  public void checkFutureCollision(IEntity entity) {
    CollisionDirections collision = hitBox.getFutureCollisionDirection(entity.getHitBox(), this.getXVel(), this.getYVel());


    this.processCurrentCollision(entity, collision);
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
  }

  public void checkCollision(IEntity entity){}
}
