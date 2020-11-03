package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.util.Pair;

/**
 * Builds a set of pairs <String, String> (i.e. <"D", "down">) that map keys to their methods from a
 * file and stores these pairs in foundPairs to be queried when the user wishes
 *
 * @author Alex Lu
 */
public class PairBuilder {

  private List<Pair<String, String>> foundPairs;
  private String path;
  private static final String PAIR_IDENTIFIER = "PAIRS";

  public PairBuilder(String filePath) throws PairBuilderInstantiationException {
    try {
      path = filePath;
      foundPairs = new ArrayList<>();
      setUpBuildPairings();
    } catch (Exception e) {
      e.printStackTrace();
      throw new PairBuilderInstantiationException("Unable to build pair inputs indexed by "
        + path);
    }
  }

  /**
   * Checks to make sure that we are dealing with the correct type of file (i.e. a KeyInput file
   * that has KEY_INPUTS as its first line
   *
   * @throws URISyntaxException
   * @throws IOException
   */
  private void setUpBuildPairings() throws URISyntaxException, IOException {
    Path file = Paths.get(ButtonBuilder.class.getClassLoader().getResource(path).toURI());
    Scanner fileScan = new Scanner(file);
    String identifier = getNextLine(fileScan);

    if (identifier.equals(PAIR_IDENTIFIER)) {
      buildPairInputs(fileScan);
    }
    else {
      throw new IOException("should bubble up to a catch statement to throw a different exception");
    }
  }

  /**
   * Builds the pairs from the file mapping key->value and stores them in foundPairs
   */
  private void buildPairInputs(Scanner fileScan) {
    while (fileScan.hasNextLine()) {
      Pair<String, String> pair = buildKeyPairFromLine(fileScan.nextLine());
      foundPairs.add(pair);
    }
  }

  /**
   * Builds the <key, value> pair from a given line in the .txt file
   * @param line the line in the .txt file to be parsed
   */
  private Pair<String, String> buildKeyPairFromLine(String line) {
    String[] pairComponents = line.split(" ");
    Pair<String, String> pair = new Pair<>(pairComponents[0], pairComponents[1]);
    return pair;
  }

  /**
   * @param fileScan the Scanner from which to receive the next line
   * @return the next line, if it exists, from fileScan
   */
  private String getNextLine(Scanner fileScan) {
    if (fileScan.hasNextLine()) {
      return fileScan.nextLine();
    }
    return "";
  }

  /**
   * Returns the list of <"Key", "Method"> <String, String> pairs found by this KeyInputBuilder
   *
   * @return foundPairs as a defensively copied List, defensiveKeyPairs
   */
  public List<Pair<String, String>> getFoundPairs() {
    List<Pair<String, String>> defensiveFoundPairs = new ArrayList<>();
    defensiveFoundPairs.addAll(foundPairs);
    return defensiveFoundPairs;
  }

  /**
   * Turns the pairs found into a map from the key of each pair to the value associated with that
   * key in the pair
   * @return foundPairs as a Map from key -> value
   */
  public Map<String, String> getFoundMap() {
    Map<String, String> keyToValueMap = new HashMap<>();
    foundPairs.forEach(pair -> keyToValueMap.put(pair.getKey(), pair.getValue()));
    return keyToValueMap;
  }

}
