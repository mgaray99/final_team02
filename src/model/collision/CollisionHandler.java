package model.collision;

import model.entity.IEntity;

public interface CollisionHandler {
  public void handleCollision(IEntity entity, CollisionDirections collisionInfo);
}
