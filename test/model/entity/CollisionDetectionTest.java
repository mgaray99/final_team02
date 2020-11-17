package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.collision.Direction;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the GameScene class
 */
public class CollisionDetectionTest extends DukeApplicationTest {



  @Test
  public void collisionTrueXTest() {
    Player player = new MarioPlayer(5, 5) {

    };
    Block block = new Block(5.8,5);
    player.checkCollision(block);
    assertTrue(player.getCurrentCollision().contains(Direction.RIGHT));
  }

  @Test
  public void collisionTrueYTest() {
    Player player = new MarioPlayer(5, 5);
    player.setGrounded(false);
    Block block = new Block(5,5.9);
    player.checkCollision(block);
    assertTrue(player.getCurrentCollision().contains(Direction.BOTTOM));
  }

  @Test
  public void collisionFalseTest() {
    Player player = new MarioPlayer(25, 25);
    Block block = new Block(10,10);
    player.checkCollision(block);
    assertFalse(player.getCurrentCollision().doesCollide());
  }

  @Test
  public void collisionBorderlineHitTest() {
    Player player = new MarioPlayer(0, 0);
    Block block = new Block(4.9,4.9);
    player.checkCollision(block);
    assertTrue(player.getCurrentCollision().contains(Direction.BOTTOM));
    assertTrue(player.getCurrentCollision().contains(Direction.LEFT));
  }

  @Test
  public void collisionBorderlineMissTest() {
    Player player = new MarioPlayer(0, 0);
    Block block = new Block(5,5);
    player.setXVel(5);
    player.setYVel(5);
    player.checkCollision(block);
    assertEquals(5, player.getXVel());
    assertEquals(5, player.getYVel());
  }
}
