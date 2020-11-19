package api.controller;

import javafx.util.Pair;
import model.configuration.InvalidFileException;

import java.util.List;

public interface IKeyInputter {
    abstract void loadKeyInputsFromFile(String path) throws InvalidFileException;

    abstract void swapKeyInput(String currentKey, String replacementKey);

    abstract void keyPressed(String press);

    abstract void keyReleased(String press);

    void invokeMethod(String methodPath);

    abstract List<Pair<String, String>> getKeyMethodPairings();

    abstract boolean isValidKey(String key);

    abstract String getLastPush();
}
