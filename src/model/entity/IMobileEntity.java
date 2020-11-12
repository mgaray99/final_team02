package model.entity;

import model.collision.CollisionDirection;

public interface IMobileEntity extends IEntity {

  default void checkGravity(IEntity entity, CollisionDirection collision){
    if (collision == CollisionDirection.BOTTOM) {
      System.out.print("Bottom");
      this.setGrounded(true);
      this.setGracePeriodBeforeFalling(true);
      if (this.getYVel() > 0) {
        this.setYVel(0);
      }
      this.getHitBox().setYTop(entity.getHitBox().getYTop() - this.getHitBox().getYSize());
    }
    if (collision == CollisionDirection.TOP) {
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
    }
  }

  default void updateGravity(double levelGravityFactor) {
    if(!this.getGrounded()){
      if (this.getGracePeriodBeforeFalling()) {
        this.setGracePeriodBeforeFalling(false);
      } else {
        this.setYVel(this.getYVel() + levelGravityFactor);
      }
    }
  }

  default void jump(double jumpSpeed){
    this.setYVel(jumpSpeed);
    this.setGrounded(false);
  }

  boolean getGrounded();

  void setGrounded(boolean grounded);

  boolean getGracePeriodBeforeFalling();

  void setGracePeriodBeforeFalling(boolean isActive);

  void setCurrentCollision(CollisionDirection collision);
}
