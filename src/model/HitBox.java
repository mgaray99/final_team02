package model;

/**
 * A HitBox class for the entities.
 * Essentially a non-JavaFX version of a Rectangle.
 * It is also capable of detecting collisions with other HitBoxes,
 * and returning the direction of the collision.
 * @author Ryan Krakower
 */

public class HitBox {

  public static final int X_SIZE = 10;
  public static final int Y_SIZE = 10;
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

  public double getYBottom() {
    return yTop + ySize;
  }

  public void translateX(double deltaX) {
    xLeft += deltaX;
  }

  public void translateY(double deltaY) {
    yTop += deltaY;
  }


  public CollisionDirections getCollisionDirection(HitBox otherBox) {
    if (xLeft+xSize <= otherBox.getXLeft()) {
      return CollisionDirections.RIGHT;
    }
    if (otherBox.getXRight() <= xLeft) {
      return CollisionDirections.LEFT;
    }
    if (yTop+ySize <= otherBox.getYTop()) {
      return CollisionDirections.BOTTOM;
    }
    if (otherBox.getYBottom() <= yTop) {
      return CollisionDirections.TOP;
    }
    return CollisionDirections.NONE;
  }

}
