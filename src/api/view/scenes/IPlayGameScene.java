package api.view.scenes;

import javafx.event.EventTarget;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Level;
import api.view.IGameScene;

public interface IPlayGameScene extends EventTarget, IGameScene {
    void makeScoreText();

    void updateScoreText(String update);

    void addTexturesGroup();

    void buildSavingFunctionality();

    void buildTextField(TextField field);

    void buildScoreField();

    void launchSave(Level level);

    void handleTextFieldPress(KeyEvent event);

    void attemptSave();

    boolean checkIsValidText(String text);

    void finalizeSave();

    void attemptScoreSave(KeyEvent key);

    void finalizeScoreSave();

    void inputScore(String path, Level level);

    void clearFields();

    void pauseLevel();
}
