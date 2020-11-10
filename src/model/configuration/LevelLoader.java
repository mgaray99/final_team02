package model.configuration;

import java.util.List;

import model.entity.Block;
import model.entity.Enemy;
import model.entity.IEntity;
import model.entity.Player;
import model.entity.PowerUp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class responsible for creating the initial state of a game
 * @author Mike Garay
 */

public class LevelLoader {
    //private static final String ENTITY_PACKAGE_PATH = "model.entity.";
    private final List<Player> playerList = new ArrayList<>();
    private final List<Enemy> enemyList = new ArrayList<>();
    private final List<PowerUp> powerUpList = new ArrayList<>();
    private final List<Block> blockList = new ArrayList<>();
    private final List<IEntity> entityList = new ArrayList<>();
    //private final Map<String, String> levelDecoder;
    private final EntityFactory entityFactory = new EntityFactory();

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
        /*
        try {
            LevelDecoder decoderMap = new LevelDecoder();
            levelDecoder = decoderMap.getIdToEntityMap();
        }
        catch (Exception e) {
            throw new InvalidFileException(ModelExceptionReason.FILE_NOT_FOUND, levelFileIn.getPath());
        }

         */
        //alex end
        this.initializeEntityLists(levelFileIn);
    }

    public int getLevelLength() {
        return levelLength;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public List<Player> getCopyOfPlayerList() {
        return new ArrayList<Player>(playerList);
    }

    public List<Enemy> getCopyOfEnemyList() {
        return new ArrayList<Enemy>(enemyList);
    }

    public List<Block> getCopyOfBlockList() {
        return new ArrayList<Block>(blockList);
    }

    public List<PowerUp> getCopyOfPowerUpList() {
        return new ArrayList<PowerUp>(powerUpList);
    }

    public List<IEntity> getCopyOfEntityList() {
        return new ArrayList<IEntity>(entityList);
    }

    private void initializeEntityLists(File levelFileIn) throws InvalidFileException {
        try {
            Scanner fileReader = new Scanner(levelFileIn);
            int yCounter = 0;
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String[] currentStringArray = currentLine.split(",");
                for (int xIndex = 0; xIndex < currentStringArray.length; xIndex++) {
                    String entityString = currentStringArray[xIndex];
                    IEntity entity = this.entityFactory.createEntity(entityString, xIndex, yCounter);
                    this.addEntityToLists(entity);
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

    private void addEntityToLists(IEntity entity) {
        if(entity != null) {
            this.entityList.add(entity);
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
