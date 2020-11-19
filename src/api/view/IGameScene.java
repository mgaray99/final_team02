package api.view;

import controller.GameController;
import javafx.event.EventTarget;
import javafx.scene.Node;

import java.util.List;

public interface IGameScene extends EventTarget {
    void makeBackground();

    void makeErrorText();

    void updateErrorText(String newText);

    void hideErrorText();

    String getErrorText();

    void setGameController(GameController cont);

    void addButtonsToControllerFromFile(String file);

    void buildOptionsSelectorFromFolderForController(String folder, String extension,
                                                     String method);

    void buildOptionsSelectorFromListForController(List<String> choices, String method);

    void addElementToRoot(Node toBeAdded);

    GameController getGameController();

    void removeElementFromRoot(Node toBeRemoved);

    Node lookupElementInRoot(String id);

    void updateResources(String name);

    void updateStylesheet(String name);

    String getSceneId();

    String getValueFromBundle(String key);
}
