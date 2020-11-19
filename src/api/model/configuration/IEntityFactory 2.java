package api.model.configuration;

import api.model.entity.IEntity;

import java.util.Optional;

public interface IEntityFactory {
    void updatePlayerMapping(String newMapping) throws NullPointerException;

    Optional<IEntity> createEntity(String entityString, double rowIndex, double colIndex);

    Optional<IEntity> reflectEntity(String decodedEntityString, double rowIndex, double colIndex);
}
