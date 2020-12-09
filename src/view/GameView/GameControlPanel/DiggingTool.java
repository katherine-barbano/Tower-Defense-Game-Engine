package view.GameView.GameControlPanel;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import view.GameView.PlantSelection.PlantHandler;

/**
 * The digging tool has an event handler that removes a tower on click. If there is no tower there
 * nothing happens
 * @author alex chao and heather grune
 */
public class DiggingTool extends GameControlComponent {

  private final PlantHandler myPlantRemovalHandler;

  public DiggingTool(Map<String, Image> images, double height, PlantHandler plantRemovalHandler) {
    super(images, height);
    this.myPlantRemovalHandler = plantRemovalHandler;
  }

  @Override
  protected void addEventListener() {
    EventHandler<MouseEvent> eventHandler = event -> setPlantRemoval();
    this.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }

  private void setPlantRemoval() {
    this.myPlantRemovalHandler.setPlantRemoval();
  }


}
