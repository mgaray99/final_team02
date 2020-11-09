package model.entity;

import model.HitBox;

public class Enemy implements IEntity, IGravity{
    private HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private boolean grounded;


    public Enemy(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void checkCollision(IEntity entity) {
    }

    @Override
    public void setXVel(double xVel) {

    }

    @Override
    public void setYVel(double yVel) {

    }

    @Override
    public double getYVel() {
        return 0;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public double getXVel() {
        return 0;
    }

    @Override
    public boolean getGrounded() {
        return grounded;
    }

    @Override
    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
