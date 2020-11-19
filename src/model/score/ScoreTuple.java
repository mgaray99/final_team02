package model.score;

import org.jetbrains.annotations.NotNull;

public class ScoreTuple implements Comparable<ScoreTuple> {
    private final int score;
    private final String name;

  /**
   * Instantiates a score tuple object
   * @param n the name of the person who attained the score
   * @param s that particular score
   */
  public ScoreTuple(String n, int s) {
      name = n;
      score = s;
    }

  /**
   * The String representation of this object
   * @return a String representation of the form "NAME: SCORE"
   */
  @Override
  public String toString() {
      return name + ": " + score;
  }

  /**
   * Returns the name associated with this ScoreTuple
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the score associated with this tuple
   * @return score
   */
  public int getScore() {
    return score;
  }

  /**
   * Compares this ScoreTuple to another ScoreTuple
   * @param other the other ScoreTuple
   * @return a value determining the ordering of this ScoreTuple and the other
   */
  @Override
  public int compareTo(@NotNull ScoreTuple other) {

    return other.getScore() - this.getScore();
  }
}
