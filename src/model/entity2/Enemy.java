package model.entity2;

import model.CollisionDirections;
import model.HitBox;

public class Enemy implements IEntity, IGravity{
    private HitBox hitBox;

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
}
