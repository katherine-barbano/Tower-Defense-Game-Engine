package view.GameView.PlantSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * This is an implementation of PlantSelection where the possible towers come up as a queue but
 * each plant can only be planted once. Since the plants are on a queue, it does not require sun to
 * create towers.
 */
public class Queue extends PlantSelection {

  private int selectionCapacity;
  private int numOfPlants = 0;

  public Queue(Map<String, Image> plantConfig, PlantHandler plantCreationHandler) {
    super(plantConfig, plantCreationHandler);
    selectionCapacity = 5;
  }

  /**
   * This method adds in a new seed to the queue if the capacity has not already been reached.
   * @see PlantSelection#updatePlantSelection()
   */
  @Override
  public void updatePlantSelection() {
    if (this.numOfPlants < this.selectionCapacity) {
      addPlants();
      numOfPlants++;
    }
  }

  @Override
  protected void addPlants() {
    List<String> keysAsArray = new ArrayList<>(getPlantConfig().keySet());
    Random r = new Random();
    String name = keysAsArray.get(r.nextInt(keysAsArray.size()));
    addSeed(name, getPlantConfig().get(name)).setId(String.format("seed%d", numOfPlants));
  }

  @Override
  protected void addEventListener(TowerBox plant, String plantName) {
    EventHandler<MouseEvent> eventHandler = event -> {
      setPlantCreation(plantName);
      removeFromSelection(plant);
      selectionCapacity++;
    };
    plant.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }

  @Override
  protected void setPlantCreation(String plantName) {
    getPlantCreationHandler().setPlantCreationWithoutSun(plantName);
  }
}


