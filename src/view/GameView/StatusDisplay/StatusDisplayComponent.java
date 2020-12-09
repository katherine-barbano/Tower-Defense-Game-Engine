package view.GameView.StatusDisplay;

import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * Represents all of the possible status displays that are updated from the back end and displayed
 * to the user. Used by StatusBar
 * @author Heather Grune (hlg20) and Alex Chao
 */
public abstract class StatusDisplayComponent extends FlowPane {

  private final GameStatusAPI gameStatus;

  public StatusDisplayComponent(Map<String, Image> images, double height,
      GameStatusAPI gameStatus) {
    Image sunImage = images.get(this.getClass().getSimpleName());
    ImageView myImageView = new ImageView(sunImage);
    myImageView.setFitHeight(height);
    myImageView.setFitWidth(height);
    this.getChildren().add(myImageView);
    this.gameStatus = gameStatus;
  }

  protected GameStatusAPI getGameStatus() {
    return gameStatus;
  }

  /**
   * Updates the value of the status from the backend
   */
  public abstract void update();

}
