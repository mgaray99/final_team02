package api.model.collision;

import model.collision.Direction;

import java.util.List;

public interface ICollisionHandler {
    void add(Direction direction);

    void add(ICollisionHandler directions);

    void remove(Direction direction);

    boolean contains(Direction direction);

    List<Direction> getRawList();

    void clear();

    boolean oneIsContainedIn(ICollisionHandler otherDirections);

    boolean containsVerticalCollision();

    boolean containsHorizontalCollision();

    boolean isEmpty();

    boolean doesCollide();

    ICollisionHandler getOpposites();
}
