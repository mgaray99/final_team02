package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A HitBox class for the entities.
 * Essentially a non-JavaFX version of a Rectangle.
 * It is also capable of detecting collisions with other HitBoxes,
 * and returning the direction of the collision.
 * @author Ryan Krakower
 */

public class HitBox {

  public static final double MAX_INTERSECT = 0.5;
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

  public double getYBottom() {
    return yTop + ySize;
  }

  public void translateX(double deltaX) {
    xLeft += deltaX;
  }

  public void translateY(double deltaY) {
    yTop += deltaY;
  }


  public List<CollisionDirection> getCollisionDirections(HitBox otherBox) {

    List<CollisionDirection> collisions = new ArrayList<>();
    double xRight = xLeft + xSize;
    double yBottom = yTop + ySize;
    if (!((xRight > otherBox.getXLeft() && xLeft < otherBox.getXRight()) &&
        (yBottom > otherBox.getYTop() && yTop < otherBox.getYBottom()))) {
      collisions.add(CollisionDirection.NONE);
      System.out.print("bbbbbbbbbbb");
      return collisions;
    } else {System.out.print("collision");}
    if (between(xRight - otherBox.getXLeft(), 0, MAX_INTERSECT)) {
      collisions.add(CollisionDirection.RIGHT);
    }
    if (between(otherBox.getXRight() - xLeft, 0, MAX_INTERSECT))  {
      collisions.add(CollisionDirection.LEFT);
    }
    if (between(yBottom - otherBox.getYTop(), 0, MAX_INTERSECT))  {
      collisions.add(CollisionDirection.BOTTOM);
    }
    if (between(otherBox.getYBottom() - yTop, 0, MAX_INTERSECT))  {
        collisions.add(CollisionDirection.TOP);
    }
    return collisions;
  }

  private boolean between(double value, double min, double max) {
    if ((value > min) && (value < max)) {
      return true;
    }
    return false;
  }

}
