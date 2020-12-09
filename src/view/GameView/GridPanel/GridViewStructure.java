package view.GameView.GridPanel;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.image.Image;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;

/**
 * GridViewStructure is the superclass for PlayerGridView and EnemyGridView.  Defines basic constructor
 * and the basic abstract methods for these components.
 */
public abstract class GridViewStructure extends Group {

  protected final GameDisplayAPI myGame;
  protected final Map<String, Image> myImages;
  protected final double myCellHeight;
  protected final double myCellWidth;

  public GridViewStructure(GameDisplayAPI game, Map<String, Image> images, double cellHeight,
      double cellWidth) {
    this.myGame = game;
    this.myImages = images;
    this.myCellHeight = cellHeight;
    this.myCellWidth = cellWidth;
  }

  /**
   * Update the components in the GridViewStructure
   */
  public abstract void update();

  protected abstract void setupGrid();

}
