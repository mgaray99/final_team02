package model.entity;

import java.awt.Rectangle;

public class LivingEntity extends Entity{

    private int health;

    public LivingEntity(int xUpperLeft, int yUpperLeft, int healthIn) {
        super(xUpperLeft, yUpperLeft);
        this.health = healthIn;
    }


    public boolean checkCollision(Entity entityIn){
        for (Rectangle thisSubHitBox : this.getHitBox()) {
            for (Rectangle otherSubHitBox : entityIn.getHitBox()) {
                if (thisSubHitBox.intersects(otherSubHitBox)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int healthIn){
        this.health = healthIn;
    }
}
