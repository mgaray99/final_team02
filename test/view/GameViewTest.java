package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.viewName;

/**
 * Tests the GameView class for its ability to switch between scenes and perform various functions
 */
class GameViewTest extends DukeApplicationTest {

    private GameView view;
    private Stage stage;
    private Scene currentScene;
    private static final String DEFAULT_STYLESHEET = "resources/cssstylesheets/default.css";
    private static final String DARK_STYLESHEET = "resources/cssstylesheets/dark.css";
    private static final String START_BUTTON_ID = "StartCommand";

    @Override
    public void start(Stage st) {
      view = new GameView();
      stage = st;
      view.start(stage);
      currentScene = stage.getScene();
    }

  /**
   * Checks to make sure that when the user changes the stylesheet, it updates appropriately
   */
  @Test
    public void testChangeStylesheet() {
      assertEquals(DEFAULT_STYLESHEET, currentScene.getStylesheets().get(0));
      view.switchStylesheet("dark");
      assertEquals(DARK_STYLESHEET, currentScene.getStylesheets().get(0));
    }

  /**
   * Checks to make sure that when the user tries to switch the stylesheet in view to a sheet not in
   * the resources/cssstylesheets folder, the stylesheet does not change
   */
  @Test
    public void testChangeStylesheetNotFound() {
      assertEquals(DEFAULT_STYLESHEET,currentScene.getStylesheets().get(0));
      view.switchStylesheet("dart");
      assertEquals(DEFAULT_STYLESHEET, currentScene.getStylesheets().get(0));
    }

  /**
   * Checks to make sure that the text on the start button changes appropriately in the event that
   * there is a language switch in view
   */
  @Test
    public void testChangeLanguage() {
      Button startButton = (Button)((GameScene)currentScene).getGameController()
          .lookup("#" + START_BUTTON_ID);

      assertEquals("Start",  startButton.getText());
      javafxRun(() -> view.switchLanguage("Espanol"));
      assertEquals("Empezar", startButton.getText());
    }

  /**
   * Checks to make sure that when we try to switch the Resource Bundle to a bundle that is not in
   * the resources/resourcebundles folder, it does not work
   */
  @Test
    public void testChangeLanguageNotFound() {
      Button startButton = (Button)((GameScene)currentScene).getGameController()
          .lookup("#" + START_BUTTON_ID);

      assertEquals("Start",  startButton.getText());
      javafxRun(() -> view.switchLanguage("Klingon"));
      assertEquals("Start", startButton.getText());
    }

  /**
   * Tests that the start() method opens the GAME scene
   */
  @Test
    public void testStartGame() {
        assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
        javafxRun(() -> view.start());

        currentScene = stage.getScene();
        assertEquals(((GameScene)currentScene).getSceneId(), viewName.GAME.toString());
    }

  /**
   * Tests that the selectGameTypeScreen() method opens the GAMEVERSION scene
   */
  @Test
  public void testGameVersion() {
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
    javafxRun(() -> view.selectGameTypeScreen());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.GAMEVERSION.toString());
  }

  /**
   * Tests that the switchToControlScreen() method opens the CONTROLS scene
   */
  @Test
  public void testSetControls() {
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
    javafxRun(() -> view.switchToControlScreen());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.CONTROLS.toString());
  }

  /**
   * Tests that the homeScreen() method opens the HOME_SCREEN scene
   */
  @Test
  public void testBackToMenu() {
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
    javafxRun(() -> view.start());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.GAME.toString());
    javafxRun(() -> view.homeScreen());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
  }

  /**
   * Tests that the switchToSelectCssStylesheetScreen() method opens the SELECT_CSS_STYLESHEET scene
   */
  @Test
  public void testAppearanceScreen() {
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
    javafxRun(() -> view.switchToSelectCssStylesheetScreen());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.SELECT_CSS_STYLESHEET.toString());
  }

  /**
   * Tests that the switchToSelectLanguageScreen() method opens the SELECT_RESOURCE_BUNDLE scene
   */
  @Test
  public void testLanguageScreen() {
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
    javafxRun(() -> view.switchToSelectLanguageScreen());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(),
        viewName.SELECT_RESOURCE_BUNDLE.toString());
  }

  /**
   * Tests that the back() button returns the user to the HOME_SCREEN scene
   */
  @Test
  public void testBack() {
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
    javafxRun(() -> view.selectGameTypeScreen());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.GAMEVERSION.toString());
    javafxRun(() -> view.back());

    currentScene = stage.getScene();
    assertEquals(((GameScene)currentScene).getSceneId(), viewName.HOME_SCREEN.toString());
  }


}