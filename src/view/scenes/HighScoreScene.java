package view.scenes;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import view.GameScene;
import view.LeaderboardView;


public class HighScoreScene extends GameScene {


    private static final String ID = "HIGHSCORE";
    private static final String BUTTON_FOLDERPATH_SLASH = "./src/resources/buttons/";
    private static final String DOODLE_LEADERBOARD2 = "doodlejump2leaderboard.csv";
    private static final String MARIO_INFINITY_LEADERBOARD = "marioinfinityleaderboard.csv";
    private static final String DOODLE_LEADERBOARD = "doodlejumpleaderboard.csv";
    private static final String FLAPPY_LEADERBOARD = "flappybirdleaderboard.csv";
    private static final String EGG_LEADERBOARD = "eggleaderboard.csv";
    private static final String LEADERBOARD_LOAD_ERROR = "LEADERBOARD_LOAD_ERROR";
    private static final String LEADERBOARD_UPDATE_ERROR = "LEADERBOARD_UPDATE_ERROR";


    private static final double LEADERBOARDY = 300;
    private static final double LEADERBOARD_WIDTH = 100;
    private static final double LEADERBOARD_HEIGHT = 400;

    private List<LeaderboardView> leaderboardViewList;

    public HighScoreScene(Group myRoot, double width, double height) {
        super(myRoot, ID, width, height);

        addButtonsToControllerFromFile(
            BUTTON_FOLDERPATH_SLASH + ID.toLowerCase()+ "buttons.xml");
        buildLeaderBoardViews();
    }

    /**
     * Builds the leaderboard view
     */
    private void buildLeaderBoardViews() {
        leaderboardViewList = new ArrayList<>();
        buildLeaderBoardView(MARIO_INFINITY_LEADERBOARD, 25, LEADERBOARDY);
        buildLeaderBoardView(DOODLE_LEADERBOARD, 175, LEADERBOARDY);
        buildLeaderBoardView(DOODLE_LEADERBOARD2, 325, LEADERBOARDY);
        buildLeaderBoardView(FLAPPY_LEADERBOARD, 475, LEADERBOARDY);
        buildLeaderBoardView(EGG_LEADERBOARD, 625, LEADERBOARDY);
    }

    /**
     * Builds a leaderboard view and adds it to root
     */
    private void buildLeaderBoardView(String path, double x, double y) {
        try {
            LeaderboardView view = new LeaderboardView(path, x, y,
                LEADERBOARD_WIDTH, LEADERBOARD_HEIGHT);
            leaderboardViewList.add(view);
            addElementToRoot(view);
            view.toFront();
        }
        catch (Exception e) {
            updateErrorText(getValueFromBundle(LEADERBOARD_LOAD_ERROR));
        }
    }

    /**
     * Updates the contents of all leaderboards in the scene
     */
    public void updateLeaderboards() {
        for (LeaderboardView leader : leaderboardViewList) {
            try {
                leader.updateLeaderboard();
            }
            catch (FileNotFoundException e) {
                updateErrorText(getValueFromBundle(LEADERBOARD_UPDATE_ERROR));
            }
        }
    }
}
