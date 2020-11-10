package model.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Level;
import org.junit.jupiter.api.Test;

public class LevelLoaderTest {

  @Test
  public void OneBrickLevelLoadTest() throws InvalidFileException {
    GameConfiguration gameConfiguration = new GameConfiguration("oneBlock.properties");
    LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile());
    Level level = new Level(levelLoader);
    assertEquals(level.getCopyOfEntityList().size(), 1);
  }
}
