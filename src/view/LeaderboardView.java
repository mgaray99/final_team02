package view;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.text.Text;
import model.score.GameLeaderboard;
import api.model.score.IGameLeaderboard;
import api.view.ILeaderboardView;

public class LeaderboardView extends Group implements ILeaderboardView {

  private IGameLeaderboard leaderboard;

  private String path;
  private Text title;
  private List<Text> scoreTexts;

  private static final int NUM_SCORES = 10;

  private double xOffset;
  private double yOffset;
  private double width;
  private double height;

  public LeaderboardView(String filepath, double x, double y, double w, double h)
      throws FileNotFoundException {

    leaderboard = new GameLeaderboard(filepath);

    path = filepath;
    xOffset = x;
    yOffset = y;
    width = w;
    height = h;

    initialize();
    updateLeaderboard();
  }

  /**
   * Initializes this LeaderboardView object
   */
  @Override
  public void initialize() {
    buildTitle();
    scoreTexts = new ArrayList<>();

    for (int index = 1; index <= NUM_SCORES; index+=1) {
      buildScoreText(index);
    }
  }

  /**
   * Builds the title text label
   */
  @Override
  public void buildTitle() {
    title = new Text();
    title.setLayoutX(xOffset + width/4);
    title.setLayoutY(yOffset);
    title.setText(leaderboard.getTitle());
    getChildren().add(title);
  }

  @Override
  public void buildScoreText(double index) {
    Text scoreText = new Text();
    scoreText.setLayoutX(xOffset);
    scoreText.setLayoutY(yOffset + index * 1.0 / NUM_SCORES * height);
    scoreTexts.add(scoreText);
    getChildren().add(scoreText);
  }

  /**
   * Updates the high scores
   */
  @Override
  public void updateLeaderboard() throws FileNotFoundException {
    IGameLeaderboard tempBoard = new GameLeaderboard(path);

    for (int index = 1; index <=10; index+=1) {
      Text currentScoreText = scoreTexts.get(index - 1);
      currentScoreText.setText(index + ". "
          + tempBoard.getScoreTupleAtPlace(index));
    }
  }
}
