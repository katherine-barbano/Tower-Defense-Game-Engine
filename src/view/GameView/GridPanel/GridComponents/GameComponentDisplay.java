package view.GameView.GridPanel.GridComponents;

import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.gameComponents.GameComponent;

/**
 * The GameComponentDisplay class is the superclass for EnemyGridComponentDisplay and PlayerGridComponentDisplay.
 * This class defines the constructor for all grid components.  It uses the name of the gameComponent
 * as a key to access gameComponent's image from the map.
 * @author Heather Grune (hlg20)
 */
public abstract class GameComponentDisplay extends ImageView {

  protected Image myImage;

  public GameComponentDisplay(GameComponent gameComponent, Map<String, Image> images,
      double height, double width, double xPosition, double yPosition) {
    this.setImageFromGameComponent(gameComponent, images);
    this.setFitHeight(height);
    this.setFitWidth(width);
    this.setX(xPosition);
    this.setY(yPosition);
  }

  protected void setImageFromGameComponent(GameComponent gameComponent, Map<String, Image> images) {
    String componentName = gameComponent.getName();
    String componentString =
        componentName.substring(0, 1).toUpperCase() + componentName.substring(1)
            .toLowerCase();

    this.myImage = images.get(componentString);
    this.setImage(myImage);
  }
}
