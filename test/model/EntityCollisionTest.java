package model;

import static org.junit.jupiter.api.Assertions.*;

import model.entity.*;
import util.DukeApplicationTest;
import org.junit.jupiter.api.Test;


/**
 * Tests the GameScene class
 */
public class EntityCollisionTest extends DukeApplicationTest {

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
