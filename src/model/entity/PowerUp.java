package model.entity;

import model.HitBox;

public class PowerUp implements IEntity, IEmpowering {
    private HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private Modifier modifier;
    private boolean hasAppliedModifier = false;

    public PowerUp(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

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
    public Modifier getModifier() {
        return this.modifier;
    }

    @Override
    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public boolean hasAppliedModifier() {
        return this.hasAppliedModifier;
    }

    @Override
    public void setHasAppliedModifier(boolean hasAppliedModifier) {
        this.hasAppliedModifier = hasAppliedModifier;
    }
}
