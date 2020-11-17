package model.entity;

import model.Level;
import model.configuration.EntityFactory;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoalTest {


    private static final String TEST_CONFIGURATION = "collision_test.properties";

    @Test
    public void playerWinsLevel() throws InvalidFileException {
        GameConfiguration gameConfiguration = new GameConfiguration(TEST_CONFIGURATION);
        LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile(), new EntityFactory());
        Level level = new Level(levelLoader);
        Goal goal = null;
        Player player = null;
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof Goal && goal == null){
                goal = (Goal)entity;
            }
            else if(entity instanceof Player && player == null){
                player = (Player)entity;
            }
        }

        if(player != null && goal != null){
            assertTrue(level.getCopyOfEntityList().contains(goal));
            double xPosition = goal.getHitBox().getXLeft();
            double yPosition = goal.getHitBox().getYTop();
            player.getHitBox().setXLeft(xPosition);
            player.getHitBox().setYTop(yPosition);
            level.step();
            assertTrue(level.isLevelWon());
        }
    }

    @Test
    public void playerLosesLevel() throws InvalidFileException {
        GameConfiguration gameConfiguration = new GameConfiguration(TEST_CONFIGURATION);
        LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile(), new EntityFactory());
        Level level = new Level(levelLoader);
        Player player = null;
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof Player && player == null){
                player = (Player)entity;
            }
        }

        if(player != null){
            assertTrue(level.getCopyOfEntityList().contains(player));
            player.getHitBox().setXLeft(level.getLevelWidth());
            player.getHitBox().setYTop(level.getLevelLength());
            level.step();
            assertTrue(level.isLevelLost());
        }
    }
}
