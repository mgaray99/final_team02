package model.entity;

public abstract class LivingEntity extends Entity{

    private int health;
    private boolean onGround;

    public LivingEntity(IEntityType entityType, int xUpperLeft, int yUpperLeft, int healthIn) {
        super(entityType, xUpperLeft, yUpperLeft);
        this.health = healthIn;
        onGround = true;
    }

    @Override
    public boolean shouldCheckCollisions() {
        return true;
    }

    @Override
    public boolean affectedByGravity() {
        return true;
    }

    @Override
    public boolean checkCollision(Entity entityIn) {
        if(this.getHitBox().intersects(entityIn.getHitBox())) {
            onGround = true;
            return true;
        };
        onGround = false;
        return false;
    }

    //@Override
    //public boolean checkCollision(Entity entityIn) {
    //    if (this.getYVel() <= 0 &&
    //        entityIn.getHitBox().get)
    //    return this.getHitBox().intersects(entityIn.getHitBox());
    //}

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int healthIn){
        this.health = healthIn;
    }

    public boolean isGrounded() {return onGround;}

    public void setOnGround(boolean onGround) {this.onGround = onGround;}
}
