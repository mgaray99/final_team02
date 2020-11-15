package model.collision;

import java.util.ArrayList;
import java.util.List;

public class CollisionDirections {
  public List<Direction> directionsList = new ArrayList<>();

  public CollisionDirections(List<Direction> directions) {
    directionsList = directions;
  }

  public CollisionDirections() {}

  public void add(Direction direction) {
    if (!directionsList.contains(direction)) {
      directionsList.add(direction);
    }
  }

  public void add(CollisionDirections directions) {
    for (Direction direction : directions.getRawList()) {
      this.add(direction);
    }
  }

  public void remove(Direction direction) {
    directionsList.remove(direction);
  }

  public boolean contains(Direction direction) {
    return directionsList.contains(direction);
  }

  protected List<Direction> getRawList() {
    return directionsList;
  }

  public void clear() {
    this.directionsList = new ArrayList<>();
  }

  public boolean oneIsContainedIn(CollisionDirections otherDirections) {
    for (Direction direction : otherDirections.getRawList()) {
      if (this.contains(direction)) {
        return true;
      }
    }
    return false;
  }

  public boolean containsVerticalCollision() {
    if (this.contains(Direction.TOP) || this.contains(Direction.BOTTOM)) {
      return true;
    }
    return false;
  }

  public boolean containsHorizontalCollision() {
    if (this.contains(Direction.RIGHT) || this.contains(Direction.LEFT)) {
      return true;
    }
    return false;
  }

  public boolean isEmpty() {
    return directionsList.size() == 0;
  }

  public boolean doesCollide() {
    return directionsList.size() > 0 && !directionsList.contains(Direction.NONE);
  }


  public CollisionDirections getOpposites() {
    List<Direction> oppositeList = new ArrayList<>();
    for (Direction direction : directionsList) {
      oppositeList.add(direction.getOpposite());
    }
    return new CollisionDirections(oppositeList);
  }
}
