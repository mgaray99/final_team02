package api.model.configuration;

import java.io.File;

public interface IGameConfiguration {
    void setLevelFile();

    String getTexturesPath();

    String getKeyInputsPath();

    String getAutoGeneratorPath();

    String getPlayerType();

    String getHighScoresPath();

    String getNextConfigFilePath();

    String[] getScrollerArgs();

    File getLevelFile();
}
