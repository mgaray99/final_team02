package model.entity;

import java.util.ArrayList;
import java.util.List;
import model.Level;
import model.configuration.EntityFactory;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EnemyTest {

    private static final float GRAVITY_FACTOR = 0.015f;
    private static final int LEVEL_STEP_AMOUNT = 10;
    private static final double ENEMY_MOVEMENT_SPEED = 0.1;
    private static final double TOLERANCE = 0.01;
    private static final String TEST_CONFIGURATION = "collision_test.properties";

    @Test
    public void enemiesMoveByThemselvesTest() throws InvalidFileException {
        GameConfiguration gameConfiguration = new GameConfiguration(TEST_CONFIGURATION);
        LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile(), new EntityFactory());
        Level level = new Level(levelLoader);
        List<Double> entityXPositions = new ArrayList<>();
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof Enemy){
                entityXPositions.add(entity.getHitBox().getXLeft());
            }
        }
        for(int i = 0; i < LEVEL_STEP_AMOUNT; i++){
            level.step();
        }
        List<Double> updatedEntityXPositions = new ArrayList<>();
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof Enemy){
                updatedEntityXPositions.add(entity.getHitBox().getXLeft());
            }
        }
        for(int i = 0; i < entityXPositions.size(); i++){
            double enemyPositionDifference = Math.abs(updatedEntityXPositions.get(i) - entityXPositions.get(i));
            assertEquals(LEVEL_STEP_AMOUNT * ENEMY_MOVEMENT_SPEED, enemyPositionDifference, TOLERANCE);
        }
    }



    @Test
    public void playerKillsEnemyTest() throws InvalidFileException {
        GameConfiguration gameConfiguration = new GameConfiguration("collision_test.properties");
        LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile(), new EntityFactory());
        Level level = new Level(levelLoader);
        Enemy enemy = null;
        Player player = null;
        for(IEntity entity : level.getCopyOfEntityList()){
            if(entity instanceof Enemy && enemy == null){
                enemy = (Enemy)entity;
            }
            else if(entity instanceof Player && player == null){
                player = (Player)entity;
            }
        }
        if(player != null && enemy != null){
            assertTrue(level.getCopyOfEntityList().contains(enemy));
            double xPosition = enemy.getHitBox().getXLeft();
            double yPosition = enemy.getHitBox().getYTop();
            player.getHitBox().setXLeft(xPosition);
            float fallDistance = LEVEL_STEP_AMOUNT * 10 * GRAVITY_FACTOR;
            player.getHitBox().setYTop(yPosition - fallDistance);
            player.setGrounded(false);
            for(int i = 0; i < LEVEL_STEP_AMOUNT * 10; i++){
                level.step();
            }
            assertFalse(level.getCopyOfEntityList().contains(enemy));
        }
    }
}
