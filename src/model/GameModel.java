package model;

import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import model.level.Level;

import java.io.File;

public class GameModel {

    private GameConfiguration gameConfiguration;
    private Level level;

    public GameModel(GameConfiguration gameConfiguration) throws InvalidFileException {
        this.gameConfiguration = gameConfiguration;
        File levelFile = gameConfiguration.getLevelFile();
        LevelLoader levelLoader = new LevelLoader(levelFile);
        this.level = new Level(levelLoader);
    }

    public void updateGame(){
        this.level.step();
    }
}
