package model.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import model.entity2.Block;
import model.entity2.Enemy;
import model.entity2.IEntity;
import model.entity2.Player;
import model.entity2.PowerUp;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class responsible for creating the initial state of a game
 * @author Mike Garay
 */

public class LevelLoader {
    private static final String ENTITY_PACKAGE_PATH = "src.model.entity2.";
    private final List<Player> playerList = new ArrayList<>();
    private final List<Enemy> enemyList = new ArrayList<>();
    private final List<PowerUp> powerUpList = new ArrayList<>();
    private final List<Block> blockList = new ArrayList<>();
    private final List<IEntity> entityList = new ArrayList<>();
    private final Map<String, String> levelDecoder;

    private int levelLength;
    private int levelWidth;

    /**
     * Constructs a LevelLoader given a CSV file
     * @param levelFileIn The CSV File to be used for seed creation
     * @throws InvalidFileException if the file is a directory,
     * an invalid file, or not a CSV file
     */
    public LevelLoader(File levelFileIn) throws InvalidFileException {
        this.handleConstructionExceptions(levelFileIn);
        //alex start
        try {
            LevelDecoder decoderMap = new LevelDecoder();
            levelDecoder = decoderMap.getIdToEntityMap();
        }
        catch (Exception e) {
            throw new InvalidFileException(ModelExceptionReason.FILE_NOT_FOUND, levelFileIn.getPath());
        }
        //alex end
        this.initializeEntityLists(levelFileIn);
    }

    public int getLevelLength() {
        return levelLength;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public List<Block> getBlockList() {
        return blockList;
    }

    public List<PowerUp> getPowerUpList() {
        return powerUpList;
    }

    public List<IEntity> getEntityList() {
        return entityList;
    }

    private void initializeEntityLists(File levelFileIn) throws InvalidFileException {
        try {
            Scanner fileReader = new Scanner(levelFileIn);
            int rowCounter = 0;
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String[] currentStringArray = currentLine.split(",");
                for (int colIndex = 0; colIndex < currentStringArray.length; colIndex++) {
                    String entityString = currentStringArray[colIndex];
                    this.createAndAddEntity(entityString, rowCounter, colIndex);
                    if(this.levelWidth < colIndex){
                        this.levelWidth = colIndex;
                    }

                }
                rowCounter++;
            }
            this.levelLength = rowCounter;
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new InvalidFileException(ModelExceptionReason.FILE_NOT_FOUND, levelFileIn.getPath());
        }
    }

    private void createAndAddEntity(String entityString, int rowIndex, int colIndex) {
        IEntity decodedEntity;
        String decodedEntityString = levelDecoder.get(entityString);
        if(decodedEntityString == null) return;

        decodedEntity = reflectEntity(decodedEntityString, rowIndex, colIndex);
        if(decodedEntity != null) {
            this.entityList.add(decodedEntity);
            if(decodedEntity instanceof Player){
                this.playerList.add((Player)decodedEntity);
            }
            else if(decodedEntity instanceof Enemy){
                this.enemyList.add((Enemy)decodedEntity);
            }
            else if(decodedEntity instanceof Block){
                this.blockList.add((Block)decodedEntity);
            }
            else if(decodedEntity instanceof PowerUp){
                this.powerUpList.add((PowerUp)decodedEntity);
            }
        }
    }

    @Nullable
    private IEntity reflectEntity(String decodedEntityString, int rowIndex, int colIndex) {
        IEntity decodedEntity;
        try {
            Class entityClass = Class.forName(ENTITY_PACKAGE_PATH + decodedEntityString);
            Constructor entityConstructor = entityClass.getConstructor(double.class, double.class);
            decodedEntity = (IEntity) entityConstructor.newInstance(rowIndex, colIndex);
            return decodedEntity;
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | InstantiationException
                | InvocationTargetException
                | IllegalAccessException e) {
            return (IEntity) null;
        }
    }

    private void handleConstructionExceptions(File levelFileIn) throws InvalidFileException {
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
