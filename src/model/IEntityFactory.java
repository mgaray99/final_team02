package model;

import model.entity.Entity;
import model.entity.EntityType;
import model.entity.IEntityType;

public interface IEntityFactory {

    Entity createEntity(IEntityType entityType, int i, int j);
}
