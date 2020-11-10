package model.autogenerator;

public abstract class GenerationInstruction {

  protected int numRows;
  protected int numCols;

  protected int startRow;
  protected int startCol;
  protected int endRow;
  protected int endCol;
  protected String entityType;

  private static final String EXCEPTION_MESSAGE = "Failed to build Instruction";


  /**
   * Builds a generation instruction (i.e. if you pass (6, 4) your parent generation might look like
   * 0,0,0,0,0,0
   * 0,0,0,0,0,0
   * 0,0,0,0,0,0
   * 0,0,0,0,0,0
   *
   * @param w the number of blocks wide of the new block that we're building the generation for
   * @param h the number of blocks wide of the new block that we're building the generation for
   */
  public GenerationInstruction(int w, int h) {
    numRows = w;
    numCols = h;
  }

  /**
   * @return the row from which to start drawing the entity
   */
  public int getStartRow() {
    return startRow;
  }

  /**
   * @return the column from which to start drawing the entity
   */
  public int getStartCol() {
    return startCol;
  }

  /**
   * @return the row at which to stop drawing the entity
   */
  public int getEndRow() {
    return endRow;
  }

  /**
   * @return the column at which to stop drawing the entity
   */
  public int getEndCol() {
    return endCol;
  }

  /**
   * @return the String entity to fill in between startx and endx and starty and endy
   */
  public String getEntityTypeToInsert() {
    return entityType;
  }





  /**
   * Checks to make sure that startRow, endRow, startCol and endCol all have valid values.
   * If not, throws an exception
   */
  protected void validate() {
    if (endCol < startCol || endRow < startRow) {
      throwGenerationException();
    }
    else if (endCol > numCols - 1 || endRow > numRows - 1) {
      throwGenerationException();
    }
    else if (startRow < 0 || startCol < 0) {
      throwGenerationException();
    }
  }

  /**
   * Throws an exception to let the user know that the ConstantInstruction failed to build
   */
  protected void throwGenerationException() {
    throw new GenerationException(EXCEPTION_MESSAGE);
  }
}
