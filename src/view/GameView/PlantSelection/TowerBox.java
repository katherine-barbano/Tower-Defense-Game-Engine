package view.GameView.PlantSelection;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This represents a seed that the user clicks on in order to plant a seed. It is used inside of
 * PlantSelection for each tower
 */
public class TowerBox extends ImageView {

  public TowerBox(Image plantImage, double width, double height) {
    this.setImage(plantImage);
    this.setFitHeight(height);
    this.setFitWidth(width);
  }
}
