package model.configuration;

import model.entity.*;
import api.model.entity.IEntity;
import api.model.entity.IMovable;
import api.model.entity.IWinnable;

import java.io.File;
import java.util.List;

public interface ILevelLoader {
    int getLevelLength();

    int getLevelWidth();

    List<Player> getCopyOfPlayerList();

    List<IMovable> getCopyOfMovableEntityList();

    List<IWinnable> getCopyOfWinnableList();

    List<Enemy> getCopyOfEnemyList();

    List<Block> getCopyOfBlockList();

    List<PowerUp> getCopyOfPowerUpList();

    List<IEntity> getCopyOfEntityList();

    void initializeEntityLists(File levelFileIn) throws InvalidFileException;

    void addEntityToLists(IEntity entity);

    List<IEntity> defensivelyCopyList(List<IEntity> originalCopy);

    void reinitialize();

    void handleConstructionExceptions(File levelFileIn) throws InvalidFileException;
}
