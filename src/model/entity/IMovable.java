package model.entity;

import model.HitBox;

public interface IMovable extends IEntity {

  //void processCurrentCollision(IEntity entity, CollisionDirections collision);

  //void applyGravity(double levelGravityFactor);
  /*default void applyGravity(double levelGravityFactor) {
    if(!this.getGrounded()){
      //if (this.getGracePeriodBeforeFalling() > 0) {
      //  this.subtractFromGracePeriodBeforeFalling();
      //} else {
        this.setYVel(this.getYVel() + levelGravityFactor);
      //}
    }
  }*/

  default void updatePosition(){
    this.getHitBox().translateX(this.getXVel());
    this.getHitBox().translateY(this.getYVel());
  }

  boolean isDead();

  void checkFutureCollision(IEntity entity);

  //void moveOneStep();

  //void updateVelocity();

  /*default void jump(double jumpSpeed){
    this.setYVel(jumpSpeed);
    this.setGrounded(false);
  }*/

  //void jump(double jumpSpeed);

  boolean getGrounded();

  void setGrounded(boolean grounded);

  // Expected velocity methods that can be implemented by IEntity
  void setXVel(double xVel);

  double getXVel();

  HitBox getHitBox();

  void setYVel(double yVel);

  double getYVel();

  @Override
  String getType();
}
