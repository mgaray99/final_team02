package model.autogenerator;

import api.model.autogenerator.IConstantGeneration;

public class ConstantGeneration extends GenerationInstruction implements IConstantGeneration {

  public ConstantGeneration(int rows, int cols, String[] args) {
    super(rows, cols);

    try {
      buildInstruction(args);
    }
    catch (Exception e) {
      throwGenerationException();
    }
  }

  /**
   * Builds a constant instruction based on a String array. The array should be specified as follows
   * args[0] = type (i.e. "Constant")
   * args[1] = entity type (i.e. "2" for enemy)
   * args[2] = row specification taking the form RowStart:RowEnd (i.e. 3:5)
   * args[3] = column specification taking the form ColStart:ColEnd (i.e. 9:10)
   * Note: "*" as an argument to args[2] or args[3] means start at beginning or end (i.e. args[2] =
   * "*:9" means "0:9" and args[3] = "3:*" means "3:numCols"
   *
   * @param args the array of arguments
   */
  @Override
  public void buildInstruction(String[] args) {
    entityType = args[1];
    buildXSpecification(args[2]);
    buildYSpecification(args[3]);

    validate();
  }

  /**
   * Sets up startRow and endRow from the xArgs
   * @param xArgs the String containing the row information (i.e. 4:6)
   */
  @Override
  public void buildXSpecification(String xArgs) {
    String[] startEnd = xArgs.split(":");

    buildStartRow(startEnd[0]);
    buildEndRow(startEnd[1]);
  }

  /**
   * Sets up startCol and endCol from the yArgs
   * @param yArgs the String containing the column information (i.e. 3:5)
   */
  @Override
  public void buildYSpecification(String yArgs) {
    String[] startEnd = yArgs.split(":");

    buildStartCol(startEnd[0]);
    buildEndCol(startEnd[1]);
  }

  /**
   * Configures the startRow variable based on rowArg
   * @param rowArg the String containing data on the row
   */
  @Override
  public void buildStartRow(String rowArg) {
    startRow = (rowArg.equals("*")) ? 0 : Integer.parseInt(rowArg);
  }

  /**
   * Configures the endRow variable based on rowArg
   * @param rowArg the String containing data on the row
   */
  @Override
  public void buildEndRow(String rowArg) {
    endRow = (rowArg.equals("*")) ? numRows - 1 : Integer.parseInt(rowArg);

  }

  /**
   * Configures the startCol variable based on colArg
   * @param colArg the String containing data on the row
   */
  @Override
  public void buildStartCol(String colArg) {
    startCol = (colArg.equals("*")) ? 0 : Integer.parseInt(colArg);
  }

  /**
   * Configures the endCol variable based on colArg
   * @param colArg the String containing data on the row
   */
  @Override
  public void buildEndCol(String colArg) {
    endCol = (colArg.equals("*")) ? numCols - 1 : Integer.parseInt(colArg);
  }
}
