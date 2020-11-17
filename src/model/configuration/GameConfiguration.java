package model.configuration;

import java.io.File;
import java.util.Properties;

public class GameConfiguration {
    public static final String LEVEL_KEY = "level";
    private static final String TEXTURES_KEY = "textures";
    private static final String KEYINPUTS_KEY = "keys";
    private static final String SCROLLER_KEY = "scroller";
    private static final String AUTO_KEY = "autofile";
    private static final String PLAYER_KEY = "player";
    private static final String HIGH_SCORES_KEY = "leaderboard";
    private static final String DEFAULT_LEVEL_FILEPATH = "./data/";
    private static final String ROOT_SOURCE_INDICATOR = "./";
    private final Properties properties;
    private File levelFile;

    public GameConfiguration(String resource){
        this.properties = FileHelper.createPropertiesAndTryLoadFromResource(this.getClass(), resource);
        this.setLevelFile();
    }

    public GameConfiguration(String resource, Class clazz){
        this.properties = FileHelper.createPropertiesAndTryLoadFromResource(clazz, resource);
        this.setLevelFile();
    }

    private void setLevelFile() {
        String levelFileName = this.properties.getProperty(LEVEL_KEY);
        //make exception here for when levelFileName is null
        if (levelFileName.contains(ROOT_SOURCE_INDICATOR)) {
            this.levelFile = new File(levelFileName);
        } else {
            this.levelFile = new File(DEFAULT_LEVEL_FILEPATH + levelFileName);
        }
    }

    /**
     * Determines the String filepath that holds the textures (i.e. ./.../mariotextures.properties)
     * @return the filepath to the textures file
     */
    public String getTexturesPath() { return properties.getProperty(TEXTURES_KEY); }

    /**
     * Determines the String filepath that holds the textures (i.e. ./.../mariokeyinputs.properties)
     * @return the filepath to the key inputs file
     */
    public String getKeyInputsPath() { return properties.getProperty(KEYINPUTS_KEY); }

    /**
     * Returns the String path to the .xml file specifying how to automatically generate new parts
     * of this level (i.e. ./.../automario.xml)
     * (will be NA if no such generation is needed)
     *
     * @return the filepath to the .txt file specifying automatic generation
     */
    public String getAutoGeneratorPath() { return properties.getProperty(AUTO_KEY); }

    /**
     * Returns the class name for the player class that will run
     * @return the String corresponding to the type of player
     */
    public String getPlayerType() { return properties.getProperty(PLAYER_KEY); }

    /**
     * Returns the String filepath that references high scores
     * @return the String corresponding to the high score path
     */
    public String getHighScoresPath() { return properties.getProperty(HIGH_SCORES_KEY); }

    /**
     * Determines the String array containing data necessary to build a Scroller
     * (i.e. ["Auto", "0.1", "-0.5"])
     * @return the list of arguments related to a scroller as a String array
     */
    public String[] getScrollerArgs() {
        String scrollerLine = properties.getProperty(SCROLLER_KEY);
        //if (scrollerLine.indexOf(",") > 0) {
            return scrollerLine.split(",");
        //}
        /*else {
            String[] line = new String[1];
            line[0] = scrollerLine;
            return line;
        }*/
    }

    public File getLevelFile() {
        return this.levelFile;
    }
}
