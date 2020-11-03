package model.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the GameScene class
 */
public class EntityTest extends DukeApplicationTest {

  @Test
  public void collisionTrueTest() {
    PlayerEntity livingEntity = new PlayerEntity(5, 5, 1);
    BarrierBlockEntity deadEntity = new BarrierBlockEntity(10,10);
    assertTrue(livingEntity.checkCollision(deadEntity));
  }

  @Test
  public void collisionFalseTest() {
    PlayerEntity livingEntity = new PlayerEntity(25, 25, 1);
    BarrierBlockEntity deadEntity = new BarrierBlockEntity(10,10);
    assertFalse(livingEntity.checkCollision(deadEntity));
  }

  @Test
  public void collisionBorderlineTest() {
    PlayerEntity livingEntity = new PlayerEntity(0, 0, 1);
    BarrierBlockEntity deadEntity = new BarrierBlockEntity(10,10);
    assertFalse(livingEntity.checkCollision(deadEntity));
  }

}
