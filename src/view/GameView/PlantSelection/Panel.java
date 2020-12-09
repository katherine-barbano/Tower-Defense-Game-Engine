package view.GameView.PlantSelection;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * This is a implementation of PlantSelection where all of the possible towers are set initially
 * and do not change over time. The creation of plants uses up sun.
 * @author alex chao
 */
public class Panel extends PlantSelection {

  private final int selectionCapacity;
  private int numOfPlants = 0;

  public Panel(Map<String, Image> plantConfig,
      PlantHandler plantCreationHandler) {
    super(plantConfig, plantCreationHandler);
    selectionCapacity = plantConfig.size();
  }


  /**
   * This method updates the plant selection available in the panel by taking all of the plants
   * in the config that was passed into the constructor and creating a selection option for each
   * of these plants.
   * @see PlantSelection#updatePlantSelection()
   */
  @Override
  public void updatePlantSelection() {
    if (numOfPlants < selectionCapacity) {
      addPlants();
    }
  }

  @Override
  protected void addPlants() {
    getPlantConfig().forEach((name, image) -> {
      addSeed(name, image).setId(name);
      this.numOfPlants++;
    });
  }

  @Override
  protected void addEventListener(TowerBox plant, String plantName) {
    EventHandler<MouseEvent> eventHandler = event -> setPlantCreation(plantName);
    plant.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }

  @Override
  protected void setPlantCreation(String plantName) {
    getPlantCreationHandler().setPlantCreationWithSun(plantName);
  }

}
