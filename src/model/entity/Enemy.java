package model.entity;

import model.HitBox;
import model.collision.CollisionDirections;
import model.collision.Direction;
import api.model.collision.ICollisionHandler;
import api.model.entity.IDamageable;
import api.model.entity.IEntity;
import api.model.entity.IMovable;

import java.util.Arrays;

public class Enemy implements IEntity, IMovable, IDamageable {

    private static final int GRACE_PERIOD = 2;
    private static final double ENEMY_MOVEMENT_SPEED = 0.1;
    public static final double GRAVITY_FACTOR = 0.015f;
    private final HitBox hitBox;
    private final String type = this.getClass().getSimpleName();
    private double xVel = 0;
    private double yVel = 0;
    private boolean grounded = true;
    private double health = 0;
    private double damage = 0;
    private ICollisionHandler currentCollision = new CollisionDirections();


    public Enemy(double x, double y){
        this.hitBox = new HitBox(x, y);
        this.setHealth(100);
        this.setCollisionDamage(100);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
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

    public void checkCollision(IEntity entity) {
        CollisionDirections collision = hitBox.getCollisionDirection(entity.getHitBox());
        currentCollision.add(collision);

        this.processCurrentCollision(entity, collision);
        if(entity instanceof IDamageable){
            this.attemptApplyDamage((IDamageable) entity,collision);
        }
    }

    @Override
    public void updatePosition() {
        if (!this.getCurrentCollision().contains(Direction.BOTTOM)) {
            this.applyGravity();
        }
        translateHitbox();
    }

    protected void translateHitbox() {
        hitBox.translateX(xVel);
        hitBox.translateY(yVel);
        this.currentCollision.clear();
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
    public boolean isDead() {
        return this.health <= 0;
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
    public ICollisionHandler getAppliesDamageDirections() {
        return new CollisionDirections(Arrays.asList(Direction.BOTTOM, Direction.LEFT, Direction.RIGHT));
    }

    @Override
    public ICollisionHandler getReceivesDamageDirections() {
        return new CollisionDirections(Arrays.asList(Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT));
    }
    @Override
    public Teams getTeam() {
        return Teams.ENEMY;
    }

    protected void applyGravity() {
        this.setYVel(this.getYVel() + GRAVITY_FACTOR);
    }

    protected ICollisionHandler getCurrentCollision() {
        return this.currentCollision;
    }
}
