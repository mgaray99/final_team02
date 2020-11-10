package model.entity;

import model.collision.CollisionDirection;

import java.util.List;

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
    List<CollisionDirection> getAppliesDamageDirections();

    /**
     * Returns a list of CollisionDirections that this damageable can receive damage from
     * @return The list of CollisionDirections that this damageable can receive damage from
     */
    List<CollisionDirection> getReceivesDamageDirections();

    default boolean isDead(){
        return this.getHealth() <= 0;
    }

    default void attemptApplyDamage(IDamageable damageable, CollisionDirection currentCollidingDirection){
        boolean canApplyDamage = this.canApplyDamage(currentCollidingDirection);
        boolean damageableCanReceiveDamage = damageable.canReceiveDamage(currentCollidingDirection.getOpposite());
        if(canApplyDamage && damageableCanReceiveDamage){
            double currentHealth = damageable.getHealth();
            damageable.setHealth(currentHealth - this.getCollisionDamage());
        }
    }

    default boolean canApplyDamage(CollisionDirection collisionDirection){
        return this.getAppliesDamageDirections().contains(collisionDirection);
    }

    default boolean canReceiveDamage(CollisionDirection collisionDirection){
        return this.getReceivesDamageDirections().contains(collisionDirection);
    }
}
