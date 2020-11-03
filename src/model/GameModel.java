package model;

import model.configuration.GameConfiguration;
import model.level.Level;

public class GameModel {

    private GameConfiguration gameConfiguration;
    private Level level;

    public GameModel(GameConfiguration gameConfiguration){
        this.gameConfiguration = gameConfiguration;
        this.level = Level.fromConfiguration(gameConfiguration);
    }

    public void updateGame(){
        this.level.step();
    }
}
