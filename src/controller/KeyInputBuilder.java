package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;

/**
 * Builds a set of pairs <String, String> (i.e. <"D", "down">) that map keys to their methods from a
 * file and stores these pairs in keyPairs to be queried when the user wishes
 *
 * @author Alex Lu
 */
public class KeyInputBuilder {

  private List<Pair<String, String>> keyPairs;
  private String path;
  private static final String KEY_INPUT_IDENTIFIER = "KEY_INPUTS";

  public KeyInputBuilder(String filePath) throws KeyInputBuilderInstantiationException {
    try {
      path = filePath;
      keyPairs = new ArrayList<>();
      setUpBuildKeyInputs();
    } catch (Exception e) {
      throw new KeyInputBuilderInstantiationException("Unable to build key inputs indexed by "
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
  private void setUpBuildKeyInputs() throws URISyntaxException, IOException {
    Path file = Paths.get(ButtonBuilder.class.getClassLoader().getResource(path).toURI());
    Scanner fileScan = new Scanner(file);
    String identifier = getNextLine(fileScan);

    if (identifier.equals(KEY_INPUT_IDENTIFIER)) {
      buildKeyInputs(fileScan);
    }
    else {
      throw new IOException("should bubble up to a catch statement to throw a different exception");
    }
  }

  /**
   * Builds the KeyInput pairs from the file mapping key->method and stores them in keyPairs
   */
  private void buildKeyInputs(Scanner fileScan) {
    while (fileScan.hasNextLine()) {
      Pair<String, String> pair = buildKeyPairFromLine(fileScan.nextLine());
      keyPairs.add(pair);
    }
  }

  /**
   * Builds the <key, method> pair from a given line in the .txt file
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
   * @return keyPairs as a defensively copied List, defensiveKeyPairs
   */
  public List<Pair<String, String>> getKeyPairs() {
    List<Pair<String, String>> defensiveKeyPairs = new ArrayList<>();
    defensiveKeyPairs.addAll(keyPairs);
    return defensiveKeyPairs;
  }

}
