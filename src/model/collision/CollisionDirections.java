package model.collision;

import api.model.collision.ICollisionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a list of collision
 */
public class CollisionDirections implements ICollisionHandler {
  public List<Direction> directionsList = new ArrayList<>();

  public CollisionDirections(List<Direction> directions) {
    directionsList = directions;
  }

  public CollisionDirections() {}

  @Override
  public void add(Direction direction) {
    if (!directionsList.contains(direction)) {
      directionsList.add(direction);
    }
  }

  @Override
  public void add(ICollisionHandler directions) {
    for (Direction direction : directions.getRawList()) {
      this.add(direction);
    }
  }

  @Override
  public void remove(Direction direction) {
    directionsList.remove(direction);
  }

  @Override
  public boolean contains(Direction direction) {
    return directionsList.contains(direction);
  }

  @Override
  public List<Direction> getRawList() {
    return directionsList;
  }

  @Override
  public void clear() {
    this.directionsList = new ArrayList<>();
  }

  @Override
  public boolean oneIsContainedIn(ICollisionHandler otherDirections) {
    for (Direction direction : otherDirections.getRawList()) {
      if (this.contains(direction)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsVerticalCollision() {
    return this.contains(Direction.TOP) ^ this.contains(Direction.BOTTOM); // ^ is XOR
  }

  @Override
  public boolean containsHorizontalCollision() {
    return this.contains(Direction.RIGHT) ^ this.contains(Direction.LEFT);
  }

  @Override
  public boolean isEmpty() {
    return directionsList.size() == 0;
  }

  @Override
  public boolean doesCollide() {
    return directionsList.size() > 0 && !directionsList.contains(Direction.NONE);
  }


  @Override
  public ICollisionHandler getOpposites() {
    List<Direction> oppositeList = new ArrayList<>();
    for (Direction direction : directionsList) {
      oppositeList.add(direction.getOpposite());
    }
    return new CollisionDirections(oppositeList);
  }
}
