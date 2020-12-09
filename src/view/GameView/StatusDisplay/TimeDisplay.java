package view.GameView.StatusDisplay;

import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * Displays and updates the amount of Time that has elapsed during the game.
 * @author Heather Grune (hlg20) and Alex Chao (ac590)
 */
public class TimeDisplay extends StatusDisplayComponent {

  private final Text myTime;

  public TimeDisplay(Map<String, Image> images, double height, GameStatusAPI gameStatus) {
    super(images, height, gameStatus);
    myTime = new Text(Integer.toString(0));
    myTime.setId("time");
    myTime.getStyleClass().add("status-text");
    this.getChildren().add(myTime);
  }

  /**
   * @see StatusDisplayComponent#update()
   */
  @Override
  public void update() {
    getGameStatus().applyTime((timeElapsed) ->
        myTime.setText(Integer.toString(timeElapsed)));
  }
}
