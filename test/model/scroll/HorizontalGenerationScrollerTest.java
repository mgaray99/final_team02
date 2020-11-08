package model.scroll;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import model.entity.BarrierBlockEntity;
import model.entity.EnemyEntity;
import model.entity.Entity;
import model.entity.PlayerEntity;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests the HorizontalGenerationScroller class
 */

public class HorizontalGenerationScrollerTest extends DukeApplicationTest{

  private static final int MAX = 15;
  private static final String PATH = "resources/game_configuration/autoflappy.txt";
  private static final double XSCROLL = -0.5;
  private static final double YSCROLL = 0.5;

  private static final int PLAYERX = 4;
  private static final int PLAYERY = 5;

  private static final int BARRIERX = 12;
  private static final int BARRIERY = 8;

  private static final int ENEMYX = 6;
  private static final int ENEMYY = 7;

  private PlayerEntity playerEntity;
  private BarrierBlockEntity barrierBlockEntity;
  private EnemyEntity enemyEntity;

  private List<Entity> entityList;

  @Override
  public void start(Stage stage) {
    entityList = new ArrayList<>();

    playerEntity = new PlayerEntity(PLAYERX, PLAYERY, 10);
    barrierBlockEntity = new BarrierBlockEntity(BARRIERX, BARRIERY);
    enemyEntity = new EnemyEntity(ENEMYX, ENEMYY, 10);

    entityList.add(playerEntity);
    entityList.add(barrierBlockEntity);
    entityList.add(enemyEntity);
  }

  /**
   * Tests that the HorizontalGenerationScroller correctly scrolls everything when not generating
   * a new portion of the game
   */
  @Test
  public void testSimpleScroll() {
    HorizontalGenerationScroller scroller = new HorizontalGenerationScroller(XSCROLL,YSCROLL,
        MAX, PATH);
    scroller.scroll(entityList, playerEntity);

    assertEquals(PLAYERX + XSCROLL, playerEntity.getHitBox().x);
    assertEquals(BARRIERX + XSCROLL, barrierBlockEntity.getHitBox().x);
    assertEquals(ENEMYX + XSCROLL, enemyEntity.getHitBox().x);

    assertEquals(PLAYERY + YSCROLL, playerEntity.getHitBox().y);
    assertEquals(BARRIERY + YSCROLL, barrierBlockEntity.getHitBox().y);
    assertEquals(ENEMYY + YSCROLL, enemyEntity.getHitBox().y);
  }
}