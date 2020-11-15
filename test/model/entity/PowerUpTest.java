package model.entity;

import model.Level;
import model.collision.Direction;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PowerUpTest {

    private static final float MAX_INTERSECT = 0.5F;
    private static final String TEST_CONFIGURATION = "powerup_test.properties";

    @Test
    public void playerSpawnsAndConsumesPowerUp() throws InvalidFileException {
        GameConfiguration gameConfiguration = new GameConfiguration(TEST_CONFIGURATION);
        LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile());
        Level level = new Level(levelLoader);
        PowerUpBlock powerUpBlock = null;
        Player player = null;
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof PowerUpBlock && powerUpBlock == null){
                powerUpBlock = (PowerUpBlock) entity;
            }
            else if(entity instanceof Player && player == null){
                player = (Player)entity;
            }
            if(player != null && powerUpBlock != null){
                break;
            }
        }
        if(player != null && powerUpBlock != null){
            assertTrue(player.getModifiers().keySet().isEmpty());
            assertTrue(level.getCopyOfEntityList().contains(powerUpBlock));
            assertEquals(1, powerUpBlock.getMaxSpawnCount());
            double xPosition = powerUpBlock.getHitBox().getXLeft();
            double yPosition = powerUpBlock.getHitBox().getYTop();
            double height = powerUpBlock.getHitBox().getYSize();
            double width = powerUpBlock.getHitBox().getXSize();
            player.getHitBox().setXLeft(xPosition + width * 0.5);
            player.getHitBox().setYTop((yPosition + (height)) - (MAX_INTERSECT * 0.9));
            assertEquals(Direction.TOP, player.getHitBox().getCollisionDirection(powerUpBlock.getHitBox()));
            level.step();
            assertEquals(0, powerUpBlock.getMaxSpawnCount());
            PowerUp powerUp = null;
            for(IEntity entity : level.getCopyOfEntityList()){
                if(entity instanceof PowerUp){
                    powerUp = (PowerUp)entity;
                    break;
                }
            }
            if(powerUp != null){
                assertTrue(level.getCopyOfEntityList().contains(powerUp));

                xPosition = powerUp.getHitBox().getXLeft();
                yPosition = powerUp.getHitBox().getYTop();
                player.getHitBox().setXLeft(xPosition);
                player.getHitBox().setYTop(yPosition);
                assertNotEquals(Direction.NONE, player.getHitBox().getCollisionDirection(powerUpBlock.getHitBox()));
                level.step();
                assertFalse(level.getCopyOfEntityList().contains(powerUp));
                assertFalse(player.getModifiers().keySet().isEmpty());
            }
        }
    }
}
