package model.entity2;

import model.HitBox;

public interface IEntity {

    HitBox getHitBox();

    boolean checkCollision(IEntity entity);

    void setXVel(double xVel);

    void setYVel(double yVel);

    double getYVel();

    String getType();

    double getXVel();
}
