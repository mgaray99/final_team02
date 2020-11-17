package model.entity;

public class ZeroGEnemy extends Enemy {

  private final String ID = "Enemy";

  public ZeroGEnemy(double x, double y) {
    super(x,y);
  }
  @Override
  public void updatePosition() {
    this.translateHitbox();
  }

  @Override
  public String getType() {
    return ID;
  }
}
