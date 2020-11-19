package api.view.scenes;

import javafx.event.EventTarget;
import api.view.IGameScene;

public interface IControlsScene extends EventTarget, IGameScene {
    void addKeyBinders();

    @Override
    void updateResources(String name);
}
