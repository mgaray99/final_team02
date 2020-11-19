package api.model.entity;

import model.HitBox;

public interface IEntity {

    HitBox getHitBox();

    void checkCollision(IEntity entity);

    void setXVel(double xVel);

    void setYVel(double yVel);

    double getXVel();

    double getYVel();

    String getType();



}
