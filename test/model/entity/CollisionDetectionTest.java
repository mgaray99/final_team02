package model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Tests the GameScene class
 */
public class CollisionDetectionTest extends DukeApplicationTest {



  @Test
  public void collisionTrueXTest() {
    Player player = new Player(5, 5);
    Block block = new Block(5.9,5);
    player.setXVel(5);
    player.checkCollision(block);
    assertEquals(0, player.getXVel());
  }

  @Test
  public void collisionTrueYTest() {
    Player player = new Player(5, 5);
    player.setGrounded(false);
    Block block = new Block(5,5.9);
    player.setXVel(5);
    player.checkCollision(block);
    assertEquals(0, player.getYVel());
    assertTrue(player.getGrounded());
  }

  @Test
  public void collisionFalseTest() {
    Player player = new Player(25, 25);
    player.setXVel(5);
    player.setYVel(5);
    Block block = new Block(10,10);
    player.checkCollision(block);
    assertEquals(5, player.getXVel());
    assertEquals(5, player.getYVel());
  }

  @Test
  public void collisionBorderlineHitTest() {
    Player player = new Player(0, 0);
    Block block = new Block(4.9,4.9);
    player.setXVel(5);
    player.setYVel(5);
    player.checkCollision(block);
    assertEquals(0, player.getXVel());
    assertEquals(0, player.getYVel());
  }

  @Test
  public void collisionBorderlineMissTest() {
    Player player = new Player(0, 0);
    Block block = new Block(5,5);
    player.setXVel(5);
    player.setYVel(5);
    player.checkCollision(block);
    assertEquals(5, player.getXVel());
    assertEquals(5, player.getYVel());
  }
}
