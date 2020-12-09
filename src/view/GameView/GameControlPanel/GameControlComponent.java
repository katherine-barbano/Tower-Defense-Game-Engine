package view.GameView.GameControlPanel;

import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

/**
 * This represents a specific selection in the game control panel that has its own event listener
 *
 * @author heather grune and alex chao
 */
public abstract class GameControlComponent extends FlowPane {

  public GameControlComponent(Map<String, Image> images, double height) {
    Image sunImage = images.get(this.getClass().getSimpleName());
    ImageView myImageView = new ImageView(sunImage);
    myImageView.setFitHeight(height);
    myImageView.setFitWidth(height);
    this.getChildren().add(myImageView);
    addEventListener();
  }

  protected abstract void addEventListener();

}
