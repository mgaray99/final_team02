package model.entity;

import model.collision.CollisionDirections;

public interface IDamageable {

    /**
     * Accesses the current health of this damageable
     * @return the current health of this damageable
     */
    double getHealth();

    /**
     * Sets the current health of this damageable
     * @param health The health to set the health of this damageable to
     */
    void setHealth(double health);

    /**
     * Accesses the current collision damage of this damageable
     * @return The current collision damage of this damageable
     */
    double getCollisionDamage();

    /**
     * Sets the current collision damage of this damageable
     * @param collisionDamage The collision damage to set the collision damage of this damageable to
     */
    void setCollisionDamage(double collisionDamage);

    /**
     * Returns a list of CollisionDirections that this damageable can apply damage from
     * @return The list of CollisionDirections that this damageable can apply damage from
     */
    CollisionDirections getAppliesDamageDirections();

    /**
     * Returns a list of CollisionDirections that this damageable can receive damage from
     * @return The list of CollisionDirections that this damageable can receive damage from
     */
    CollisionDirections getReceivesDamageDirections();

    default boolean isDead(){
        return this.getHealth() <= 0;
    }

    default void attemptApplyDamage(IDamageable damageable, CollisionDirections currentCollidingDirection){
        boolean canApplyDamage = this.canApplyDamage(currentCollidingDirection);
        boolean damageableCanReceiveDamage = damageable.canReceiveDamage(currentCollidingDirection.getOpposites());
        if(canApplyDamage && damageableCanReceiveDamage){
            double currentHealth = damageable.getHealth();
            damageable.setHealth(currentHealth - this.getCollisionDamage());
        }
    }

    default boolean canApplyDamage(CollisionDirections direction){
        return this.getAppliesDamageDirections().oneIsContainedIn(direction);
    }

    default boolean canReceiveDamage(CollisionDirections direction){
        return this.getReceivesDamageDirections().oneIsContainedIn(direction);
    }
}
