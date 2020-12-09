package view.GameView.StatusDisplay;

import java.util.Map;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * EnemyProgress extends StatusDisplayComponent to display and update based on how close the
 * enemyWaves are to completion
 * @author Heather Grune (hlg20) and Alex Chao (ac590)
 */
public class EnemyProgress extends StatusDisplayComponent {

  private final ProgressBar myEnemyProgress;

  public EnemyProgress(Map<String, Image> images, double height, GameStatusAPI gameStatus) {
    super(images, height, gameStatus);
    myEnemyProgress = new ProgressBar(0);
    this.getChildren().add(myEnemyProgress);
    myEnemyProgress.setId("enemyProgress");
  }

  /**
   * @see StatusDisplayComponent#update()
   */
  @Override
  public void update() {
    getGameStatus().applyProgress(
        myEnemyProgress::setProgress);
  }
}
