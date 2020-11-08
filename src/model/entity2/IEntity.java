package model.entity2;

import model.HitBox;
import model.entity.IEntityType;

public interface IEntity {

    HitBox getHitBox();

    boolean checkCollision(IEntity entity);

}
