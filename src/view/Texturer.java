package view;

import controller.ImageBuilder;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.GameModel;
import model.entity.Entity;

public class Texturer {
  private Map<String, ImageView> textureMap;
  private Group textureGroup;
  private final double WIDTH;
  private final double HEIGHT;
  private static final double VISIBLE_AMOUNT = 100;
  private final double XSTRETCH_FACTOR;
  private final double YSTRETCH_FACTOR;
  private double numBlocksWide;
  private double numBlocksHigh;
  private static final String TEXTURES = "textures";

  /**
   *
   * @param w the WIDTH of the screen
   * @param h the HEIGHT of the screen
   * @param path the filepath leading to the file containing the texture data that ImageBuilder
   *             will use to create the ImageViews
   * @param tGroup the Group that will contain the textures
   */
  public Texturer(double w, double h, String path, Group tGroup) {
    WIDTH = w;
    HEIGHT = h;
    XSTRETCH_FACTOR = WIDTH/VISIBLE_AMOUNT;
    YSTRETCH_FACTOR = WIDTH/VISIBLE_AMOUNT;
    textureGroup = tGroup;

    ImageBuilder builder = new ImageBuilder(WIDTH, HEIGHT, path);
    constructTextureMap(builder.getFoundImages());
  }

  /**
   * Builds the map String ids -> ImageViews
   * @param viewList the List<ImageView> to build the map on top of
   */
  private void constructTextureMap(List<ImageView> viewList) {
    textureMap = new HashMap<>();
    viewList.forEach(view -> textureMap.put(view.getId(), view));
  }

  /**
   * Updates the textures of a relevantScene
   * @param entityList the list of Entities to be textured
   */
  public void updateTextures(List<Entity> entityList) {
    clearCurrentTextures();
    insertNewTextures(entityList);
  }

  /**
   * Clears the textures out from texturedScene
   */
  private void clearCurrentTextures() {
    textureGroup.getChildren().clear();
  }

  /**
   * Inserts the new textures into texturedScene
   */
  private void insertNewTextures(List<Entity> entityList) {
    entityList.forEach(entity -> addNewTexture(entity));
  }

  /**
   * Adds a single new texture to the group
   * @param currentEntity
   */
  private void addNewTexture(Entity currentEntity) {
    Image image = textureMap.get(currentEntity.getTypeId()).getImage();
    ImageView view = new ImageView(image);


    if (view!=null){
      placeLocationOfView(currentEntity, view);
      textureGroup.getChildren().add(view);
    }
  }

  private void placeLocationOfView(Entity currentEntity, ImageView view) {
    Rectangle2D.Float flo = currentEntity.getHitBox();

    view.setX(flo.x * flo.width * XSTRETCH_FACTOR);
    view.setY(flo.y * flo.height * YSTRETCH_FACTOR);
    view.setFitWidth(flo.width * XSTRETCH_FACTOR);
    view.setFitHeight(flo.height * YSTRETCH_FACTOR);
  }
}
