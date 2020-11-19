package api.view.scenes;

import javafx.event.EventTarget;
import api.view.IGameScene;

public interface IMenuScene extends EventTarget, IGameScene {
    void addImagesToHomeScreen();
}
