package model.entity;

public class PlayerEntity extends LivingEntity{

    private int health;
    private boolean onGround;
    public PlayerEntity(int xUpperLeft, int yUpperLeft, int healthIn) {
        super(EntityType.PLAYER, xUpperLeft, yUpperLeft, healthIn);

        this.onGround = true;
    }


    @Override
    public boolean affectedByGravity() {
        return true;
    }

}
