package model.entity;

import model.HitBox;
import model.collision.CollisionDirection;

public class Player implements IEntity, IGravity {

    private final String type = this.getClass().getSimpleName();

    private double xVel = 0;
    private double yVel = 0;
    private HitBox hitBox;
    private boolean grounded = true;
    private boolean gracePeriodBeforeFalling = true;

    public Player(double x, double y){
        this.hitBox = new HitBox(x, y);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void checkCollision(IEntity entity) {
        CollisionDirection collision = hitBox.getCollisionDirection(entity.getHitBox());
        //this if statement is for testing - will be removed
        //if (!collision.contains(CollisionDirection.NONE)) {
        //    yVel = 0;
        //}
        if (collision == CollisionDirection.BOTTOM) {
            this.setGrounded(true);
            this.setGracePeriodBeforeFalling(true);
            if (yVel > 0) {
                yVel = 0;
            }
            this.getHitBox().setYTop(entity.getHitBox().getYTop() - this.getHitBox().getYSize());
        }
        if (collision == CollisionDirection.TOP) {
            if (yVel < 0) {
                yVel = 0;
            }
            this.getHitBox().setYTop(entity.getHitBox().getYBottom());
        }
        if (collision == CollisionDirection.LEFT) {
            if (xVel < 0) {
                xVel = 0;
            }
            this.getHitBox().setXLeft(entity.getHitBox().getXRight());
        }
        if (collision == CollisionDirection.RIGHT) {
            if (xVel > 0) {
                xVel = 0;
            }
            this.getHitBox().setXLeft(entity.getHitBox().getXLeft() - this.getHitBox().getXSize());
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
        if (grounded) {
            this.gracePeriodBeforeFalling = true;
        }
    }

    public boolean getGracePeriodBeforeFalling() {
        return gracePeriodBeforeFalling;
    }

    public void setGracePeriodBeforeFalling(boolean isActive) {
        this.gracePeriodBeforeFalling = isActive;
    }

    @Override
    public void moveOneStep(){
        this.getHitBox().translateX(this.getXVel());
        this.getHitBox().translateY(this.getYVel());
        this.setGrounded(false);
    }
}
