package model.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameLeaderboard {
  private String path;
  private File leaderboardFile;
  private List<ScoreTuple> scoresList;
  private Scanner leaderboardScanner;
  private String title;
  private static String PATH_START = "./src/resources/leaderboards/";

  /**
   * @param filepath the filepath to the leaderboard xml file
   */
  public GameLeaderboard(String filepath) throws FileNotFoundException {
    path = filepath;

    leaderboardFile = new File(PATH_START + filepath);
    leaderboardScanner = new Scanner(leaderboardFile);


    buildTitle();
    buildScoresList();
    sortScoresList();
  }

  /**
   * Saves the title of the file
   */
  private void buildTitle() {
    if (leaderboardScanner.hasNextLine()) {
      title = leaderboardScanner.nextLine();
    }
  }

  /**
   * Instantiates and builds the scoresList which will hold all of the scores
   */
  private void buildScoresList() {
    scoresList = new ArrayList<>();
    while (leaderboardScanner.hasNextLine()) {
      insertIntoScoresList(leaderboardScanner.nextLine());
    }
  }

  /**
   * Reads data from a line, uses it to construct an ScoreTuple and then
   * adds it to scoresList
   * @param line the line whose data will be inserted into scoresList
   */
  private void insertIntoScoresList(String line) {
    String[] nameScore = line.split(",");

    String name = nameScore[0];
    int score = Integer.parseInt(nameScore[1]);
    ScoreTuple scoreTuple = new ScoreTuple(name, score);
    scoresList.add(scoreTuple);
  }

  /**
   * Sorts the list of scores so that the highest one is at the top
   */
  public void sortScoresList() {
    Collections.sort(scoresList);
  }

  /**
   * Returns the score tuple holding data on the indexth highest score (i.e. a call
   * of getScoreTupleAtPlace(1) would return the String representation of the ScoreTuple
   * for the highest score on the leaderboard
   *
   * @return the String representing the indexth highest placer on the leaderboard
   */
  public String getScoreTupleAtPlace(int index) {
    sortScoresList();
    if (index <= 0 || index > scoresList.size()) {
      return "";
    }
    return scoresList.get(index - 1).toString();
  }

  /**
   * Returns the title associated with the java file
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Adds a ScoreTuple to this game's leaderboard
   * @param toBeAdded the tuple to be added to the leaderboard
   */
  public void addScoreTuple(ScoreTuple toBeAdded) throws IOException{
    scoresList.add(toBeAdded);
    writeTupleToFile(toBeAdded);
    sortScoresList();
  }

  /**
   * Saves the contents of a ScoreTuple to a file
   * @param toBeWritten the tuple whose data will be saved in a file
   */
  private void writeTupleToFile(ScoreTuple toBeWritten) throws IOException {
    String line = toBeWritten.getName() + "," + toBeWritten.getScore();

    FileWriter writer = new FileWriter(PATH_START + path, true);

    writer.write("\n");
    writer.write(line);
    writer.close();
  }
}
