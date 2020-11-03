package model;

import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;

import java.io.File;

public class GameModel {

    private GameConfiguration gameConfiguration;
    private Level level;
    private KeyPressFunctions keyPressFunctions = new KeyPressFunctions();

    public GameModel() {}

    public GameModel(GameConfiguration gameConfiguration) throws InvalidFileException {
        this.gameConfiguration = gameConfiguration;
        File levelFile = gameConfiguration.getLevelFile();
        LevelLoader levelLoader = new LevelLoader(levelFile);
        this.level = new Level(levelLoader);
    }

    public KeyPressFunctions getKeyPressFunctions() {
        return this.level.getKeyPressFunctions();
    }

    public void updateGame(){
        this.level.step();
    }
}
