package view.GameView.GridPanel.GridComponents;

import java.util.Map;
import javafx.scene.image.Image;
import model.gameComponents.GameComponent;

/**
 * The EnemyGridComponentDisplay class extends the superclass GameComponentDisplay.  It is responsible
 * for visually displaying all EnemyGridComponents.
 * @author Heather Grune (hlg20)e
 */
public class EnemyGridComponentDisplay extends GameComponentDisplay {

  public EnemyGridComponentDisplay(GameComponent gameComponent, Map<String, Image> images,
      double height, double width, double xPosition, double yPosition, double health) {
    super(gameComponent, images, height, width, xPosition, yPosition);
    this.setOpacity(health);
  }

}
