package model.entity;

import java.awt.*;

public abstract class Entity {

    private static final int HIT_BOX_X_SIZE = 10;
    private static final int HIT_BOX_Y_SIZE = 10;
    private final IEntityType entityType;

    private final Rectangle hitBox;

    protected Entity(){
        this.hitBox = new Rectangle();
        this.entityType = EntityType.EMPTY;
    }

    public Entity(IEntityType entityType, int xUpperLeft, int yUpperLeft){
        this.hitBox = new Rectangle(xUpperLeft, yUpperLeft, HIT_BOX_X_SIZE, HIT_BOX_Y_SIZE);
        this.entityType = entityType;
    }

    public IEntityType getEntityType() {
        return this.entityType;
    }

    public boolean isEmpty(){
        return this.entityType == EntityType.EMPTY;
    }

    public boolean hasMatchingId(IEntityType entityType, int x, int y){
        return this.getId().equals(entityType.getTypeID() + "@x=" + x + "@y=" + y);
    }

    public String getId(){
        return this.entityType.getTypeID() + "@x=" + this.hitBox.x  + "@y=" + this.hitBox.y;
    }

    public Rectangle getHitBox(){
        return this.hitBox;
    }
}
