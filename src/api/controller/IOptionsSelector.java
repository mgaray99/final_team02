package api.controller;

import javafx.css.Styleable;
import javafx.event.EventTarget;

public interface IOptionsSelector extends EventTarget, Styleable, IButtonPushHandler {

    void handlePush(String type);

    void regenerateOptions();

    void removeOption(String option);

    void addOption(String option);

    String getTextInBuffer();

    void updateBundle(String path);
}
