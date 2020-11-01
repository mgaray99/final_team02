package model;

import static org.junit.jupiter.api.Assertions.*;

import model.entity.Entity;
import model.entity.LivingEntity;
import util.DukeApplicationTest;
import org.junit.jupiter.api.Test;


/**
 * Tests the GameScene class
 */
public class EntityTest extends DukeApplicationTest {

  @Test
  public void collisionTrueTest() {
    LivingEntity livingEntity = new LivingEntity(5, 5, 1);
    Entity deadEntity = new Entity(10,10);
    assertTrue(livingEntity.checkCollision(deadEntity));
  }

  @Test
  public void collisionFalseTest() {
    LivingEntity livingEntity = new LivingEntity(25, 25, 1);
    Entity deadEntity = new Entity(10,10);
    assertFalse(livingEntity.checkCollision(deadEntity));
  }

  @Test
  public void collisionBorderlineTest() {
    LivingEntity livingEntity = new LivingEntity(0, 0, 1);
    Entity deadEntity = new Entity(10,10);
    assertFalse(livingEntity.checkCollision(deadEntity));
  }

}
