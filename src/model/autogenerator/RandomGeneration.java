package model.autogenerator;

public class RandomGeneration extends GenerationInstruction {

  private static final String LEFT = "LEFT";
  private static final String UP = "UP";

  private String[] specifications;
  private boolean growsLeft;
  private boolean growsUp;

  public RandomGeneration(int rows, int cols, String[] args) {

    super(rows, cols);
    specifications = args;

    try {
      buildInstruction(specifications);
      validate();
    }
    catch (Exception e) {
      throwGenerationException();
    }
  }

  /**
   * Builds a constant instruction based on a String array. The array should be specified as follows
   * args[0] = type (i.e. "Random")
   * args[1] = entity type (i.e. "2" for enemy)
   *
   * args[2] = direction that this random instruction builds in (i.e. LEFT:UP)
   *
   * args[3] = start row specification (i.e. 3)
   * args[4] = start column specification (i.e. 4)
   * Note, an argument of U(x:y) to either args[2] or args[3] means unifomrly generate the
   * start row or column in the range [x,y]
   *
   * args[5] = width specification in the form of a Randomizer String
   * args[6] = height specification in the form of a Randomizer String
   * Note, a randomizer String is R(1,2,3;0.50,0.30,0.20) where the set of arguments before the
   * semi colon is the set of valid values and the set of arguments after the semi-colon is the
   * set of probabilities corresponding to each of those values
   *
   * In this case we would generate 1 with probability 0.50, 2 with probability 0.30 and 3 with
   * probability 0.20
   *
   * @param args the array of arguments
   */
  private void buildInstruction(String[] args) {
    entityType = args[1];
    setDirectionOfGrowth(args[2]);
    buildStartRow(args[3]);
    buildStartCol(args[4]);
    buildRowDepth(args[5]);
    buildColDepth(args[6]);
  }

  /**
   * Extracts data on which directions to expand the generation in from its (x,y) origin from
   * growthString
   *
   * (i.e. "RIGHT:DOWN" would mean that given (X,Y) are the anchor coordinates of the generation
   * and if width is 2 and height is 4, the startRow would be X - 2 and the endRow would be X and the
   * startCol would be Y and endCol would be Y + 4)
   *
   * @param growthString
   */
  private void setDirectionOfGrowth(String growthString) {
    String[] xyDirections = growthString.split(":");
    growsLeft = xyDirections[0].equals(LEFT);
    growsUp = xyDirections[1].equals(UP);
  }

  /**
   * Determines the startRow coordinate from xArg
   * @param xArg either the starting x coordinate or "R" if we should randomize to calculate it
   */
  private void buildStartRow(String xArg) {
    startRow = decodeStartArg(xArg, numRows);
  }

  /**
   * Determines the startCol coordinate from yArg
   * @param yArg either the starting y coordinate or "U" if we should randomize to calculate it
   */
  private void buildStartCol(String yArg) {
    startCol = decodeStartArg(yArg, numCols);
  }

  /**
   * Creates a int value based on a String passed into args[3] or args[4] (startRow or startCol
   * parameters)
   *
   * @param arg the String configuration argyment
   * @param numOffset either numRows or numCols depending on if we're decoding the startRow or
   *                  startCol (i.e. this will be multiplied by a random number to return a
   *                 value in the range [0, numOffset])
   * @return an int based on the String argument
   */
  private int decodeStartArg(String arg, int numOffset) {
    if (arg.charAt(0) == 'U') {
      UniformRandomizer randomizer = new UniformRandomizer(arg);
      return randomizer.getUniformValue();
    }
    else {
      return Integer.parseInt(arg);
    }
  }

  /**
   * Calculates a random number from a Randomizer using a String that it reads
   * @param randomizedString the String prepared to be passed into the randomizer
   * @return the random number
   */
  private int getNumberFromRandomizedString(String randomizedString) {
    if (randomizedString.charAt(0) == 'R') {
      Randomizer randomizer = new Randomizer(randomizedString);
      return randomizer.getRandomValue();
    }
    return Integer.parseInt(randomizedString);
  }

  /**
   *
   * @param firstPoint the value of the starting point
   * @param length the length from the firstPoint that the secondPoint is (note,
   *               this length may be negative)
   * @param maxAllowed
   * @return an array startAndEnd with the following characteristics:
   *         startAndEnd[0] < startAndEnd[1]
   *         startAndEnd[0] >= 0
   *         startAndEnd[1] <= maxAllowed
   */
  private int[] getStartAndEnd(int firstPoint, int length, int maxAllowed) {
    int secondPoint = firstPoint + length;

    int start = Math.min(firstPoint, secondPoint);
    int end = Math.max(firstPoint, secondPoint);

    int checkedStart = Math.max(start, 0);
    int checkedEnd = Math.min(end, maxAllowed);

    int[] startAndEnd = {checkedStart, checkedEnd};
    return startAndEnd;
  }

  /**
   * Builds the width for the generation
   * @param randomizedWidth the String prepared to be passed into the randomizer
   */
  private void buildRowDepth(String randomizedWidth) {
    int rowDepth = getNumberFromRandomizedString(randomizedWidth) - 1;

    if (growsUp) {
      rowDepth *= -1;
    }

    int[] startAndEnd = getStartAndEnd(startRow, rowDepth, numRows - 1);
    startRow = startAndEnd[0];
    endRow = startAndEnd[1];
  }

  /**
   * Builds the width for the genertation
   * @param randomizedHeight the String prepared to be passed into the randomizer
   */
  private void buildColDepth(String randomizedHeight) {
    int colDepth = getNumberFromRandomizedString(randomizedHeight) - 1;

    if (growsLeft) {
      colDepth *= -1;
    }

    int[] startAndEnd = getStartAndEnd(startCol, colDepth, numCols - 1);
    startCol = startAndEnd[0];
    endCol = startAndEnd[1];
  }

  /**
   * Recalculates values for this random instruction
   */
  public void regenerate() {
    buildInstruction(specifications);
  }
}
