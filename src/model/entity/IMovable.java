package model.entity;

import model.HitBox;
import model.collision.CollisionDirections;

public interface IMovable extends IEntity {

  void processCurrentCollision(IEntity entity, CollisionDirections collision);

  default void applyGravity(double levelGravityFactor) {
    if(!this.getGrounded()){
      if (this.getGracePeriodBeforeFalling() > 0) {
        this.subtractFromGracePeriodBeforeFalling();
      } else {
        this.setYVel(this.getYVel() + levelGravityFactor);
      }
    }
  }

  boolean isDead();

  void checkFutureCollision(IEntity entity);

  void moveOneStep();

  default void jump(double jumpSpeed){
    this.setYVel(jumpSpeed);
    this.setGrounded(false);
  }

  boolean getGrounded();

  void setGrounded(boolean grounded);

  int getGracePeriodBeforeFalling();

  void resetGracePeriodBeforeFalling();

  void subtractFromGracePeriodBeforeFalling();

  // Expected velocity methods that can be implemented by IEntity
  void setXVel(double xVel);

  double getXVel();

  HitBox getHitBox();

  void setYVel(double yVel);

  double getYVel();

  @Override
  String getType();
}
