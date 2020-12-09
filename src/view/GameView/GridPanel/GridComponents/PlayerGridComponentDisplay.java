package view.GameView.GridPanel.GridComponents;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.gameComponents.GameComponent;

/**
 * PlayerGridComponentDisplay extends GameComponentDisplay in order to display all Player Grid Components.
 * This class also defines methods to set an event handler and update the component when the player clicks on the component
 * and plants a tower in the cell.
 * @author Heather Grune (hlg20)
 */
public class PlayerGridComponentDisplay extends GameComponentDisplay {

  private String myName;

  public PlayerGridComponentDisplay(GameComponent gameComponent,
      Map<String, Image> images, double height, double width, double xPosition, double yPosition) {
    super(gameComponent, images, height, width, xPosition, yPosition);
    myName = gameComponent.getName();
  }

  /**
   * Accessor for the name of the gameComponent
   * @return name of the gameComponent
   */
  public String getName() {
    return myName;
  }

  /**
   * Adds event handler to player grid component
   * @param eventHandler PlantCreation handler eventHandler
   */
  public void setNewEventHandler(EventHandler<MouseEvent> eventHandler) {
    this.setEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
  }

  /**
   * Updates the image and name of a component in the player grid.
   * @param gameComponent The new gameComponent
   * @param images Map of images for all gameComponent types
   */
  public void updateComponent(GameComponent gameComponent, Map<String, Image> images) {
    this.setImageFromGameComponent(gameComponent, images);
    myName = gameComponent.getName();
  }


}
