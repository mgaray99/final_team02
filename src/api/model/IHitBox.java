package api.model;

import model.HitBox;
import model.collision.CollisionDirections;

public interface IHitBox {
    double getXLeft();

    int getXSize();

    int getYSize();

    double getXRight();

    double getYTop();

    void setYTop(double yTop);

    void setYBottom(double yBottom);

    void setXRight(double xRight);

    void setXLeft(double xLeft);

    double getYBottom();

    void translateX(double deltaX);

    void translateY(double deltaY);

    CollisionDirections getCollisionDirections(HitBox otherBox);

    default boolean between(double value, double min, double max) {
        if ((value > min) && (value < max)) {
            return true;
        }
        return false;
    }
}
