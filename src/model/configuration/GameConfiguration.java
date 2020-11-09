package model.configuration;

import java.io.File;
import java.util.Properties;

public class GameConfiguration {
    public static final String LEVEL_KEY = "level";
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

    public File getLevelFile() {
        return this.levelFile;
    }
}
