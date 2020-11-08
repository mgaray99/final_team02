package model;

import java.util.ArrayList;
import java.util.List;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;

import java.io.File;
import model.entity.Entity;

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

    /**
     * Returns the level of this model
     * @return level
     */
    public Level getLevel() { return this.level; }

    /**
     * Returns a defensively copied list of all of the entities present in level
     * @return defensively copied list of all entities in Level
     */
    public List<Entity> getAllEntitiesInLevel() {
        List<Entity> defensivelyCopiedEntityList = new ArrayList<>();
        defensivelyCopiedEntityList.addAll(level.getAllEntitiesInMapAsList());
        return defensivelyCopiedEntityList;
    }
}
