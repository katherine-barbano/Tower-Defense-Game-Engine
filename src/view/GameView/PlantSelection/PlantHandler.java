package view.GameView.PlantSelection;

import controller.Alerts;
import controller.ExternalAccess.ConfigAccess;
import java.io.IOException;
import java.util.function.BiConsumer;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import model.gameplay.gameplayResources.Position;

/**
 * The plant handler holds the information about what the user's next click should do regarding towers,
 * including creating them or removing them. It is used in the PlantSelection to set the functionality
 * of all of the TowerBoxes and is also used by the DiggingTool to remove plants. All of the cells
 * in the PlayerView have an event handler that is dependent on this object's
 */
public class PlantHandler {

  private final GameDisplayAPI myGame;
  private final ConfigAccess plantConfig = new ConfigAccess();
  private final Alerts alerts = new Alerts();
  private BiConsumer<Integer, Integer> plantCreation;

  public PlantHandler(GameDisplayAPI game) {
    myGame = game;
  }

  /**
   * Using the GameDisplayAPI, it sets its event handler
   * tells the back end to create a plant and use up the sun
   * @param plantName
   */
  public void setPlantCreationWithSun(String plantName) {
    plantCreation = (row, col) -> {
      try {
        myGame.createTowerWithSun(plantConfig.getTowerConfig(plantName), new Position(row, col));
      } catch (IOException e) {
        alerts.makeAlert(e.getMessage());
      }
    };
  }

  /**
   * Using the GameDisplayAPI, it sets its event handler to
   * tell the back end to create a plant and not use up sun
   * @param plantName
   */
  public void setPlantCreationWithoutSun(String plantName) {
    plantCreation = (row, col) -> {
      try {
        myGame.createTowerWithoutSun(plantConfig.getTowerConfig(plantName), new Position(row, col));
      } catch (IOException e) {
        alerts.makeAlert(e.getMessage());
      }
    };
  }

  /**
   * This sets its event handler to tell the back end to remove the component at the location that
   * was clicked
   */
  public void setPlantRemoval() {
    plantCreation = (row, col) -> myGame.removePlayerComponent(new Position(row, col));
  }

  /**
   * Returns an event handler that takes in a row and a column and enacts an action at that position
   * @return
   */
  public BiConsumer<Integer, Integer> getEventHandler() {
    return plantCreation;
  }
}
