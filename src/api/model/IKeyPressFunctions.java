package api.model;

public interface IKeyPressFunctions {
    boolean isPaused();

    boolean isPlayerMovingLeft();

    boolean isPlayerMovingRight();

    boolean isPlayerJumping();

    void pauseGame();

    void resumeGame();

    void startMovingPlayerLeft();

    void stopMovingPlayerLeft();

    void startMovingPlayerRight();

    void stopMovingPlayerRight();

    void startPlayerJumping();

    void stopPlayerJumping();
}
