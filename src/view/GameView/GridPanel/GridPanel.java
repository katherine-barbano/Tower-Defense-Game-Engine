package view.GameView.GridPanel;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.image.Image;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import view.GameView.PlantSelection.PlantHandler;

/**
 * GridPanel constructs the PlayerGridView and EnemyGridView and determines the sizing of the cells
 * in each.  We add the PlayerGridView and EnemyGridView to the GridPanel with the EnemyGridView directly
 * overlaying the PlayerGridView
 * @author Heather Grune (hlg20)
 */
public class GridPanel extends Group {

  private final GameDisplayAPI myGame;
  private final double myHeight;
  private final double myWidth;
  private final Map<String, Image> myImages;
  private final double myPlayerCellSize;
  private final int myPlayerGridRows;
  private final int myPlayerGridCols;
  private PlayerGridView myPlayerGrid;
  private EnemyGridView myEnemyGrid;

  public GridPanel(GameDisplayAPI game, Map<String, Image> images, double height, double width,
      PlantHandler plantCreationHandler) {
    this.myGame = game;
    this.myHeight = height;
    this.myWidth = width;
    this.myImages = images;
    this.myPlayerGridRows = myGame.getPlayerAreaNumberOfRows();
    this.myPlayerGridCols = myGame.getPlayerAreaNumberOfColumns();
    this.myPlayerCellSize = getCellSize(myPlayerGridRows, myPlayerGridCols);
    this.prefHeight(myHeight);
    this.prefWidth(myWidth);
    this.setId("grid-view");
    createPlayerGrid(plantCreationHandler);
    createEnemyGrid();
  }

  private void createEnemyGrid() {

    double enemyCellHeight =
        myPlayerCellSize * myPlayerGridRows / myGame.getEnemyAreaNumberOfRows();
    double enemyCellWidth =
        myPlayerCellSize * myPlayerGridCols / myGame.getEnemyAreaNumberOfColumns();
    this.myEnemyGrid = new EnemyGridView(myGame, myImages, enemyCellHeight,
        enemyCellWidth);
    this.getChildren().add(myEnemyGrid);
  }

  private void createPlayerGrid(PlantHandler plantCreationHandler) {
    this.myPlayerGrid = new PlayerGridView(myGame, myImages, myPlayerGridRows, myPlayerGridCols,
        myPlayerCellSize, myPlayerCellSize, plantCreationHandler);
    this.getChildren().add(myPlayerGrid);
  }

  private double getCellSize(int numGridRows, int numGridCols) {
    double cellHeight = myHeight / numGridRows;
    double cellWidth = myWidth / numGridCols;
    return Math.min(cellHeight, cellWidth);
  }

  /**
   * Update the state of the components in the EnemyGridView.
   */
  public void updateEnemyGrid() {
    myEnemyGrid.update();
  }

  /**
   * Update the state of the components in the PlayerGridView.
   */
  public void updatePlayerGrid() {
    myPlayerGrid.update();
  }

}
