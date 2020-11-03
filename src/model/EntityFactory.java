package model;

import model.entity.*;

public class EntityFactory implements IEntityFactory{

    @Override
    public Entity createEntity(IEntityType entityType, int i, int j) {
        switch (entityType.getTypeID()) {
            case "BARRIER_BLOCK" -> {
                return new BarrierBlockEntity(i, j);
            }
            case "BREAKABLE_BLOCK" -> {
                return new BreakableBlockEntity(i, j);
            }
            case "DAMAGING_BLOCK" -> {
                return new DamagingBlockEntity(i, j);
            }
            case "ENEMY" -> {
                return new EnemyEntity(i, j, 100);
            }
            case "GOAL" -> {
                return new GoalEntity(i, j);
            }
            case "PLAYER" -> {
                return new PlayerEntity(i, j, 100);
            }
            case "POWER_UP_BLOCK" -> {
                return new PowerUpBlockEntity(i, j);
            }
            case "POWER_UP" -> {
                return new PowerUpEntity(i, j);
            }
            default -> {
                return EmptyEntity.INSTANCE;
            }
        }
    }
}
