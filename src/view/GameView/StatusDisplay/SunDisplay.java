package view.GameView.StatusDisplay;

import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * Displays and updates the amount of sun that the player has.
 * @author Heather Grune (hlg20) and Alex Chao (ac590)
 */
public class SunDisplay extends StatusDisplayComponent {

  private final Text mySunAmount;

  public SunDisplay(Map<String, Image> images, double height, GameStatusAPI gameStatus) {
    super(images, height, gameStatus);
    mySunAmount = new Text(Integer.toString(0));
    mySunAmount.setId("sunAmount");
    mySunAmount.getStyleClass().add("status-text");
    this.getChildren().add(mySunAmount);
  }

  /**
   * @see StatusDisplayComponent#update()
   */
  @Override
  public void update() {
    getGameStatus().applySun((newSunAmount) ->
        mySunAmount.setText(Integer.toString(newSunAmount)));
  }
}
