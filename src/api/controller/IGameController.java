package api.controller;

import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.scene.input.KeyEvent;

import java.util.List;

public interface IGameController extends EventTarget, Styleable, IButtonPushHandler {
    void addOptionsSelectorFromFolder(String folder, String extension, String method);

    void buildOptionsSelector(List<String> choices, String method);

    void addButtonsFromFile(String file);

    @Override
    void handlePush(String methodName);

    void handleKeyPress(KeyEvent event);

    void handleKeyRelease(KeyEvent event);

    void updateResources(String name);

    List<String> getBuffer();
}
