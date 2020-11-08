package model.autogenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutoGenerator {

  private static final String SET = "set";
  private static final String AUTO_GENERATION_FAILED = "Automatic level generation failed";
  private int numBlocksWide;
  private int numBlocksHigh;
  private String defaultValue;
  private String[][] newBlock;

  private List<ConstantGeneration> constantSpecifications;
  private List<RandomGeneration> randomSpecifications;

  public AutoGenerator(String path) {
    try {
      Path file = Paths.get(AutoGenerator.class.getClassLoader().getResource(
          path).toURI());
      Scanner fileScan = new Scanner(file);
      buildSpecification(fileScan);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new GenerationException(AUTO_GENERATION_FAILED);
    }
  }

  /**
   * Builds the specification lists that the AutoGenerator uses to generate levels
   * @param fileScan the Scanner object containing the contents the AutoGenerator configuration file
   */
  private void buildSpecification(Scanner fileScan)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    constantSpecifications = new ArrayList<>();
    randomSpecifications = new ArrayList<>();

    while(fileScan.hasNextLine()) {
      String line = fileScan.nextLine();
      String[] lineParts = line.split(" ");

      String methodPiece = lineParts[0];
      Method method = getClass().getDeclaredMethod(SET + methodPiece, String[].class);
      method.invoke(this, (Object)lineParts);
    }
  }

  /**
   * Updates the specification of the AutoGenerator such that its default entityType equals something in
   * the lineParts array
   */
  private void setDimensions(String[] lineParts) {
    numBlocksWide = Integer.parseInt(lineParts[1]);
    numBlocksHigh = Integer.parseInt(lineParts[2]);

  }

  /**
   * Updates the specification of the AutoGenerator such that its default entityType equals something in
   * the lineParts array
   */
  private void setDefault(String[] lineParts) {
    defaultValue = lineParts[1];
  }

  /**
   * Creates a ConstantInstruction out of the String lineParts
   * @param lineParts the array of arguments to be passed to the RandomInstruction constructor
   */
  private void setConstant(String[] lineParts) {
    ConstantGeneration instruction = new ConstantGeneration(
        numBlocksWide, numBlocksHigh, lineParts);
    constantSpecifications.add(instruction);
  }

  /**
   * Creates an RandomInstruction out of the String lineParts
   * @param lineParts the array of arguments to be passed to the RandomInstruction constructor
   */
  private void setRandom(String[] lineParts) {
    RandomGeneration instruction = new RandomGeneration(
        numBlocksWide, numBlocksHigh, lineParts);
    randomSpecifications.add(instruction);
  }

  /**
   * Builds the 2D String array that represents the entities who fill the next block
   * @return the 2D String array of new entity representations
   */
  public String[][] generateNextBlock() {
    newBlock = new String[numBlocksWide][numBlocksHigh];
    fillInDefaultValues();
    executeAllSpecifications();
    return newBlock;
  }

  /**
   * Fills the new block with default entity entityType (i.e. what will be present at a location in the
   * game matrix if there aren't any other entity values to overwrite it)
   */
  private void fillInDefaultValues() {
    for (int row = 0; row < numBlocksWide; row+=1) {
      for (int column = 0; column < numBlocksHigh; column+=1) {
        newBlock[row][column] = defaultValue;
      }
    }
  }

  /**
   * Reads each of the GenerationInstruction objects in the specifications list and then calls
   * executeSpecification on that
   */
  private void executeAllSpecifications() {
    constantSpecifications.forEach(genInst -> executeSpecification(genInst));

    randomSpecifications.forEach(genInst -> genInst.regenerate());
    randomSpecifications.forEach(genInst -> executeSpecification(genInst));
  }

  /**
   * Applies a single GenerationInstruction to the 2D array newBlock
   * @param spec the GenerationInstruction to apply to the newBlock
   */
  private void executeSpecification(GenerationInstruction spec) {
    for (int row = spec.getStartRow(); row <= spec.getEndRow(); row+=1) {
      for (int column = spec.getStartCol(); column <=spec.getEndCol(); column+=1) {
        newBlock[row][column] = spec.getEntityTypeToInsert();
      }
    }
  }
}
