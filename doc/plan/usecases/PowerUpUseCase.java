class PowerUpUseCase{

    static playerCounter = 0;

    static void main(String[] args){
        int playerHeight = 2;
        int playerWidth = 2;
        int powerUpHeight = 2;
        int powerUpWidth = 2;
        PlayerEntity playerEntity = new PlayerEntity(playerHeight, playerWidth);
        PowerUpEntity powerUpEntity = new PowerUpEntity(powerUpHeight, powerUpWidth);
    }

    class Level{
        int[][] levelMatrix;

        Level(){

        }
    }

    class PlayerEntity{
        int height;
        int width;

        PlayerEntity(int height, int width){
            this.height = height;
            this.width = width;
        }
    }

    class PowerUpEntity{
        int height;
        int width;

        PlayerEntity(int height, int width){
            this.height = height;
            this.width = width;
        }
    }

}