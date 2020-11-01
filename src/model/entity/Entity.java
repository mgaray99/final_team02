package model.entity;

import java.awt.Rectangle;

public class Entity {

    private final int HIT_BOX_X_SIZE = 10;
    private final int HIT_BOX_Y_SIZE = 10;

    private final Rectangle hitBox;

    public Entity(int xUpperLeft, int yUpperLeft){
        this.hitBox = new Rectangle(xUpperLeft, yUpperLeft, HIT_BOX_X_SIZE, HIT_BOX_Y_SIZE);
    }

    //translate

    //

    protected Rectangle getHitBox(){
        return this.hitBox;
    }

    /*public static class HitBox2D{
        private float xUpperLeft;
        private float yUpperLeft;
        private final float width = 1;
        private final float height = 1;

        public HitBox2D(float xUpperLeft, float yUpperLeft){
            this.xUpperLeft = xUpperLeft;
            this.yUpperLeft = yUpperLeft;
        }

        public float getxUpperLeft() {
            return this.xUpperLeft;
        }

        public float getyUpperLeft() {
            return this.yUpperLeft;
        }

        public float getWidth() {
            return this.width;
        }

        public float getHeight() {
            return this.height;
        }

        public boolean intersects(HitBox2D otherHitBox2D){
            float otherWidth = otherHitBox2D.getWidth();
            float otherHeight = otherHitBox2D.getHeight();
            if (otherWidth <= 0 || otherHeight <= 0) {
                return false;
            }
            float otherX = otherHitBox2D.getxUpperLeft();
            float otherY = otherHitBox2D.getyUpperLeft();
            return (otherX + otherWidth > this.xUpperLeft &&
                    otherY + otherHeight > this.yUpperLeft &&
                    otherX < this.xUpperLeft + this.width &&
                    otherY < this.yUpperLeft + this.height);
        }

    }*/
}
