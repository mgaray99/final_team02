package api.model.entity;

import model.collision.CollisionDirections;
import model.collision.Direction;
import model.entity.Teams;
import api.model.collision.ICollisionHandler;

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
    ICollisionHandler getAppliesDamageDirections();

    /**
     * Returns a list of CollisionDirections that this damageable can receive damage from
     * @return The list of CollisionDirections that this damageable can receive damage from
     */
    ICollisionHandler getReceivesDamageDirections();

    default boolean isDead(){
        return this.getHealth() <= 0;
    }

    default void attemptApplyDamage(IDamageable damageable, CollisionDirections currentCollidingDirections){
        boolean isOnSameTeam = this.isOnSameTeam(damageable);
        boolean isEmpty =  currentCollidingDirections.isEmpty();
        for(Direction direction : currentCollidingDirections.directionsList){
            boolean canApplyDamage = this.canApplyDamageToDirection(direction);
            boolean damageableCanReceiveDamage = damageable.canReceiveDamageFromDirection(direction.getOpposite());

            if(canApplyDamage && damageableCanReceiveDamage && !isOnSameTeam && !isEmpty){
                double currentHealth = damageable.getHealth();
                damageable.setHealth(currentHealth - this.getCollisionDamage());
            }
        }
    }

    default boolean canApplyDamageToDirection(Direction direction){
        return this.getAppliesDamageDirections().contains(direction);
    }

    default boolean canReceiveDamageFromDirection(Direction direction){
        return this.getReceivesDamageDirections().contains(direction);
    }

    default boolean isOnSameTeam(IDamageable damageable){
        return this.getTeam() == damageable.getTeam();
    }

    Teams getTeam();
}
