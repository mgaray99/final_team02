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

  public void checkCollision(IEntity entity) {
    CollisionDirections collision = hitBox.getCollisionDirection(entity.getHitBox());
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
    if (entity instanceof IWinnable && !collision.isEmpty()) {
      IWinnable goal = (IWinnable) entity;
      goal.setHasWon(true);
    }
  }

  @Override
  public HitBox getHitBox() {
    return hitBox;
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

  public void translateHitBox() {
    Modifier movementSpeedModifier = this.getModifiers().get(Modifier.ModifierType.MOVEMENT_SPEED);
    double movementSpeedModifierValue = movementSpeedModifier != null ? movementSpeedModifier.getValue() : 1;
    this.getHitBox().translateX(this.getXVel() * movementSpeedModifierValue);

    Modifier jumpSpeedModifier = this.getModifiers().get(Modifier.ModifierType.JUMP_SPEED);
    double jumpSpeedModifierValue = jumpSpeedModifier != null ? jumpSpeedModifier.getValue() : 1;
    this.getHitBox().translateY(this.getYVel() * jumpSpeedModifierValue);

    this.currentCollision.clear();
  }

  protected void applyGravity() {
    double currentYVelWithAppliedGravityFactor = this.getYVel() + GRAVITY_FACTOR;
    if (this.getModifiers().containsKey(Modifier.ModifierType.GRAVITY)) {
      double gravityModifierValue = this.getModifiers().get(Modifier.ModifierType.GRAVITY).getValue();
      this.setYVel(currentYVelWithAppliedGravityFactor * gravityModifierValue);
    } else {
      this.setYVel(currentYVelWithAppliedGravityFactor);
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

  @Override
  public boolean isDead() {
    return this.health <= 0;
  }
}
