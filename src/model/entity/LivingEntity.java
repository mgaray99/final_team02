package model.entity;

public class LivingEntity extends Entity{

    private int health;

    public LivingEntity(int xUpperLeft, int yUpperLeft, int healthIn) {
        super(xUpperLeft, yUpperLeft);
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
