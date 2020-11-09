package model.entity;

import model.Level;
import model.configuration.GameConfiguration;
import model.configuration.InvalidFileException;
import model.configuration.LevelLoader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class EnemyTest {

    public static final int LEVEL_STEP_AMOUNT = 10;
    public static final double ENEMY_MOVEMENT_SPEED = 0.1;
    public static final double TOLERANCE = 0.01;

    @Test
    public void enemiesMoveByThemselvesTest() throws InvalidFileException {
        GameConfiguration gameConfiguration = new GameConfiguration("configuration.properties");
        LevelLoader levelLoader = new LevelLoader(gameConfiguration.getLevelFile());
        Level level = new Level(levelLoader);
        List<Double> entityXPositions = new ArrayList<>();
        for(IEntity entity : level.getAllEntities()){
            if(entity instanceof Enemy){
                entityXPositions.add(entity.getHitBox().getXLeft());
            }
        }
        for(int i = 0; i < LEVEL_STEP_AMOUNT; i++){
            level.step();
        }
        List<Double> updatedEntityXPositions = new ArrayList<>();
        for(IEntity entity : level.getAllEntities()){
            if(entity instanceof Enemy){
                updatedEntityXPositions.add(entity.getHitBox().getXLeft());
            }
        }
        for(int i = 0; i < entityXPositions.size(); i++){
            double enemyPositionDifference = Math.abs(updatedEntityXPositions.get(i) - entityXPositions.get(i));
            assertEquals(LEVEL_STEP_AMOUNT * ENEMY_MOVEMENT_SPEED, enemyPositionDifference, TOLERANCE);
        }
    }
}
