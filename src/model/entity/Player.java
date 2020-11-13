package model.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.HitBox;
import model.collision.Direction;

public abstract class Player implements IEntity, IMovable, IDamageable, IEmpowerable {

  private final String type = this.getClass().getSimpleName();
  public static final int GRACE_PERIOD = 1;
  protected boolean immobilized = false;
  private boolean canMoveUp = true;
  private boolean canMoveRight = true;
  private double xVel = 0;
  private double yVel = 0;
  protected HitBox hitBox;
  private boolean grounded = true;
  private int gracePeriodBeforeFalling = GRACE_PERIOD;
  private int gracePeriodBeforeSidewaysMovement = GRACE_PERIOD;
  private double health = 0;
  private double damage = 0;
  private final Map<Modifier.ModifierType, Modifier> modifiers = new HashMap<>();
  private Direction currentCollision;

  public Player(double x, double y){
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

  @Override
  public abstract void checkFutureCollision(IEntity entity);


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
      this.resetGracePeriodBeforeFalling();
    }
  }

  @Override
  public int getGracePeriodBeforeFalling() {
    return gracePeriodBeforeFalling;
  }
  //@Override
  public void setGracePeriodBeforeFalling(int isActive) {
    this.gracePeriodBeforeFalling = isActive;
  }

  @Override
  public void subtractFromGracePeriodBeforeFalling() {
    gracePeriodBeforeFalling -= 1;
  }


  public int getGracePeriodBeforeSidewaysMovement() {
    return gracePeriodBeforeSidewaysMovement;
  }

  public void setGracePeriodBeforeSidewaysMovement(int isActive) {
    this.gracePeriodBeforeSidewaysMovement = isActive;
  }

  public void resetGracePeriodBeforeSidewaysMovement() {
    this.gracePeriodBeforeSidewaysMovement = GRACE_PERIOD;
  }

  public void resetGracePeriodBeforeFalling() {
    this.gracePeriodBeforeFalling = GRACE_PERIOD;
  }

  @Override
  public void moveOneStep() {
    //if (!((this.getCurrentCollision() == CollisionDirection.LEFT && this.getXVel() < 0) || (
    //   this.getCurrentCollision() == CollisionDirection.RIGHT && this.getXVel() > 0))) {

    if (immobilized) {
      return;
    }
    if (this.canMoveUp) {
      this.getHitBox().translateX(this.getXVel());
    }
    if (this.canMoveRight) {
      this.getHitBox().translateY(this.getYVel());
    }
    this.setGrounded(false);
    this.canMoveUp = true;
    this.canMoveRight = true;
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
    return Collections.singletonList(Direction.BOTTOM);
  }

  @Override
  public List<Direction> getReceivesDamageDirections() {
    return Arrays.asList(Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT);
  }

  protected void setCanMoveUp(boolean canMoveUp) {
    this.canMoveUp = canMoveUp;
  }

  protected void setCanMoveRight(boolean canMoveRight) {
    this.canMoveRight = canMoveRight;
  }

  @Override
  public Map<Modifier.ModifierType, Modifier> getModifiers() {
    return this.modifiers;
  }
}
