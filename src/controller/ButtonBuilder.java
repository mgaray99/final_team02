package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Is responsible for creating Buttons from a config file that takes the form XXXX.txt. The first
 * line of this file should be an ID of some kind (i.e. MAIN_SCENE) and the other lines should run
 * as BUTTON_ID XCOORD YCOORD BUTTON_WIDTH BUTTON_HEIGHT.
 * <p>
 * (i.e. PushCommand 0.5 0.5 0.4 0.2 sets the x coordinate at location 0.5 * WIDTH, y coordinate 0.5
 * * HEIGHT, button width at 0.4 * WIDTH and button height at 0.2 * HEIGHT)
 * <p>
 * This builder sets the action for the push of a button to handler.handlePush(BUTTON_ID) for all
 * buttons so that the handler knows that the button with BUTTON_ID was pushed
 *
 * @author Alex Lu
 */
public class ButtonBuilder extends Builder {

  private final List<Button> foundButtons;
  private final ButtonPushHandler handler;

  private static final String BUTTON = "button";
  private static final String ID = "id";
  private static final String CENTERX = "centerx";
  private static final String CENTERY = "centery";
  private static final String BUTTON_WIDTH = "width";
  private static final String BUTTON_HEIGHT = "height";
  private static final String METHOD = "method";
  private static final String TITLE = "title";

  /**
   * Instantiates a new ButtonBuilder object
   *
   * @param w    the WIDTH of the area in which buttons can be placed
   * @param h    the HEIGHT of the area in which buttons can be placed
   * @param path the filepath leading to the .txt file containing data on the button
   * @param bph  the handler who will respond when a Button that has been created is pushed
   * @throws ButtonBuilderInstantiationException if failing to build a button
   */
  public ButtonBuilder(double w, double h, String path, ButtonPushHandler bph)
      throws ButtonBuilderInstantiationException {

    super(w,h);
    foundButtons = new ArrayList<>();
    handler = bph;

    try {
      makeButtons(path);
    }
    catch (Exception e) {
      throw new ButtonBuilderInstantiationException("Couldn't load button file indexed by "
          + path);
    }
  }

  /**
   * Makes each of the buttons as specified in a particular file
   *
   * @param xmlPath the file to be read
   */
  private void makeButtons(String xmlPath)
      throws IOException, ParserConfigurationException, SAXException {
    Element root = buildRoot(xmlPath);

    NodeList buttons = root.getElementsByTagName(BUTTON);
    stateReferenced = getTextFromElement(root, TITLE);

    for (int index = 0; index <  buttons.getLength(); index += 1) {
      Element buttonNode = (Element)buttons.item(index);
      Button builtButton = buildButtonFromLine(buttonNode);
      foundButtons.add(builtButton);
    }
  }

  /**
   * Sets up new a button's starting x and y coordinates, its text, its width and height, and its
   * action based on a line in a file which specifies this
   *
   * @param buttonNode the node of the file to be parsed for the relevant information
   * @return a fully instantiated Button
   */
  private Button buildButtonFromLine(Element buttonNode) {

    Button output = new Button();
    String text = getTextFromElement(buttonNode, ID);

    output.setId(text);
    output.setText(resourceBundle.getString(text));

    output.setPrefWidth(WIDTH * Double.parseDouble(getTextFromElement(buttonNode, BUTTON_WIDTH)));
    output.setPrefHeight(HEIGHT * Double.parseDouble(getTextFromElement(buttonNode, BUTTON_HEIGHT)));

    output.setLayoutX(WIDTH * Double.parseDouble(getTextFromElement(buttonNode, CENTERX)) -
        output.getPrefWidth() / 2);
    output.setLayoutY(HEIGHT * Double.parseDouble(getTextFromElement(buttonNode, CENTERY)) -
        output.getPrefHeight() / 2);

    output.setOnAction(e -> handler.handlePush(getTextFromElement(buttonNode, METHOD)));
    return output;
  }

  /**
   * Returns the list of buttons that this ButtonBuilder has found from parsing the file
   *
   * @return a list of buttons
   */
  public List<Button> getFoundButtons() {
    return foundButtons;
  }
}
