package model.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import model.entity.*;
import api.model.configuration.IEntityFactory;
import api.model.entity.IEntity;
import api.model.entity.IMovable;
import api.model.entity.IWinnable;

/**
 * A class responsible for creating the initial state of a game
 * @author Mike Garay
 */

public class LevelLoader implements ILevelLoader {
    //private static final String ENTITY_PACKAGE_PATH = "model.entity.";
    private final List<Player> playerList = new ArrayList<>();
    private final List<Enemy> enemyList = new ArrayList<>();
    private final List<PowerUp> powerUpList = new ArrayList<>();
    private final List<Block> blockList = new ArrayList<>();
    private final List<IEntity> entityList = new ArrayList<>();
    private final List<IMovable> movableEntityList = new ArrayList<>();
    private final List<IWinnable> winnableList = new ArrayList<>();
    private List<IEntity> entityCopy = new ArrayList<>();
    //private final Map<String, String> levelDecoder;
    private api.model.configuration.IEntityFactory IEntityFactory;

    private int levelLength;
    private int levelWidth;

    /**
     * Constructs a LevelLoader given a CSV file
     * @param levelFileIn The CSV File to be used for seed creation
     * @throws InvalidFileException if the file is a directory,
     * an invalid file, or not a CSV file
     */
    public LevelLoader(File levelFileIn, IEntityFactory factory) throws InvalidFileException {
        this.handleConstructionExceptions(levelFileIn);
        IEntityFactory = factory;
        this.initializeEntityLists(levelFileIn);
        entityCopy = defensivelyCopyList(entityList);
    }

    @Override
    public int getLevelLength() {
        return levelLength;
    }

    @Override
    public int getLevelWidth() {
        return levelWidth;
    }

    @Override
    public List<Player> getCopyOfPlayerList() {
        return new ArrayList<>(playerList);
    }

    @Override
    public List<IMovable> getCopyOfMovableEntityList() { return new ArrayList<>(movableEntityList);}

    @Override
    public List<IWinnable> getCopyOfWinnableList() { return new ArrayList<>(winnableList);}

    @Override
    public List<Enemy> getCopyOfEnemyList() {
        return new ArrayList<Enemy>(enemyList);
    }

    @Override
    public List<Block> getCopyOfBlockList() {
        return new ArrayList<Block>(blockList);
    }

    @Override
    public List<PowerUp> getCopyOfPowerUpList() {
        return new ArrayList<PowerUp>(powerUpList);
    }

    @Override
    public List<IEntity> getCopyOfEntityList() {
        return new ArrayList<IEntity>(entityList);
    }

    @Override
    public void initializeEntityLists(File levelFileIn) throws InvalidFileException {
        try {
            Scanner fileReader = new Scanner(levelFileIn);
            int yCounter = 0;
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String[] currentStringArray = currentLine.split(",");
                for (int xIndex = 0; xIndex < currentStringArray.length; xIndex++) {
                    String entityString = currentStringArray[xIndex];
                    Optional<IEntity> optionalEntity = this.IEntityFactory.createEntity(entityString, xIndex, yCounter);
                    optionalEntity.ifPresent(this::addEntityToLists);
                    if(this.levelWidth < xIndex+1){
                        this.levelWidth = xIndex+1;
                    }

                }
                yCounter++;
            }
            this.levelLength = yCounter;
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new InvalidFileException(ModelExceptionReason.FILE_NOT_FOUND, levelFileIn.getPath());
        }
    }

    @Override
    public void addEntityToLists(IEntity entity) {
        if(entity != null) {
            this.entityList.add(entity);
            if(entity instanceof IMovable){
                this.movableEntityList.add((IMovable)entity);
            }
            if(entity instanceof Player){
                this.playerList.add((Player)entity);
            }
            else if(entity instanceof Enemy){
                this.enemyList.add((Enemy)entity);
            }
            else if(entity instanceof Block){
                this.blockList.add((Block)entity);
            }
            else if(entity instanceof PowerUp){
                this.powerUpList.add((PowerUp)entity);
            }
            else if(entity instanceof Goal){
                this.winnableList.add((Goal)entity);
            }
        }
    }

    /**
     * Copies a list of IEntity into another list and returns this other list. This copying
     * generates new IEntity objects for the other list to avoid aliasing issues.
     *
     * @param originalCopy the original list to be copied from
     * @return a new list with absolutely no dependencies
     */
    @Override
    public List<IEntity> defensivelyCopyList(List<IEntity> originalCopy) {
        List<IEntity> copyList = new ArrayList<>();

        for(IEntity originalEntity : originalCopy){
            Optional<IEntity> optionalEntity = IEntityFactory.reflectEntity(
                    originalEntity.getClass().getSimpleName(),
                    originalEntity.getHitBox().getXLeft(),
                    originalEntity.getHitBox().getYTop());
            optionalEntity.ifPresent(copyList::add);
        }

        return copyList;
    }

    /**
     * Reinitializes the level loader (i.e. resets all lists to have their contents when the
     * LevelLoader was first instantiated
     */
    @Override
    public void reinitialize() {
        this.playerList.clear();
        this.blockList.clear();
        this.enemyList.clear();
        this.powerUpList.clear();
        this.entityList.clear();
        this.winnableList.clear();
        this.movableEntityList.clear();

        List<IEntity> concurrentEntityCopy = defensivelyCopyList(entityCopy);
        concurrentEntityCopy.forEach(entity -> addEntityToLists(entity));

    }

    @Override
    public void handleConstructionExceptions(File levelFileIn) throws InvalidFileException {
        if(levelFileIn.isDirectory()){
            throw new InvalidFileException(
                    ModelExceptionReason.DIRECTORY,
                    levelFileIn.getPath());
        }

        else if(!levelFileIn.isFile()){
            throw new InvalidFileException(
                    ModelExceptionReason.NOT_A_FILE,
                    levelFileIn.getPath());
        }

        else if(!FileHelper.isCSVFile(levelFileIn)){
            throw new InvalidFileException(
                    ModelExceptionReason.NOT_A_CSV_FILE,
                    levelFileIn.getPath());
        }
    }


}
