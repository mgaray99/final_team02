package model.entity;

import model.HitBox;
import model.collision.CollisionDirection;

import java.util.List;
import java.util.Optional;

public interface ISpawner {


    default Optional<IEntity> attemptSpawnEntity(){
        if(!this.getSpawnList().isEmpty()){
            IEntity entity = this.getSpawnList().get(0);
            this.getSpawnList().remove(0);
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    default void attemptCreateAndAddSpawn(CollisionDirection direction){
        CollisionDirection directionOpposite = direction.getOpposite();
        if(this.getCollisionsRequiredForSpawn().contains(directionOpposite)){
            double xPos = this.getHitBox().getXLeft();
            double yPos = this.getHitBox().getYTop();
            int height = this.getHitBox().getYSize();
            int width = this.getHitBox().getXSize();
            switch(directionOpposite){
                case TOP -> yPos += height;
                case BOTTOM -> yPos -= height;
                case LEFT -> xPos += width;
                case RIGHT -> xPos -= width;
            }
            this.attemptCreateAndAddSpawn(xPos, yPos);
        }
    }

    default void attemptCreateAndAddSpawn(double xPos, double yPos){
        if(this.getMaxSpawnCount() > 0){
            IEntity entity = this.createSpawn(xPos, yPos);
            this.getSpawnList().add(entity);
            this.setMaxSpawnCount(this.getMaxSpawnCount() - 1);
        }
    }

    List<IEntity> getSpawnList();

    List<CollisionDirection> getCollisionsRequiredForSpawn();

    IEntity createSpawn(double x, double y);

    int getMaxSpawnCount();

    void setMaxSpawnCount(int maxSpawnCount);

    // Expected hitbox method that can be implemented by IEntity
    HitBox getHitBox();
}
