package model;

import controller.ImageBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class AutoGenerator {
    private final int NUM_WIDE_BLOCKS;
    private final int NUM_HIGH_BLOCKS;
    private final String CONFIG_FILE;
    private String[][] currentNewBlock;
    private String defaultValue;


    public AutoGenerator(int width, int height, String path) {
      NUM_WIDE_BLOCKS = width;
      NUM_HIGH_BLOCKS = height;
      CONFIG_FILE = path;
      setUpGenerator();
    }

  /**
   * Builds the generator from the config file at path
   */
  private void setUpGenerator() {
      try {
        Path file = Paths.get(ImageBuilder.class.getClassLoader().getResource(
            CONFIG_FILE).toURI());
        Scanner scan = new Scanner(file);
        buildGenerator(scan);
      }
      catch (Exception e) {
        System.out.println("File not found");
      }
    }

  /**
   * Scans through the config file to find out what is necessary to know to generate new portions
   * of the level
   * @param scan a Scanner containing the config file
   */
  private void buildGenerator(Scanner scan) {
      determineDefaultValue(scan);
  }

  private void determineDefaultValue(Scanner scan) {
    if (scan.hasNextLine())  {
      String[] firstLine = scan.nextLine().split(" ");
      defaultValue = firstLine[1];
    }
  }

    public String[][] generateLevelBlock() {
      currentNewBlock = new String[NUM_WIDE_BLOCKS][NUM_HIGH_BLOCKS];
      fillInDefaultValues();
      return currentNewBlock;
    }

  /**
   * Initialize every entityType in the new level block to the default entityType found in CONFIG_PATH
   */
  private void fillInDefaultValues() {
      for (int row = 0; row < currentNewBlock.length; row+=1) {
        for (int column = 0; column < currentNewBlock[0].length; column+=1) {
          currentNewBlock[row][column] = defaultValue;
        }
      }
    }
}
