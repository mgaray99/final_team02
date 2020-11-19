package api.view.scenes;

import javafx.event.EventTarget;
import api.view.IGameScene;

public interface IHighScoreScene extends EventTarget, IGameScene {
    void buildLeaderBoardViews();

    void buildLeaderBoardView(String path, double x, double y);

    void updateLeaderboards();
}
