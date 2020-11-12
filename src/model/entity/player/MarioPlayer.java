package model.entity.player;

import model.collision.CollisionDirection;
import model.entity.IDamageable;
import model.entity.IEmpowering;
import model.entity.IEntity;
import model.entity.ISpawner;

public class MarioPlayer extends Player2 {


  public MarioPlayer(double x, double y) {
    super(x, y);
  }

  public void checkCollision(IEntity entity) {
    CollisionDirection collision = hitBox.getCollisionDirection(entity.getHitBox());
    if (collision != CollisionDirection.NONE) {
      this.setCurrentCollision(collision);
    }

    if (collision == CollisionDirection.LEFT || collision == CollisionDirection.RIGHT) {
      this.resetGracePeriodBeforeSidewaysMovement();
    }
    //this if statement is for testing - will be removed
    //if (!collision.contains(CollisionDirection.NONE)) {
    //    yVel = 0;
    //}
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
