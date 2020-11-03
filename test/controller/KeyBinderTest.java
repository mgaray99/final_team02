package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.GameModel;
import model.configuration.GameConfiguration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests that KeyBinder actually changes the binding of the keys in testInputter
 */
public class KeyBinderTest extends DukeApplicationTest {

  private KeyInputter testInputter;

  @Override
  public void start(Stage stage) throws KeyInputBuilderInstantiationException {
    testInputter = new KeyInputter(new GameModel(new GameConfiguration()));
  }
}