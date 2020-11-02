package model.entity;

public class EnemyEntity extends LivingEntity{
    public EnemyEntity(int xUpperLeft, int yUpperLeft, int healthIn) {
        super(EntityType.ENEMY, xUpperLeft, yUpperLeft, healthIn);
    }
}
