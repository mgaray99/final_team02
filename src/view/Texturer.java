package view;

import controller.ImageBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import model.GameModel;
import model.entity.Entity;

public class Texturer {
  private Map<String, ImageView> textureMap;
  private Group textureGroup;
  private final double WIDTH;
  private final double HEIGHT;
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
    ImageView view = textureMap.get(currentEntity.getId());
    view.setX(currentEntity.get)
    if (view!=null) {
      textureGroup.getChildren().add(view);
    }
  }
}
