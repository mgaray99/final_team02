package model.entity.player;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.HitBox;
import model.collision.CollisionDirection;
import model.entity.IDamageable;
import model.entity.IEmpowerable;
import model.entity.IEntity;
import model.entity.IGravitate;
import model.entity.Modifier;

public abstract class Player2 implements IEntity, IGravitate, IDamageable, IEmpowerable {

  private final String type = this.getClass().getSimpleName();
  public static final int GRACE_PERIOD = 1;
  protected boolean immobilized = false;

  private double xVel = 0;
  private double yVel = 0;
  protected HitBox hitBox;
  private boolean grounded = true;
  private int gracePeriodBeforeFalling = GRACE_PERIOD;
  private int gracePeriodBeforeSidewaysMovement = GRACE_PERIOD;
  private double health = 0;
  private double damage = 0;
  private final Map<Modifier.ModifierType, Modifier> modifiers = new HashMap<>();
  private CollisionDirection currentCollision;

  public Player2(double x, double y){
    this.hitBox = new HitBox(x, y);
    this.setHealth(100);
    this.setCollisionDamage(100);
  }


  @Override
  public HitBox getHitBox() {
    return hitBox;
  }

  @Override
  public abstract void checkCollision(IEntity entity);


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
    if (this.getGracePeriodBeforeSidewaysMovement() > 0) {
      this.subtractFromGracePeriodBeforeSidewaysMovement();
      this.setXVel(0);
    } else {
      this.getHitBox().translateX(this.getXVel());
    }

    //}
    this.getHitBox().translateY(this.getYVel());
    this.setGrounded(false);
  }

  public void subtractFromGracePeriodBeforeSidewaysMovement() {
    gracePeriodBeforeSidewaysMovement -= 1;
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
    return Collections.singletonList(CollisionDirection.BOTTOM);
  }

  @Override
  public List<CollisionDirection> getReceivesDamageDirections() {
    return Arrays.asList(CollisionDirection.TOP, CollisionDirection.BOTTOM, CollisionDirection.LEFT, CollisionDirection.RIGHT);
  }

  public void setCurrentCollision(CollisionDirection direction) {
    this.currentCollision = direction;
  }

  private CollisionDirection getCurrentCollision() {
    return this.currentCollision;
  }


  @Override
  public Map<Modifier.ModifierType, Modifier> getModifiers() {
    return this.modifiers;
  }
}
