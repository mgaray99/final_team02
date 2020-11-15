package model.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import model.HitBox;
import model.collision.CollisionDirections;
import model.collision.Direction;

public abstract class Player implements IEntity, IMovable, IDamageable, IPlayer {

  //private final String type = this.getClass().getSimpleName();
  private final String type = "Player";
  public static final int GRACE_PERIOD = 1;
  public static final double GRAVITY_FACTOR = 0.015f;
  private double xVel = 0;
  private double yVel = 0;
  protected HitBox hitBox;
  private boolean grounded = true;
  private int gracePeriodBeforeFalling = GRACE_PERIOD;
  private int gracePeriodBeforeSidewaysMovement = GRACE_PERIOD;
  private double health = 0;
  private double damage = 0;
  private final Map<Modifier.ModifierType, Modifier> modifiers = new HashMap<>();
  private CollisionDirections currentCollision = new CollisionDirections();

  public Player(double x, double y){
    this.hitBox = new HitBox(x, y);
    this.setHealth(100);
    this.setCollisionDamage(100);
  }

  public abstract void updateVelocity(boolean leftKey, boolean rightKey, boolean jumpKey);

  public abstract void updatePosition();

  //public abstract void processCurrentCollision(IEntity entity, CollisionDirections directions);


  public void checkFutureCollision(IEntity entity) {
    CollisionDirections collision = hitBox.getFutureCollisionDirection(entity.getHitBox(), this.getXVel(), this.getYVel());
    currentCollision.add(collision);
    this.processCurrentCollision(entity, collision);
    if (entity instanceof IDamageable) {
      this.attemptApplyDamage((IDamageable) entity, collision);
    }
    if (entity instanceof IEmpowering && !collision.isEmpty()) {
      IEmpowering empowering = (IEmpowering) entity;
      if (!empowering.hasAppliedModifier()) {
        this.applyModifier(empowering.getModifier());
        empowering.setHasAppliedModifier(true);
      }
    }
    if (entity instanceof ISpawner && !collision.isEmpty()) {
      ISpawner spawner = (ISpawner) entity;
      spawner.attemptCreateAndAddSpawn(collision);
    }
  }

  @Override
  public HitBox getHitBox() {
    return hitBox;
  }

  @Override
  public boolean isDead() {
    return this.health <= 0;
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

  //@Override
  public void translateHitBox() {
    //if (!((this.getCurrentCollision() == CollisionDirection.LEFT && this.getXVel() < 0) || (
    //   this.getCurrentCollision() == CollisionDirection.RIGHT && this.getXVel() > 0))) {

    //if (immobilized) {
    //  return;
    //}
    //if (gracePeriodBeforeFalling == 0) {
      this.getHitBox().translateY(this.getYVel());
    //}
    //if (gracePeriodBeforeSidewaysMovement == 0) {
      this.getHitBox().translateX(this.getXVel());
    //}
    this.currentCollision.clear();
  }

  protected void resetGracePeriodBeforeFalling() {
    this.gracePeriodBeforeFalling = GRACE_PERIOD;
  }

  protected void incrementGracePeriodBeforeFalling() {
    this.gracePeriodBeforeFalling -= 1;
  }

  protected void incrementGracePeriodBeforeSidewaysMovement() {
    this.gracePeriodBeforeSidewaysMovement -= 1;
  }

  protected void applyGravity() {
    if (this.getModifiers().containsKey(Modifier.ModifierType.GRAVITY)) {
      this.setYVel(
          this.getYVel() + GRAVITY_FACTOR * this.getModifiers().get(Modifier.ModifierType.GRAVITY)
              .getValue());
    } else {
      this.setYVel(this.getYVel() + GRAVITY_FACTOR);
    }
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
  public CollisionDirections getAppliesDamageDirections() {
    return new CollisionDirections(Collections.singletonList(Direction.BOTTOM));
  }

  @Override
  public CollisionDirections getReceivesDamageDirections() {
    return new CollisionDirections(Arrays.asList(Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT));
  }

  protected CollisionDirections getCurrentCollision() {
    return this.currentCollision;
  }

  @Override
  public Map<Modifier.ModifierType, Modifier> getModifiers() {
    return this.modifiers;
  }

  @Override
  public Teams getTeam() {
    return Teams.PLAYER;
  }
}
