package controller;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public abstract class Builder {

  protected final double WIDTH;
  protected final double HEIGHT;
  protected String stateReferenced;
  protected final ResourceBundle resourceBundle;

  private static final String EXTENSION = ".English";
  private static final String RESOURCES = "resources/resourcebundles";


  public Builder(double w, double h) {
    WIDTH = w;
    HEIGHT = h;
    resourceBundle = ResourceBundle.getBundle(RESOURCES + EXTENSION);
  }

  /**
   * Returns the root element of an xml file indexed by xmlPath
   * @param xmlPath the filepath leading to the xml file
   * @return the root element of an xmlPath
   */
  protected Element buildRoot(String xmlPath)
      throws IOException, SAXException, ParserConfigurationException {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document sourceDoc = builder.parse(new File( xmlPath ));
      return sourceDoc.getDocumentElement();
    }

  /**
   * Returns the first instance of a node with tag name tagName in parent
   * @param parent the parent node
   * @param tagName the String tag name
   * @return the first instance of a node with tagName in parent
   */
  protected Element getElementByTagName(Element parent, String tagName) {
    return  (Element)parent.getElementsByTagName(tagName).item(0);
  }

  /**
   * Gets the text from an element
   * @param parent the parent node
   * @param tagName the String tag name
   * @return the text on the element
   */
  protected String getTextFromElement(Element parent, String tagName) {
    return getElementByTagName(parent,tagName).getTextContent();
  }

  /**
   * Returns the state that these button files pertain to (i.e. SELECT_SIMULATION, MENU, etc.) so
   * that the handler knows which scene to place the newly generated buttons into
   *
   * @return stateReferenced the state listed at the top of the file
   */
  public String getStateReferenced() {
    return stateReferenced;
  }
}
