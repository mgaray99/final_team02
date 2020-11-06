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


    public AutoGenerator(int width, int height, String path) {
      NUM_WIDE_BLOCKS = width;
      NUM_HIGH_BLOCKS = height;
      CONFIG_FILE = path;
      setUpGenerator(path);
    }

    private void setUpGenerator(String path) {
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

    private void

    public String[][] generateLevelBlock() {
      currentNewBlock = new String[NUM_WIDE_BLOCKS][NUM_HIGH_BLOCKS];
    }
}
