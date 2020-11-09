package model.entity2;

import model.HitBox;

public class PowerUp implements IEntity {
    private HitBox hitBox;
    private final String type = "powerUp";


    public PowerUp(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public boolean checkCollision(IEntity entity) {
        return false;
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
}
