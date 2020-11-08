package model.entity2;

import model.HitBox;

public class PowerUp implements IEntity {
    private HitBox hitBox;

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
}
