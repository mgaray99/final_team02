package model.entity;

import model.collision.CollisionDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PowerUpBlock extends Block implements ISpawner{
    private final List<IEntity> spawnList = new ArrayList<>();
    private int maxSpawnCount = 1;

    public PowerUpBlock(double x, double y) {
        super(x, y);
    }

    @Override
    public List<IEntity> getSpawnList() {
        return this.spawnList;
    }

    @Override
    public List<CollisionDirection> getCollisionsRequiredForSpawn() {
        return Arrays.asList(CollisionDirection.BOTTOM);
    }

    @Override
    public IEntity createSpawn(double x, double y) {
        return new PowerUp(x, y);
    }

    @Override
    public int getMaxSpawnCount() {
        return this.maxSpawnCount;
    }

    @Override
    public void setMaxSpawnCount(int maxSpawnCount) {
        this.maxSpawnCount = maxSpawnCount;
    }
}
