package model.entity;

import java.util.List;
import model.CollisionDirection;
import model.HitBox;

public class Player implements IEntity, IGravity {

    private final String type = this.getClass().getSimpleName();

    private double xVel = 0;
    private double yVel = 0;
    private HitBox hitBox;
    private boolean grounded = true;

    public Player(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void checkCollision(IEntity entity) {
        List<CollisionDirection> collision = hitBox.getCollisionDirections(entity.getHitBox());
        if (collision.contains(CollisionDirection.BOTTOM)) {
            this.setGrounded(true);
            if (yVel > 0) {
                yVel = 0;
            }
        }
        if (collision.contains(CollisionDirection.TOP)) {
            if (yVel < 0) {
                yVel = 0;
            }

        }
        if (collision.contains(CollisionDirection.LEFT)) {
            if (xVel < 0) {
                xVel = 0;
            }
        }
        if (collision.contains(CollisionDirection.RIGHT)) {
            if (xVel > 0) {
                xVel = 0;
            }
        }
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
    public boolean getGrounded() {
        return grounded;
    }

    @Override
    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
