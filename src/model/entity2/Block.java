package model.entity2;

import model.HitBox;

public class Block implements IEntity {
    private HitBox hitBox;

    public Block(double x, double y){
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
}
