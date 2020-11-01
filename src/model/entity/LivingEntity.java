package ooga.model.entity;

public class LivingEntity extends Entity{

    private int health;

    public LivingEntity(int xUpperLeft, int yUpperLeft, int healthIn) {
        super(xUpperLeft, yUpperLeft);
        this.health = healthIn;
    }

    public boolean checkCollision(Entity entityIn){
        Entity.HitBox2D otherEntityHitbox = entityIn.getHitBox();
        return this.getHitBox().intersects(otherEntityHitbox);
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int healthIn){
        this.health = healthIn;
    }
}
