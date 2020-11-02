package model.entity;

public abstract class BlockEntity extends Entity{

    public BlockEntity(IEntityType entityType, int xUpperLeft, int yUpperLeft) {
        super(entityType, xUpperLeft, yUpperLeft);
    }
}
