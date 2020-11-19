package api.controller;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface IBuilder {
    Element buildRoot(String xmlPath)
        throws IOException, SAXException, ParserConfigurationException;

    Element getElementByTagName(Element parent, String tagName);

    String getTextFromElement(Element parent, String tagName);

    String getStateReferenced();
}
