package view.GameView.PlantSelection;

import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

/**
 * Abstract class to define functionality for what the user interacts with in order to create new
 * tower. The use of the abstract class allows the View to use the same methods without knowing
 * what specific plant selection is being used. Its subclasses differ in how they handle the user
 * clicking on a seed differntly.
 * @author alex chao
 */
public abstract class PlantSelection {

  private final TilePane plantSelect = new TilePane();
  private final Map<String, Image> plantConfig;
  private final PlantHandler plantCreationHandler;

  public PlantSelection(Map<String, Image> plantConfig, PlantHandler plantCreationHandler) {
    plantSelect.setPrefColumns(1);
    this.plantCreationHandler = plantCreationHandler;
    this.plantConfig = plantConfig;
    plantSelect.setAlignment(Pos.CENTER_LEFT);
    plantSelect.setId("plantSelect");
  }

  protected Map<String, Image> getPlantConfig() {
    return plantConfig;
  }

  protected PlantHandler getPlantCreationHandler() {
    return this.plantCreationHandler;
  }

  /**
   * This method updates the possible plants that can be created at each step depending on which
   * type of plant selection is being used.
   */
  public abstract void updatePlantSelection();

  protected abstract void addPlants();

  protected TowerBox addSeed(String name, Image image) {
    TowerBox towerBox = new TowerBox(image, 120, 80);
    addEventListener(towerBox, name);
    plantSelect.getChildren().add(towerBox);
    return towerBox;
  }

  protected abstract void addEventListener(TowerBox plant, String plantName);

  protected abstract void setPlantCreation(String plantName);

  protected void removeFromSelection(TowerBox plant) {
    plantSelect.getChildren().remove(plant);
  }

  /**
   * Returns the TilePane that represents all of the possible towers that can be created
   * @return
   */
  public TilePane getPlantSelect() {
    return plantSelect;
  }
}

