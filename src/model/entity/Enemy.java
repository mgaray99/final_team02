package model.entity;

import model.HitBox;
import model.collision.CollisionDirections;
import model.collision.Direction;

import java.util.Arrays;
import java.util.List;

public class Enemy implements IEntity, IMovable, IDamageable{

    private static final int GRACE_PERIOD = 2;
    private static final double ENEMY_MOVEMENT_SPEED = 0.1;
    private final HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private double xVel = 0;
    private double yVel = 0;
    private boolean grounded = true;
    private int gracePeriodBeforeFalling = GRACE_PERIOD;
    private double health = 0;
    private double damage = 0;


    public Enemy(double x, double y){
        this.hitBox = new HitBox(x, y);
        this.setHealth(100);
        this.setCollisionDamage(100);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    public void processCurrentCollision(IEntity entity, CollisionDirections collision){
        if (collision.contains(Direction.BOTTOM)) {
            System.out.print("Bottom");
            this.setGrounded(true);
            if (this.getYVel() > 0) {
                this.setYVel(0);
            }
            this.getHitBox().setYTop(entity.getHitBox().getYTop() - this.getHitBox().getYSize());
        }
        if (collision.contains(Direction.TOP)){
            System.out.print("Top");
            if (this.getYVel() < 0) {
                this.setYVel(0);
            }
            this.getHitBox().setYTop(entity.getHitBox().getYBottom());
        }
        if (collision.contains(Direction.LEFT)) {
            System.out.print("Left");
            if (this.getXVel() < 0) {
                this.setXVel(0);
            }
            this.getHitBox().setXLeft(entity.getHitBox().getXRight());
        }
        if (collision.contains(Direction.RIGHT)) {
            System.out.print("Right");
            if (this.getXVel() > 0) {
                this.setXVel(0);
            }
            this.getHitBox().setXLeft(entity.getHitBox().getXLeft() - this.getHitBox().getXSize());
        }
    }

    public void updateVelocity(Player player) {
        if(player.getHitBox().getXLeft() < this.getHitBox().getXLeft()){
            this.setXVel(ENEMY_MOVEMENT_SPEED * -1);
        }
        else if(player.getHitBox().getXLeft() > this.getHitBox().getXLeft()){
            this.setXVel(ENEMY_MOVEMENT_SPEED);
        }
        else{
            this.setXVel(0);
        }
    }

    //@Override
    public void checkFutureCollision(IEntity entity) {
        CollisionDirections collision = hitBox.getFutureCollisionDirection(entity.getHitBox(), this.getXVel(), this.getYVel());
        //this if statement is for testing - will be removed
        //if (!collision.contains(CollisionDirection.NONE)) {
        //    yVel = 0;
        //}

        this.processCurrentCollision(entity, collision);
        if(entity instanceof IDamageable && collision.doesCollide() && this.canApplyDamage(collision)){
            this.attemptApplyDamage((IDamageable) entity,collision);
        }
    }

    public void checkCollision(IEntity entity) {}

    @Override
    public void updatePosition() {
        hitBox.translateX(xVel);
        hitBox.translateY(yVel);
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


    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public double getCollisionDamage() {
        return this.damage;
    }

    @Override
    public void setCollisionDamage(double collisionDamage) {
        this.damage = collisionDamage;
    }

    @Override
    public List<Direction> getAppliesDamageDirections() {
        return Arrays.asList(Direction.BOTTOM, Direction.LEFT, Direction.RIGHT);
    }

    @Override
    public List<Direction> getReceivesDamageDirections() {
        return Arrays.asList(Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT);
    }
}
