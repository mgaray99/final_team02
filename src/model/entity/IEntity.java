package model.entity;

import java.awt.geom.Rectangle2D;

public interface IEntity {

  boolean shouldCheckCollisions();

  boolean affectedByGravity();

  boolean isGrounded();

  boolean isEmpty();

  boolean hasMatchingId();

  String getId();

  String getTypeId();

  Rectangle2D.Float getHitBox();

  boolean checkCollision();

  void setXVel();

  float getYVel();

  void setYVel();

  void moveOneStep();
}
