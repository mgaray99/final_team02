package model.collision;

import java.util.List;
import model.entity.IEntity;

public interface CollisionHandler {
  public void handleCollision(IEntity entity, List<CollisionDirection> collisionInfo);
}
