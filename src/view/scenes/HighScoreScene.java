package view.scenes;

import javafx.scene.Group;
import view.GameScene;


public class HighScoreScene extends GameScene {


    private static final String ID = "HIGHSCORE";
    private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";


    public HighScoreScene(Group myRoot, double width, double height) {
        super(myRoot, ID, width, height);

        addButtonsToControllerFromFile(
            BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.xml");
    }
}
