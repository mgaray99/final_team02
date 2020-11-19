package controller;

import api.controller.IImageBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class ImageBuilder extends Builder implements IImageBuilder {

    private String stateReferenced;
    private final List<ImageView> foundImages;


    private static final String IMAGE = "image";
    private static final String TITLE = "title";

    private static final String ID = "id";
    private static final String CENTERX = "centerx";
    private static final String CENTERY = "centery";
    private static final String IMAGE_WIDTH = "width";
    private static final String IMAGE_HEIGHT = "height";
    private static final String PATH = "path";

    /**
     * Instantiates a new ButtonBuilder object
     *
     * @param w    the WIDTH of the area in which buttons can be placed
     * @param h    the HEIGHT of the area in which buttons can be placed
     * @param path the filepath leading to the .txt file containing data on the button
     */
    public ImageBuilder(double w, double h, String path) {
        super(w,h);
        foundImages = new ArrayList<>();
        try {
            makeImages(path);
        } catch (Exception e) {
            System.out.println("Couldn't build images at " + path);

        }
    }


    /**
     * Makes each of the buttons as specified in a particular file
     *
     * @param xmlPath the file to be read
     */
    @Override
    public void makeImages(String xmlPath)
            throws IOException, ParserConfigurationException, SAXException {
        Element root = buildRoot(xmlPath);

        NodeList imageviews = root.getElementsByTagName(ImageBuilder.IMAGE);
        stateReferenced = getTextFromElement(root, ImageBuilder.TITLE);

        for (int index = 0; index < imageviews.getLength(); index += 1) {
            Element imageNode = (Element) imageviews.item(index);
            ImageView builtImage = buildImageFromLine(imageNode);
            foundImages.add(builtImage);
        }
    }

    /**
     * Sets up new a button's starting x and y coordinates, its text, its width and height, and its
     * action based on a line in a file which specifies this
     *
     * @param imageNode the line of the file to be parsed for the relevant information
     * @return a fully instantiated Button
     */
    @Override
    public ImageView buildImageFromLine(Element imageNode) throws FileNotFoundException {

        Image image = new Image(new FileInputStream(
                getTextFromElement(imageNode, ImageBuilder.PATH)));

        ImageView output = new ImageView(image);
        output.setId(getTextFromElement(imageNode, ImageBuilder.ID));

        output.setFitWidth(WIDTH * Double.parseDouble(
                getTextFromElement(imageNode, ImageBuilder.IMAGE_WIDTH)));
        output.setFitHeight(HEIGHT * Double.parseDouble(
                getTextFromElement(imageNode, ImageBuilder.IMAGE_HEIGHT)));

        output.setX(WIDTH * Double.parseDouble(getTextFromElement(imageNode, ImageBuilder.CENTERX)) -
                output.getFitWidth() / 2);
        output.setY(HEIGHT * Double.parseDouble(getTextFromElement(imageNode, ImageBuilder.CENTERY)) -
                output.getFitHeight() / 2);

        return output;
    }

    /**
     * Returns the state that these button files pertain to (i.e. SELECT_SIMULATION, MENU, etc.) so
     * that the handler knows which scene to place the newly generated buttons into
     *
     * @return stateReferenced the state listed at the top of the file
     */
    @Override
    public String getStateReferenced() {
        return stateReferenced;
    }

    /**
     * Returns the list of buttons that this ButtonBuilder has found from parsing the file
     *
     * @return a list of buttons
     */
    @Override
    public List<ImageView> getFoundImages() {
        return foundImages;
    }
}

