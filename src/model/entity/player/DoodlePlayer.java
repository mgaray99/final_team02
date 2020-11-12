package model.entity.player;

import model.collision.CollisionDirection;
import model.entity.IDamageable;
import model.entity.IEmpowering;
import model.entity.IEntity;
import model.entity.ISpawner;

public class DoodlePlayer extends Player2 {

  private static final double DOODLE_JUMP_SPEED = 6;

  public DoodlePlayer(double x, double y) {
    super(x, y);
  }

  public void checkCollision(IEntity entity) {
    CollisionDirection collision = hitBox.getCollisionDirection(entity.getHitBox());
    if (collision != CollisionDirection.NONE) {
      this.setCurrentCollision(collision);
    }

    if (collision == CollisionDirection.TOP || this.getYVel() > 0) {
      this.jump(DOODLE_JUMP_SPEED);
    }
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
}
