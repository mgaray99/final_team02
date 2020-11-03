package model.entity;

public abstract class LivingEntity extends Entity{

    private int health;
    private boolean onGround;

    public LivingEntity(IEntityType entityType, int xUpperLeft, int yUpperLeft, int healthIn) {
        super(entityType, xUpperLeft, yUpperLeft);
        this.health = healthIn;
    }

    @Override
    public boolean shouldCheckCollisions() {
        return true;
    }

    @Override
    public boolean affectedByGravity() {
        return true;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int healthIn){
        this.health = healthIn;
    }

    public boolean getOnGround() {return onGround;}

    public void setOnGround(boolean onGround) {this.onGround = onGround;}
}
