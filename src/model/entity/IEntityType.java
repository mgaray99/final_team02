package model.entity;

public interface IEntityType{
    String getTypeID();

    boolean isAffectedByGravity();

    boolean shouldCheckCollisions();
}