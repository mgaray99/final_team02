package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


public class PairBuilderTest extends DukeApplicationTest {

  private static final String DEFAULT_KEY_INPUT_PATH = "resources/keyinputs/mariokeyinputs.txt";
  private PairBuilder builder;
  private List<Pair<String, String>> pairs;

  @Override
  public void start(Stage stage) throws PairBuilderInstantiationException {
    builder = new PairBuilder(DEFAULT_KEY_INPUT_PATH);
    pairs = new ArrayList<>();
    pairs.addAll(builder.getFoundPairs());
  }

  /**
   * Tests that the PairBuilder builds a List<Pair<String, String>> of key inputs to String
   * methods
   */
  @Test
  public void testSimpleBuild() throws PairBuilderInstantiationException {
    assertEquals(5, pairs.size());
    String[] keys = {"A", "S", "D", "W", "P"};
    List<String> keysList = Arrays.asList(keys);

    for (Pair<String, String> pair : pairs) {
      assertTrue(keysList.contains(pair.getKey()));
      switch (pair.getKey()) {
        case "A" -> assertEquals("left", pair.getValue());
        case "S" -> assertEquals("down", pair.getValue());
        case "D" -> assertEquals("right", pair.getValue());
        case "W" -> assertEquals("up", pair.getValue());
        case "P" -> assertEquals("pause", pair.getValue());
      }
    }
  }

  /**
   * Tests the situation where the user inputs a file path that does not point to a valid key input
   * file
   * @throws PairBuilderInstantiationException
   */
  @Test
  public void testBadFile() throws PairBuilderInstantiationException {
    assertDoesNotThrow(() -> new PairBuilder(DEFAULT_KEY_INPUT_PATH));
    assertThrows(PairBuilderInstantiationException.class,
        () -> new PairBuilder("hi"));
  }
}