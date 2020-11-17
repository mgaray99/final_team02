package model.entity;

import model.HitBox;
import model.collision.CollisionDirections;
import model.collision.Direction;

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

    default void attemptCreateAndAddSpawn(CollisionDirections direction){
        CollisionDirections directionOpposite = direction.getOpposites();
        if(this.getCollisionsRequiredForSpawn().oneIsContainedIn(directionOpposite)){
            double xPos = this.getHitBox().getXLeft();
            double yPos = this.getHitBox().getYTop();
            int height = this.getHitBox().getYSize();
            int width = this.getHitBox().getXSize();
            if (directionOpposite.contains(Direction.TOP)) {
                yPos += height;
            }
            if (directionOpposite.contains(Direction.BOTTOM)) {
                yPos -= height;
            }
            if (directionOpposite.contains(Direction.LEFT)) {
                xPos += width;
            }
            if (directionOpposite.contains(Direction.RIGHT)) {
                xPos -= width;
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

    CollisionDirections getCollisionsRequiredForSpawn();

    IEntity createSpawn(double x, double y);

    int getMaxSpawnCount();

    void setMaxSpawnCount(int maxSpawnCount);

    HitBox getHitBox();
}
