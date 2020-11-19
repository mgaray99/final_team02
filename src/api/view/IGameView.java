package api.view;

import controller.GameController;
import javafx.event.Event;
import javafx.stage.Stage;
import api.model.IGameModel;
import view.GameScene;

import java.util.List;

public interface IGameView {

    void start(Stage sta);

    void buildScenes();

    void buildScenesList();

    void resetScenes();

    void buildModel();

    void update();

    void normalUpdate();

    void levelLost();

    void levelWon();

    void finishedFinalLevel();

    void loadNextLevel(String nextLevel);

    int getScore();

    void prepareAnimation();

    void listenOnControllers();

    void handleControllerEvent(GameController cont, Event event);

    void keyPressed(String key);

    void keyReleased(String key);

    void performReflection(List<String> reflectionArgs);

    void switchStylesheet(String name);

    void switchLanguage(String name);

    void setScene(GameScene scene);

    void switchToHomeScreen();

    void switchToSelectCssStylesheetScreen();

    void switchToSelectLanguageScreen();

    void selectGameTypeScreen();

    void switchToTextureSwapScreen();

    void saveGame();

    void endGame();

    void switchGame(String type);

    void switchTextures(String texturePath);

    void switchToHighScoresScreen();

    void switchToControlScreen();

    void resetLevel();

    void back();

    void start();

    void homeScreen();

    IGameModel getModel();

    void setModel(IGameModel m);

    String getConfigPath();

    String getTexturerPath();
}
