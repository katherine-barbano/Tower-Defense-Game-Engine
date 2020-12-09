package view.GameView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

/**
 * Unused class -- should have been deleted.
 */
public class ImageCreation extends HashMap<String, Image> {

  public static final String IMAGE_RESOURCES_PATH = "data/images/";

  public ImageCreation(ResourceBundle images) throws IOException {
    Enumeration<String> imageKeys = images.getKeys();
    while (imageKeys.hasMoreElements()) {
      String key = imageKeys.nextElement();
      String fileName = images.getString(key).replace("\"", "");
      String path = IMAGE_RESOURCES_PATH + fileName;
      InputStream inputstream = new FileInputStream(path);
      Image image = new Image(inputstream);
      this.put(key, image);
    }
  }
}
