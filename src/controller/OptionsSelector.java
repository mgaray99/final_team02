package ooga.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Works as a UI - friendly ComboBox of sorts - takes in a list of Strings as presents an interface
 * where the user can see three choices as well as options to move backwards or forwards in the list
 * (with list wrapping available). In the event that the user clicks on any of the three choices,
 * the OptionsSelector will set the text in its buffer to be the text of the button that the user
 * pressed and then will dispatch an event.
 *
 * @author Alex Lu
 */
public class OptionsSelector extends Group implements ButtonPushHandler {

  private static final String PREV_COMMAND = "PrevCommand";
  private static final String NEXT_COMMAND = "NextCommand";
  private static final String OPTION1 = "Option1";
  private static final String OPTION2 = "Option2";
  private static final String OPTION3 = "Option3";
  private static final String TITLE = "SELECT_OPTIONS";
  private static final EventType<Event> EVENT_TYPE = new EventType<>("push");

  private static final int NUM_OPTION_BUTTONS = 3;
  private static final String RESOURCES = "resources/";
  private static final String OPTIONS_SELECTOR_CONFIG_PATH = RESOURCES
      + "buttons/optionsselectorbuttons.txt";

  private ResourceBundle resourceBundle;
  private static final String EXTENSION = ".English";
  private static final String LABELS = ".resourcebundles";
  private int choosableOptionOffset;
  private String bufferText;

  private final List<String> choices;
  private final double WIDTH;
  private final double HEIGHT;

  /**
   * Instantiates an OptionsSelector
   *
   * @param w the width of the visible pane that the OptionsSelector will fill
   * @param h the height of the visible pane that the OptionsSelector will fill
   * @param c the list of choices that will fill the OptionsSelector
   */
  public OptionsSelector(double w, double h, List<String> c) {
    WIDTH = w;
    HEIGHT = h;
    choosableOptionOffset = 0;
    bufferText = "";

    choices = new ArrayList<>();
    copyIntoChoices(c);

    resourceBundle = ResourceBundle.getBundle(RESOURCES + LABELS + EXTENSION);

    buildButtons();
    regenerateOptions();
  }

  /**
   * Copies the contents of list c into choices (avoids aliasing issues)
   *
   * @param c the list of things to be copied in
   */
  private void copyIntoChoices(List<String> c) {
    choices.addAll(c);
  }

  /**
   * Creates the buttons associated with this OptionsSelector using a ButtonBuilder reading from a
   * config file as specified by OPTIONS_SELECTOR_CONFIG_PATH
   */
  private void buildButtons() {
    try {
      ButtonBuilder builder = new ButtonBuilder(WIDTH, HEIGHT,
          OPTIONS_SELECTOR_CONFIG_PATH, this);
      if (builder.getStateReferenced().equals(TITLE)) {
        super.getChildren().addAll(builder.getFoundButtons());
      }
    } catch (ButtonBuilderInstantiationException bbie) {
      bbie.printStackTrace();
    }
  }

  /**
   * Is responsible for handling the situation where the user pushes a button - directs the query to
   * a different method depending on which button was pushed
   *
   * @param type the button which was pushed
   */

  public void handlePush(String type) {
    switch (type) {
      case PREV_COMMAND -> prevOptions();
      case NEXT_COMMAND -> nextOptions();
      case OPTION1 -> chooseTopChoice();
      case OPTION2 -> chooseMiddleChoice();
      case OPTION3 -> chooseBottomChoice();
    }
  }

  /**
   * Displays the 3 previous options
   */
  private void prevOptions() {
    choosableOptionOffset -= 1;
    regenerateOptions();
  }

  /**
   * Displays the 3 next options
   */
  private void nextOptions() {
    choosableOptionOffset += 1;
    regenerateOptions();
  }

  /**
   * Regenerates the text on the buttons that select the simulation so that they reflect the current
   * options that the user is choosing from
   */
  public void regenerateOptions() {
    int offset = determineSelectionOffset(choices);

    setOptionText(choices, (Button) lookup("#" + OPTION1), offset);
    setOptionText(choices, (Button) lookup("#" + OPTION2), offset + 1);
    setOptionText(choices, (Button) lookup("#" + OPTION3), offset + 2);


  }

  /**
   * Removes an option from the option selector
   *
   * @param option the option to be removed
   */
  public void removeOption(String option) {
    choices.remove(option);
    regenerateOptions();

  }

  /**
   * Adds an option to the option selector
   *
   * @param option the option to be removed
   */
  public void addOption(String option) {
    choices.add(option);
    regenerateOptions();
  }

  /**
   * Determines which page of the options you are currently looking at
   *
   * @return the starting point in the list to generate file names for simulations
   */
  private int determineSelectionOffset(List<String> options) {
    int offset = choosableOptionOffset * NUM_OPTION_BUTTONS;
    if (offset / NUM_OPTION_BUTTONS >
        options.size() / NUM_OPTION_BUTTONS) {
      choosableOptionOffset = 0;
      return 0;
    } else if (offset < 0) {
      choosableOptionOffset = Math.max((options.size() - 1)
          / NUM_OPTION_BUTTONS, 0);
      return choosableOptionOffset * NUM_OPTION_BUTTONS;
    }
    return offset;
  }

  /**
   * Sets the text on the button referenced by button id (i.e. Simulation1, Simulation2 or
   * Simulation3 to the choice at index in choices or "" if that index is out of bounds
   *
   * @param index the index of the option in the list of choices
   */
  private void setOptionText(List<String> choices, Button relevantButton, int index) {
    try {
      relevantButton.setText(choices.get(index));
    } catch (IndexOutOfBoundsException ioobe) {
      relevantButton.setText("");
    }
  }

  /**
   * Sets the buffer text in this OptionsSelector to the text on the top option button
   */
  private void chooseTopChoice() {
    bufferText = ((Button) lookup("#" + OPTION1)).getText();
    dispatchEvent();
  }


  /**
   * Sets the buffer text in this OptionsSelector to the text on the middle option button
   */
  private void chooseMiddleChoice() {
    bufferText = ((Button) lookup("#" + OPTION2)).getText();
    dispatchEvent();
  }

  /**
   * Sets the buffer text in this OptionsSelector to the text on the bottom option button
   */
  private void chooseBottomChoice() {
    bufferText = ((Button) lookup("#" + OPTION3)).getText();
    dispatchEvent();
  }

  /**
   * Creates an event in order to let an ActionListener know that something relevant has happened
   * within the OptionSelector
   */
  private void dispatchEvent() {
    fireEvent(new Event(EVENT_TYPE));
  }

  /**
   * Returns the relevant text in the buffer - this will let the user know what this OptionsSelector
   * has currently selected. Then, set the buffer text to "" because it's already been returned
   *
   * @return bufferText
   */
  public String getTextInBuffer() {
    String tempBuffer = bufferText;
    bufferText = "";
    return tempBuffer;
  }

  /**
   * Updates the resource bundle associated with this particular OptionsSelector and then sets the
   * text accordingly
   *
   * @param path the String path leading to the properties file
   */
  public void updateBundle(String path) {
    resourceBundle = ResourceBundle.getBundle(RESOURCES + LABELS + path);
    updateAllButtonBundles();
    regenerateOptions();
  }

  /**
   * Updates the text on all buttons to reflect the text in the resource bundle
   */
  private void updateAllButtonBundles() {
    for (Node n : super.getChildren()) {
      if (n.getClass().getSimpleName().equals("Button")) {
        ((Button) n).setText(resourceBundle.getString(n.getId()));
      }
    }
  }
}