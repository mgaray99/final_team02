package api.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import api.model.entity.IEntity;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public interface ITexturer {
    List<ImageView> buildViewList(String propertiesPath);

    TreeMap buildPropertiesMap(String propertiesPath) throws IOException,
            NullPointerException;

    ImageView buildImageView(String id, String filepath);

    void constructTextureMap(List<ImageView> viewList);

    void updateTextures(List<IEntity> entityList, double blocksWide, double blocksHigh);

    void clearCurrentTextures();

    void insertNewTextures(List<IEntity> entityList);

    void addNewTexture(IEntity currentEntity);

    void placeLocationOfView(IEntity currentEntity, ImageView view);

    Image buildMissingImage(double width, double height);

    String getPath();
}
