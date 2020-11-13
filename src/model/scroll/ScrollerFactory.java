package model.scroll;

import model.autogenerator.GenerationException;

public class ScrollerFactory {

  private static final String SCROLLER = "Scroller";
  private static final String MANUAL = "Manual";
  private static final String AUTO = "Auto";
  private static final String DOODLE = "Doodle";
  private static final String AUTO_GENERATION = "AutoGeneration";

  /**
   * Builds a scroller from a set of arguments
   * @param args the set of arguments
   * @param generatorPath the String filepath leading to a file to be used for
   *                      automatic level generation
   * @return a new scroller
   */
  public Scroller buildScroller(String[] args, String generatorPath) {
    String identifier = args[0];
    String [] constructorArgs = buildConstructorArgs(args, generatorPath);

    try {
      Scroller scroller = buildScrollerFromArgs(identifier, constructorArgs);
      return scroller;
    }
    catch (IndexOutOfBoundsException | NumberFormatException | GenerationException ex) {
      ex.printStackTrace();
      System.out.println("No scrolling allowed for this level - bad configuration");
      return new AutoScroller(0,0, false);
    }
  }

  /**
   * Builds and returns a Scroller from identifier and constructorArgs
   *
   * @param identifier a String representing the type of Scroller to build
   * @param constructorArgs the arguments to be passed into its constructor
   * @return a Scroller built from identifier and constructorArgs
   */
  private Scroller buildScrollerFromArgs(String identifier, String[] constructorArgs)
  throws GenerationException {
    switch (identifier) {
      case MANUAL: return buildManualScroller(constructorArgs);
      case AUTO: return buildAutoScroller(constructorArgs);
      case DOODLE: return buildDoodleGenerationScroller(constructorArgs);
      case AUTO_GENERATION: return buildAutoGenerationScroller(constructorArgs);
      default: return new AutoScroller(0,0, false);
    }
  }

  /**
   * Returns the arguments to be passed to the Scroller constructor (i.e. take param args and strip
   * it of its identifier at index 0 and then add generatorFilepath to the end)
   * @param args the array containing the arguments passed to buildScroller
   * @param generatorFilePath the String filepath leading to a file to be used for automatic level
   *                          generation
   * @return a new array containing all elements of args besides args[0] and with the newly appended
   *          generatorFilePath thrown in
   */
  private String[] buildConstructorArgs(String[] args, String generatorFilePath) {
    String[] constructorArgs = new String[args.length];

    for (int index = 0; index < args.length - 1; index +=1) {
      constructorArgs[index] = args[index + 1];
    }
    constructorArgs[args.length - 1] = generatorFilePath;

    return constructorArgs;
  }

  /**
   * Builds and returns a new ManualScroller from args
   * @param args the arguments used to specify the ManualScroller
   * @return a new ManualScroller
   */
  private Scroller buildManualScroller(String[] args) {
    return new ManualScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
        Double.parseDouble(args[2]), Double.parseDouble(args[3]));
  }

  /**
   * Builds and returns a new AutoScroller from args
   * @param args the arguments used to specify the AutoScroller
   * @return a new AutoScroller
   */
  private Scroller buildAutoScroller(String[] args) {
      return new AutoScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
          Boolean.parseBoolean(args[2]));
  }

  /**
   * Builds and returns a new DoodleGenerationScroller from args
   * @param args the arguments used to specify the DoodleGenerationScroller
   * @return a new DoodleGenerationScroller
   */
  private Scroller buildDoodleGenerationScroller(String[] args) throws GenerationException {
    return new DoodleGenerationScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
        Double.parseDouble(args[2]), Double.parseDouble(args[3]), args[4]);
  }

  /**
   * Builds and returns a new AutoGenerationScroller from args
   * @param args the arguments used to specify the AutoGenerationScroller
   * @return a new AutoGenerationScroller
   */
  private Scroller buildAutoGenerationScroller(String[] args) throws GenerationException {
    return new AutoGenerationScroller(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
        Boolean.parseBoolean(args[2]), args[3]);
  }
}
