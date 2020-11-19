package api.model;

import model.configuration.ILevelLoader;
import api.model.entity.IEntity;
import model.entity.Player;
import api.model.scroll.Scroller;

import java.util.List;
import java.util.Optional;

public interface ILevel {
    void step();

    void removeEntitiesAsNeeded();

    void spawnEntitiesAsNeeded();

    void updateModifiers();

    void updateVelocities();

    void checkCollisions();

    void updatePositions();

    void scroll();

    void translateAllEntities(double xChange, double yChange);

    void translateEntity(IEntity entity, double xChange, double yChange);

    void setScroller(Scroller configScroller);

    int getLevelLength();

    int getLevelWidth();

    void setOrResetLevel(ILevelLoader ILevelLoader);

    void addEntity(IEntity entity);

    void removeEntity(IEntity entity);

    Optional<IEntity> getEntityAt(int xCoordinate, int yCoordinate);

    void keepPlayerInBounds(Player player);

    void checkWinLoseConditions();

    void checkFellOutOfLevel();

    void reinitialize();

    void setLevelWon(boolean isLevelWon);

    void setLevelLost(boolean isLevelLost);

    boolean isLevelWon();

    IKeyPressFunctions getKeyPressFunctions();

    List<IEntity> getCopyOfEntityList();

    List<IEntity> getAllEntities();

    List<Player> getPlayerList();

    int getScore();

    boolean isLevelLost();

    boolean isSaving();

    void setIsSaving(boolean save);
}
