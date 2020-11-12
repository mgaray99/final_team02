package model.entity;

import model.HitBox;
import model.collision.CollisionDirection;

public interface IGravitate {

  default void checkGravity(IEntity entity, CollisionDirection collision){
    if (collision == CollisionDirection.BOTTOM) {
      System.out.print("Bottom");
      this.setGrounded(true);
      this.resetGracePeriodBeforeFalling();
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

  default void applyGravity(double levelGravityFactor) {
    if(!this.getGrounded()){
      if (this.getGracePeriodBeforeFalling() > 0) {
        this.subtractFromGracePeriodBeforeFalling();
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

  int getGracePeriodBeforeFalling();

  void resetGracePeriodBeforeFalling();

  void subtractFromGracePeriodBeforeFalling();

  // Expected velocity methods that can be implemented by IEntity
  void setXVel(double xVel);

  double getXVel();

  HitBox getHitBox();

  void setYVel(double yVel);

  double getYVel();

  void setCurrentCollision(CollisionDirection collision);
}
