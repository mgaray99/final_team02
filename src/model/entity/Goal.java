package model.entity;

import model.HitBox;

public class Goal implements IEntity, IWinnable{
    private boolean hasWon;
    private HitBox hitBox;
    private final String type = this.getClass().getSimpleName();

    public Goal(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void checkFutureCollision(IEntity entity) {

    }

    @Override
    public void setXVel(double xVel) {

    }

    @Override
    public void setYVel(double yVel) {

    }

    @Override
    public double getXVel() {
        return 0;
    }

    @Override
    public double getYVel() {
        return 0;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean getHasWon() {
        return this.hasWon;
    }

    @Override
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }
}
