package model.entity;

import model.HitBox;
import model.collision.CollisionDirections;
import model.collision.Direction;

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

  default void processCurrentCollision(IEntity entity, CollisionDirections collision){
    if (collision.contains(Direction.BOTTOM) && !collision.containsHorizontalCollision()) {
      //System.out.print("Bottom");
      this.setGrounded(true);

      this.getHitBox().setYBottom(entity.getHitBox().getYTop());
      if (this.getYVel() > 0) {
        this.setYVel(0);
      }
      //this.getHitBox().setYTop(entity.getHitBox().getYTop() - this.getHitBox().getYSize());
    }
    if (collision.contains(Direction.TOP) && !collision.containsHorizontalCollision()){
      //System.out.print("Top");
      this.getHitBox().setYTop(entity.getHitBox().getYBottom());
      if (this.getYVel() < 0) {
        this.setYVel(0);
      }
      //this.getHitBox().setYTop(entity.getHitBox().getYBottom());
    }
    if (collision.contains(Direction.RIGHT) && !collision.containsVerticalCollision()) {
      //System.out.print("Right");
      this.getHitBox().setXRight(entity.getHitBox().getXLeft());
      if (this.getXVel() > 0) {
        this.setXVel(0);
      }
      //this.getHitBox().setXLeft(entity.getHitBox().getXLeft() - this.getHitBox().getXSize());
    }
    if (collision.contains(Direction.LEFT) && !collision.containsVerticalCollision()) {
      //System.out.print("Left");
      this.getHitBox().setXLeft(entity.getHitBox().getXRight());
      if (this.getXVel() < 0) {
        this.setXVel(0);
      }
      //this.getHitBox().setXLeft(entity.getHitBox().getXRight());
    }
  }
}
