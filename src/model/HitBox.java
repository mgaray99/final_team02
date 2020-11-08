package model;

public class HitBox {

  double xLeft;
  double yTop;
  int xSize;
  int ySize;

  public HitBox(double xLeft, double yTop, int xSize, int ySize) {
    this.xLeft = xLeft;
    this.xSize = xSize;
    this.yTop = yTop;
    this.ySize = ySize;
  };

  public double getXLeft() {
    return xLeft;
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

  public CollisionDirections checkCollision(HitBox otherBox) {
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
