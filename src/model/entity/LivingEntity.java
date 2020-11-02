package model.entity;

public abstract class LivingEntity extends Entity{

    private int health;

    public LivingEntity(IEntityType entityType, int xUpperLeft, int yUpperLeft, int healthIn) {
        super(entityType, xUpperLeft, yUpperLeft);
        this.health = healthIn;
    }

    public boolean checkCollision(Entity entityIn){
        return this.getHitBox().intersects(entityIn.getHitBox());
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int healthIn){
        this.health = healthIn;
    }
}
