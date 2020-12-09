package view.GameView.GridPanel;

import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;
import view.GameView.PlantSelection.PlantHandler;

/**
 * PlayerGridView extends GridViewStructure and is responsible for displaying all cells and towers
 * in the PlayerGrid.  Additionally, it is responsible for setting event listeners on each
 * PlayerGridComponentDisplay cell in the PlayerGridView.
 * @author Heather Grune (hlg20)
 */
public class PlayerGridView extends GridViewStructure {

  private final PlayerGridComponentDisplay[][] gridComponents;
  private final PlantHandler myPlantCreationHandler;

  public PlayerGridView(GameDisplayAPI game, Map<String, Image> images, int rows, int cols,
      double cellHeight,
      double cellWidth, PlantHandler plantCreationHandler) {
    super(game, images, cellHeight, cellWidth);
    this.myPlantCreationHandler = plantCreationHandler;
    this.gridComponents = new PlayerGridComponentDisplay[rows][cols];
    setupGrid();
    this.getStyleClass().add("grid");
    this.setId("player-grid");
  }

  @Override
  protected void setupGrid() {
    myGame.enactFunctionOnPlayerComponents((gameComponent, row, col, health) -> {
      PlayerGridComponentDisplay gridComponent = new PlayerGridComponentDisplay(gameComponent,
          myImages, myCellHeight, myCellWidth, col * myCellWidth, row * myCellHeight);
      gridComponent.setOpacity(health);
      gridComponents[row][col] = gridComponent;
      gridComponent.setId(String.format("player%d,%d", row, col));
      this.getChildren().add(gridComponent);
    });
  }

  @Override
  public void update() {
    myGame.enactFunctionOnPlayerComponents((gameComponent, row, col, health) -> {
      gridComponents[row][col].updateComponent(gameComponent, myImages);
      setNewGridEventListener(gridComponents[row][col], row, col);
    });
  }

  private void setNewGridEventListener(PlayerGridComponentDisplay grid, int row, int col) {
    EventHandler<MouseEvent> eventHandler = e -> myPlantCreationHandler.getEventHandler()
        .accept(row, col);
    grid.setNewEventHandler(eventHandler);
  }

}
