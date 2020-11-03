package model.configuration;

import java.io.File;
import java.util.Properties;

public class GameConfiguration {
    public static final String LEVEL_KEY = "level";
    private final Properties properties;
    private File levelFile;

    public GameConfiguration(String resource){
        this.properties = FileHelper.createPropertiesAndTryLoadFromResource(this.getClass(), resource);
        this.setLevelFile();
    }

    private void setLevelFile() {
        String levelFileName = this.properties.getProperty(LEVEL_KEY);

        this.levelFile = new File(levelFileName);
    }

    public File getLevelFile() {
        return this.levelFile;
    }
}
