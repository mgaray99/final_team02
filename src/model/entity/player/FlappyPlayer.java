package model.entity.player;

import model.collision.CollisionDirection;
import model.entity.IEntity;

public class FlappyPlayer extends Player2 {

  public FlappyPlayer(double x, double y) {
    super(x, y);
  }

  public void checkCollision(IEntity entity) {
    CollisionDirection collision = hitBox.getCollisionDirection(entity.getHitBox());
    if (collision != CollisionDirection.NONE) {
      this.immobilized = true;
    }
  }
}
