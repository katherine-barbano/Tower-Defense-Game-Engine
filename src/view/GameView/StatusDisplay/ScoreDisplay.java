package view.GameView.StatusDisplay;

import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * ScoreDisplay extends the StatusDisplayComponent to display and update the user's score
 * @author Heather Grune (hlg20) and Alex Chao (ac590)
 */
public class ScoreDisplay extends StatusDisplayComponent {

  private final Text myScore;

  public ScoreDisplay(Map<String, Image> images, double height, GameStatusAPI gameStatus) {
    super(images, height, gameStatus);
    myScore = new Text(Integer.toString(0));
    this.getChildren().add(myScore);
    myScore.getStyleClass().add("status-text");
  }

  /**
   * @see StatusDisplayComponent#update()
   */
  @Override
  public void update() {
    getGameStatus().applyPoints((newScore) ->
        myScore.setText(Integer.toString(newScore)));
  }
}
