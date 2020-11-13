package model.physics;

import java.util.List;
import model.entity.IEntity;
import model.entity.IGravitate;

public interface IPhysics {

  public void checkCollisions(List<IGravitate> gravitateEntities, List<IEntity> allEntities);

}
