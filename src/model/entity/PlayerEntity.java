package model.entity;

public class PlayerEntity extends LivingEntity{
    public PlayerEntity(int xUpperLeft, int yUpperLeft, int healthIn) {
        super(EntityType.PLAYER, xUpperLeft, yUpperLeft, healthIn);
    }
}
