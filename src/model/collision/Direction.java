package model.collision;

import api.model.collision.IDirection;

public enum Direction implements IDirection {
  RIGHT,
  LEFT,
  TOP,
  BOTTOM,
  NONE;

  @Override
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
