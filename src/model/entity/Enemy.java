package model.entity;

import model.HitBox;
import model.collision.CollisionDirection;

import java.util.Arrays;
import java.util.List;

public class Enemy implements IMobileEntity, IDamageable{
    private final HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private double xVel = 0;
    private double yVel = 0;
    private boolean grounded = true;
    private boolean gracePeriodBeforeFalling = true;
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
    public void checkCollision(IEntity entity) {
        CollisionDirection collision = hitBox.getCollisionDirection(entity.getHitBox());
        //this if statement is for testing - will be removed
        //if (!collision.contains(CollisionDirection.NONE)) {
        //    yVel = 0;
        //}

        this.checkGravity(entity, collision);
        if(entity instanceof IDamageable && this.canApplyDamage(collision)){
            this.attemptApplyDamage((IDamageable) entity,collision);
            System.out.println("Enemy is attempting to attack from direction " + collision.toString() + "!");
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

    @Override
    public boolean getGracePeriodBeforeFalling() {
        return gracePeriodBeforeFalling;
    }
    @Override
    public void setGracePeriodBeforeFalling(boolean isActive) {
        this.gracePeriodBeforeFalling = isActive;
    }

    @Override
    public void setCurrentCollision(CollisionDirection collision) {

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
    public List<CollisionDirection> getAppliesDamageDirections() {
        return Arrays.asList(CollisionDirection.BOTTOM, CollisionDirection.LEFT, CollisionDirection.RIGHT);
    }

    @Override
    public List<CollisionDirection> getReceivesDamageDirections() {
        return Arrays.asList(CollisionDirection.TOP, CollisionDirection.BOTTOM, CollisionDirection.LEFT, CollisionDirection.RIGHT);
    }
}
