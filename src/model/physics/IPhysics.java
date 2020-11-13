package model.physics;

import java.util.List;
import model.entity.IEntity;
import model.entity.IMovable;

public interface IPhysics {

  public void checkCollisions(List<IMovable> gravitateEntities, List<IEntity> allEntities);

}
