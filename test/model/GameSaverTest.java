package model;

import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import org.junit.Test;
import util.TestFiles;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class GameSaverTest {

    public static final String TEST_SAVE_PATH = "data/saves/alex_level_test_save.csv";

    //@Test
    public void testSave() throws IOException, InvalidFileException {
        LevelLoader levelLoader = new LevelLoader(TestFiles.ALEX_LEVEL);
        Level level = new Level(levelLoader);
        GameSaver gameSaver = new GameSaver(level);
        File previousSaveFile = new File(TEST_SAVE_PATH);
        if(previousSaveFile.isFile()){
            assertTrue(previousSaveFile.delete());
        }
        gameSaver.writeNewLevelCSVFile(TEST_SAVE_PATH);
        File file = new File(TEST_SAVE_PATH);
        assertTrue(file.isFile());
    }

}

