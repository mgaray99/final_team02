package view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.viewName;


/**
 * Tests the GameSceneMap class
 */
public class GameSceneMapTest extends DukeApplicationTest {

  private GameSceneMap testMap;
  private static final int currentNumViews = 6;

  /**
   * Runs before each test
   * @param stage the stage to contain the components for the test
   */
  @Override
  public void start(Stage stage) {
    testMap = new GameSceneMap();
    testMap.buildMapOfScenes();
  }

  /**
   * Tests that GameSceneMap builds a map correctly (i.e. that it builds a the correct number of
   * scenes to represent each enum currently in GameView.viewName
   */
  @Test
  public void testMapBuildsCorrectly(){
    assertEquals(currentNumViews, testMap.getMapOfScenes().size());
    for (viewName view : viewName.values()) {
      assertTrue(testMap.getMapOfScenes().containsKey(view));
    }
  }

  /**
   * Tests that each game scene has a correctly built controller at the time of its insertion into
   * the map - runs a dummy method on each GameScene value in the map, testing that trying to
   * use its controller doesn't cause a NullPointerException
   */
  @Test
  public void testEachGameSceneHasController() {
    for (viewName view : viewName.values()) {
      assertDoesNotThrow(() -> testMap.getMapOfScenes().get(view).addButtonsToControllerFromFile
          ("resources/buttons/optionsselectorbuttons.txt"));
    }
  }
}