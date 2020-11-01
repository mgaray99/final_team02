package ooga.model.entity;

public class Entity{

    private final HitBox2D hitBox2D;

    public Entity(float xUpperLeft, float yUpperLeft){
        this.hitBox2D = new HitBox2D(xUpperLeft, yUpperLeft);
    }

    public void applyMotion(float deltaX, float deltaY){
        this.hitBox2D.xUpperLeft += deltaX;
        this.hitBox2D.yUpperLeft += deltaY;
    }

    public void setPosition(float x, float y){
        this.hitBox2D.xUpperLeft = x;
        this.hitBox2D.yUpperLeft = y;
    }

    public HitBox2D getHitBox(){
        return this.hitBox2D;
    }

    public static class HitBox2D{
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

    }
}
