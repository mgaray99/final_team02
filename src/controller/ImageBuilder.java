package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

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
public class ImageBuilder {

    private final double WIDTH;
    private final double HEIGHT;
    private String stateReferenced;
    private final List<ImageView> foundImages;


    private static final String RESOURCES = "resources/resourcebundles";
    private final ResourceBundle resourceBundle;
    private static final String EXTENSION = ".English";

    /**
     * Instantiates a new ButtonBuilder object
     *
     * @param w    the WIDTH of the area in which buttons can be placed
     * @param h    the HEIGHT of the area in which buttons can be placed
     * @param path the filepath leading to the .txt file containing data on the button
     * @param bph  the handler who will respond when a Button that has been created is pushed
     * @throws ButtonBuilderInstantiationException if failing to build a button
     */
    public ImageBuilder(double w, double h, String path) {
        WIDTH = w;
        HEIGHT = h;
        foundImages = new ArrayList<ImageView>();
        resourceBundle = ResourceBundle.getBundle(RESOURCES + EXTENSION);
        try {
            makeImages(path);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Makes each of the buttons as specified in a particular file
     *
     * @param path the file to be read
     */
    private void makeImages(String path) throws IOException, URISyntaxException {
        Path file = Paths.get(ImageBuilder.class.getClassLoader().getResource(
                path).toURI());
        Scanner fileScan = new Scanner(file);
        stateReferenced = getReferencedStateFromFile(fileScan);

        while (fileScan.hasNextLine()) {
            ImageView builtImage = buildImageFromLine(fileScan.nextLine());
            foundImages.add(builtImage);
        }
    }

    /**
     * Sets up new a button's starting x and y coordinates, its text, its width and height, and its
     * action based on a line in a file which specifies this
     *
     * @param line the line of the file to be parsed for the relevant information
     * @return a fully instantiated Button
     */
    private ImageView buildImageFromLine(String line) throws FileNotFoundException {

        String[] outputComponents = line.split(" ");
        Image image = new Image(new FileInputStream(outputComponents[5]));

        ImageView output = new ImageView(image);

        output.setId(outputComponents[0]);

        output.setFitWidth(WIDTH * Double.parseDouble(outputComponents[3]));
        output.setFitHeight(HEIGHT * Double.parseDouble(outputComponents[4]));

        output.setX(WIDTH * Double.parseDouble(outputComponents[1]) -
                output.getFitWidth() / 2);
        output.setY(HEIGHT * Double.parseDouble(outputComponents[2]) -
                output.getFitHeight() / 2);

        return output;
    }




    /**
     * Determines the state associated with the relevant file when first parsing through the file.
     * This will be the first line of the file and will be something like MENU or PAUSE and will tell
     * the program which scene that its contents should be placed into
     *
     * @param scan the Scanner with the data in it
     * @return a String containing the relevant state
     */
    private String getReferencedStateFromFile(Scanner scan) {
        if (scan.hasNextLine()) {
            return scan.nextLine();
        }
        return "";
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

    /**
     * Returns the list of buttons that this ButtonBuilder has found from parsing the file
     *
     * @return a list of buttons
     */
    public List<ImageView> getFoundImages() {
        return foundImages;
    }
}

