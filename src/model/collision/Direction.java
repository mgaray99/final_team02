package model.collision;

public enum Direction {
  RIGHT,
  LEFT,
  TOP,
  BOTTOM,
  NONE;

  public Direction getOpposite(){
    switch (this){
      case TOP -> {
        return BOTTOM;
      }
      case LEFT -> {
        return RIGHT;
      }
      case BOTTOM -> {
        return TOP;
      }
      case RIGHT -> {
        return LEFT;
      }
      default -> {
        return NONE;
      }
    }
  }
}
