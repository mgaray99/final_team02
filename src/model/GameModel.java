package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import model.entity.IEntity;
import model.scroll.Scroller;
import model.scroll.ScrollerFactory;

public class GameModel {

    private GameConfiguration gameConfiguration;
    private Level level;
    private ScrollerFactory scrollerFactory;

    public GameModel() {}

    public GameModel(GameConfiguration gameConfiguration) throws InvalidFileException {
        this.gameConfiguration = gameConfiguration;
        File levelFile = gameConfiguration.getLevelFile();
        LevelLoader levelLoader = new LevelLoader(levelFile);
        this.level = new Level(levelLoader);
        setLevelScroller();
    }

    public KeyPressFunctions getKeyPressFunctions() {
        return this.level.getKeyPressFunctions();
    }

    public void updateGame(){
        this.level.step();
    }

    /**
     * Sets the scroller on level to a specific Scroller based on the contents of the level
     */
    private void setLevelScroller() {
        scrollerFactory = new ScrollerFactory();
        String[] scrollerArgs = gameConfiguration.getScrollerArgs();
        String autoGenerationPath = gameConfiguration.getAutoGeneratorPath();
        Scroller builtScroller = scrollerFactory.buildScroller(scrollerArgs, autoGenerationPath);
        level.setScroller(builtScroller);
    }

    /**
     * Returns a defensively copied list of all of the entities present in level
     * @return defensively copied list of all entities in Level
     */
    public List<IEntity> getAllEntitiesInLevel() {
        List<IEntity> defensivelyCopiedEntityList = new ArrayList<>();
        defensivelyCopiedEntityList.addAll(level.getCopyOfEntityList());
        return defensivelyCopiedEntityList;
    }

    /**
     * Returns the String path to the .properties file containing data on key inputs
     * @return the String keyInputsPath
     */
    public String getKeyInputsPath() {
        return gameConfiguration.getKeyInputsPath();
    }

    /**
     * Returns the String path to the .properties file containing data on textures
     * @return the String texturesPath
     */
    public String getTexturesPath() {
        return gameConfiguration.getTexturesPath();
    }

    /**
     * Returns the level of this model
     * @return level
     */
    public Level getLevel() { return this.level; }

}
