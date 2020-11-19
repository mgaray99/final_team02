package api.controller;

import controller.BuilderInstantiationException;
import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.scene.input.KeyEvent;

import java.util.List;

public interface IGameController extends EventTarget, Styleable, IButtonPushHandler {
    void addOptionsSelectorFromFolder(String folder, String extension, String method)
        throws BuilderInstantiationException;

    void buildOptionsSelector(List<String> choices, String method)
        throws BuilderInstantiationException;

    void addButtonsFromFile(String file) throws BuilderInstantiationException;

    @Override
    void handlePush(String methodName);

    void handleKeyPress(KeyEvent event);

    void handleKeyRelease(KeyEvent event);

    void updateResources(String name);

    List<String> getBuffer();
}
