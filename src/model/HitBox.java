package model;

import model.collision.CollisionDirections;
import model.collision.Direction;
import api.model.IHitBox;

/**
 * A HitBox class for the entities.
 * Essentially a non-JavaFX version of a Rectangle.
 * It is also capable of detecting collisions with other HitBoxes,
 * and returning the direction of the collision.
 *
 * @author Ryan Krakower
 */

public class HitBox implements IHitBox {

  public static final double MAX_SIDE_INTERSECT = 0.5;
  public static final double MAX_TOP_INTERSECT = 0.35;
  public static final double MAX_BOTTOM_INTERSECT = 1 - MAX_TOP_INTERSECT;
  public static final double CORNER_GLITCH_AVOIDANCE_OFFSET = 0.005;
  public static final int X_SIZE = 1;
  public static final int Y_SIZE = 1;
  double xLeft;
  double yTop;
  int xSize;
  int ySize;

  public HitBox(double xLeft, double yTop){
    this.xLeft = xLeft;
    this.yTop = yTop;
    this.xSize = X_SIZE;
    this.ySize = Y_SIZE;
  }

  public HitBox(double xLeft, double yTop, int xSize, int ySize) {
    this.xLeft = xLeft;
    this.xSize = xSize;
    this.yTop = yTop;
    this.ySize = ySize;
  };

  @Override
  public double getXLeft() {
    return xLeft;
  }

  @Override
  public int getXSize() {
    return xSize;
  }

  @Override
  public int getYSize() {
    return ySize;
  }

  @Override
  public double getXRight() {
    return xLeft + xSize;
  }

  @Override
  public double getYTop() {
    return yTop;
  }

  @Override
  public void setYTop(double yTop) {
    this.yTop = yTop;
  }

  @Override
  public void setYBottom(double yBottom) {
    this.yTop = yBottom - ySize;
  }

  @Override
  public void setXRight(double xRight) {
    this.xLeft = xRight - xSize;
  }

  @Override
  public void setXLeft(double xLeft) {
    this.xLeft = xLeft;
  }

  @Override
  public double getYBottom() {
    return yTop + ySize;
  }

  @Override
  public void translateX(double deltaX) {
    xLeft += deltaX;
  }

  @Override
  public void translateY(double deltaY) {
    yTop += deltaY;
  }


  @Override
  public CollisionDirections getCollisionDirections(HitBox otherBox) {
    CollisionDirections directions = new CollisionDirections();
    double xRight = xLeft + xSize;
    double yBottom = yTop + ySize;
    if (!((xRight > otherBox.getXLeft() && xLeft < otherBox.getXRight()) &&
        (yBottom > otherBox.getYTop() && yTop < otherBox.getYBottom()))) {
      return directions;
    }

      if (between(yBottom - otherBox.getYTop(), CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_BOTTOM_INTERSECT)) {
        directions.add(Direction.BOTTOM);
      }
      if (between(otherBox.getYBottom() - yTop, CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_TOP_INTERSECT)) {
        directions.add(Direction.TOP);
      }


      if (between(xRight - otherBox.getXLeft(), CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_SIDE_INTERSECT)) {
        directions.add(Direction.RIGHT);
      }
      if (between(otherBox.getXRight() - xLeft, CORNER_GLITCH_AVOIDANCE_OFFSET, MAX_SIDE_INTERSECT))  {
        directions.add(Direction.LEFT);
      }
    return directions;
  }

}
