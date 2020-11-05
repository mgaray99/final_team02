package model.entity;

import java.awt.geom.Rectangle2D;

public abstract class Entity {

    private static final int HIT_BOX_X_SIZE = 10;
    private static final int HIT_BOX_Y_SIZE = 10;
    private final IEntityType entityType;
    private final Rectangle2D.Float hitBox;
    private float xVel = 0;
    private float yVel = 0;

    protected Entity(){
        this.hitBox = new Rectangle2D.Float();
        this.entityType = EntityType.EMPTY;
    }

    public boolean shouldCheckCollisions() {return false;};
    public boolean affectedByGravity() {return false;};
    public boolean isGrounded() {return true;}


    public final boolean isEmpty(){
        return this.entityType == EntityType.EMPTY;
    }

    public Entity(IEntityType entityType, int xUpperLeft, int yUpperLeft){
        this.hitBox = new Rectangle2D.Float(xUpperLeft, yUpperLeft, HIT_BOX_X_SIZE, HIT_BOX_Y_SIZE);
        this.entityType = entityType;
    }

    public IEntityType getEntityType() {
        return this.entityType;
    }

    public boolean hasMatchingId(IEntityType entityType, int x, int y){
        return this.getId().equals(entityType.getTypeID() + "@x=" + x + "@y=" + y);
    }

    public String getId(){
        return this.entityType.getTypeID() + "@x=" + this.hitBox.x  + "@y=" + this.hitBox.y;
    }

    public String getTypeId() { return this.entityType.getTypeID(); }

    public Rectangle2D.Float getHitBox(){
        return this.hitBox;
    }

    public boolean checkCollision(Entity entityIn){
        return this.getHitBox().intersects(entityIn.getHitBox());
    }

    public void setXVel(int xVel) {
        this.xVel =xVel;
    }

    public float getYVel(){return yVel;}

    public void setYVel(float yVel) {
        this.yVel =yVel;
    }
    
  public void moveOneStep() {
      hitBox.setRect(hitBox.getX() + xVel, hitBox.getY() + yVel, HIT_BOX_X_SIZE, HIT_BOX_Y_SIZE);
  }
}

