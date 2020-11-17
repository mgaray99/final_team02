package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.configuration.EntityFactory;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import model.entity.IEntity;
import model.scroll.Scroller;
import model.scroll.ScrollerFactory;

public class GameModel {

    private GameConfiguration gameConfiguration;
    private File levelFile;
    private Level level;
    private ScrollerFactory scrollerFactory;
    private EntityFactory entityFactory;

    public GameModel() {}

    public GameModel(GameConfiguration gameConfiguration) throws InvalidFileException {
        this.gameConfiguration = gameConfiguration;
        levelFile = gameConfiguration.getLevelFile();

        entityFactory = new EntityFactory();
        String playerType = gameConfiguration.getPlayerType();
        entityFactory.updatePlayerMapping(playerType);

        LevelLoader levelLoader = new LevelLoader(levelFile, entityFactory);
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
     * Returns the String path to the .csv file containing data on high scores
     * @return the String highScoresPath
     */
    public String getHighScoresPath() {
        return gameConfiguration.getHighScoresPath();
    }

    /**
     * Returns the level of this model
     * @return level
     */
    public Level getLevel() { return this.level; }

    /**
     * Returns the score associated with the level in use by the model
     * @return level.getScore()
     */
    public int getScore() { return this.level.getScore(); }

    /**
     * Resets the level to the characteristics in levelLoader
     */
    public void resetLevel() throws InvalidFileException {
        LevelLoader levelLoader = new LevelLoader(levelFile, entityFactory);
        level.setOrResetLevel(levelLoader);
        setLevelScroller();
    }

}
