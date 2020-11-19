package api.view;

import javafx.css.Styleable;
import javafx.event.EventTarget;

import java.io.FileNotFoundException;

public interface ILeaderboardView extends EventTarget, Styleable {
    void initialize();

    void buildTitle();

    void buildScoreText(double index);

    void updateLeaderboard() throws FileNotFoundException;
}
