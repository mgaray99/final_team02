package model;

import model.collision.CollisionDirection;

/**
 * A HitBox class for the entities.
 * Essentially a non-JavaFX version of a Rectangle.
 * It is also capable of detecting collisions with other HitBoxes,
 * and returning the direction of the collision.
 *
 * @author Ryan Krakower
 */

public class HitBox {

  public static final double MAX_INTERSECT = 0.5;
  public static final double CORNER_GLITCH_AVOIDANCE_OFFSET = 0.0001;
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

  public double getXLeft() {
    return xLeft;
  }

  public int getXSize() {
    return xSize;
  }

  public int getYSize() {
    return ySize;
  }

  public double getXRight() {
    return xLeft + xSize;
  }

  public double getYTop() {
    return yTop;
  }

  public void setYTop(double yTop) {
    this.yTop = yTop;
  }

  public void setXLeft(double xLeft) {
    this.xLeft = xLeft;
  }

  public double getYBottom() {
    return yTop + ySize;
  }

  public void translateX(double deltaX) {
    xLeft += deltaX;
  }

  public void translateY(double deltaY) {
    yTop += deltaY;
  }


  public CollisionDirection getCollisionDirection(HitBox otherBox) {

    double xRight = xLeft + xSize;
    double yBottom = yTop + ySize;
    if (!((xRight >= otherBox.getXLeft() && xLeft <= otherBox.getXRight()) &&
        (yBottom >= otherBox.getYTop() && yTop <= otherBox.getYBottom()))) {
      return CollisionDirection.NONE;
    }

    if (between(yBottom - otherBox.getYTop(), 0, MAX_INTERSECT))  {
      return CollisionDirection.BOTTOM;
    }
    if (between(otherBox.getYBottom() - yTop, 0, MAX_INTERSECT))  {
        return CollisionDirection.TOP;
    }

    if (yBottom >= otherBox.getYBottom() + CORNER_GLITCH_AVOIDANCE_OFFSET) {

      if (between(xRight - otherBox.getXLeft(), 0, MAX_INTERSECT)) {
        return CollisionDirection.RIGHT;
      }
      if (between(otherBox.getXRight() - xLeft, 0, MAX_INTERSECT))  {
        return CollisionDirection.LEFT;
      }
    }
    return CollisionDirection.NONE;
  }


  private boolean between(double value, double min, double max) {
    if ((value > min) && (value < max)) {
      return true;
    }
    return false;
  }

}
