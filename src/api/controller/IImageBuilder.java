package api.controller;

import javafx.scene.image.ImageView;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IImageBuilder extends IBuilder {

    void makeImages(String xmlPath)
            throws IOException, ParserConfigurationException, SAXException;

    ImageView buildImageFromLine(Element imageNode) throws FileNotFoundException;

    String getStateReferenced();

    List<ImageView> getFoundImages();
}
