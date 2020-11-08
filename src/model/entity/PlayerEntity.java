package model.entity;

public class PlayerEntity extends LivingEntity{

    private boolean hasWonLevel;

    public PlayerEntity(int xUpperLeft, int yUpperLeft, int healthIn) {
        super(EntityType.PLAYER, xUpperLeft, yUpperLeft, healthIn);
        this.hasWonLevel = false;
    }

    @Override
    public boolean checkCollision(Entity entityIn) {
        boolean hasCollided =  super.checkCollision(entityIn);
        boolean entityIsGoal = entityIn.getEntityType().getTypeID().equals(EntityType.GOAL.toString());
        if(hasCollided && entityIsGoal){
            this.hasWonLevel = true;
        }
        return hasCollided;
    }

    public boolean hasWonLevel() {
        return this.hasWonLevel;
    }
}
