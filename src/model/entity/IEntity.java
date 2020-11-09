package model.entity;

import model.HitBox;

public interface IEntity {

    HitBox getHitBox();

    void checkCollision(IEntity entity);

    void setXVel(double xVel);

    void setYVel(double yVel);

    double getYVel();

    String getType();

    double getXVel();

    default void moveOneStep(){
        this.getHitBox().translateX(this.getXVel());
        this.getHitBox().translateY(this.getYVel());
    }

}
