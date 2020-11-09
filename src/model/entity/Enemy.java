package model.entity;

import model.HitBox;

public class Enemy implements IEntity, IGravity{
    private HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private double xVel = 0;
    private double yVel = 0;

    public Enemy(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public boolean checkCollision(IEntity entity) {
        return false;
    }


    @Override
    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    @Override
    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    @Override
    public double getXVel() {
        return xVel;
    }

    @Override
    public double getYVel() {
        return yVel;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isGrounded() {
        return false;
    }
}
