package api.controller;

import controller.KeyInputterMissingMethodException;
import javafx.util.Pair;
import model.configuration.InvalidFileException;

import java.util.List;

public interface IKeyInputter {
    abstract void loadKeyInputsFromFile(String path) throws InvalidFileException;

    abstract void swapKeyInput(String currentKey, String replacementKey);

    abstract void keyPressed(String press) throws KeyInputterMissingMethodException;

    abstract void keyReleased(String press) throws KeyInputterMissingMethodException;

    void invokeMethod(String methodPath) throws KeyInputterMissingMethodException;

    abstract List<Pair<String, String>> getKeyMethodPairings();

    abstract boolean isValidKey(String key);

    abstract String getLastPush();
}
