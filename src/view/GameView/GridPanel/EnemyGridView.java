package view.GameView.GridPanel;


import java.util.Map;
import javafx.scene.image.Image;
import model.gameComponents.GameComponent;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import view.GameView.GridPanel.GridComponents.EnemyGridComponentDisplay;
import view.GameView.GridPanel.GridComponents.GameComponentDisplay;

/**
 * The EnemyGridView is responsible for updating and displaying the enemies in the model in their
 * correct positions.
 * @author Heather Grune (hlg20)
 */
public class EnemyGridView extends GridViewStructure {

  private int zombieNumber = 0;

  public EnemyGridView(GameDisplayAPI game, Map<String, Image> images, double cellHeight,
      double cellWidth) {
    super(game, images, cellHeight, cellWidth);
    setupGrid();
    this.getStyleClass().add("grid");
    this.setId("player-grid");
  }


  @Override
  protected void setupGrid() {
    myGame.enactFunctionOnEnemyComponents((gameComponent, row, col, health) -> {
      if (gameComponent != null) {
        addEnemy(gameComponent, row, col, health);
      }
    });
  }

  private void addEnemy(GameComponent gameComponent, int row, int col, double health) {
    double enemyDisplayHeight =
        myCellHeight * myGame.getHeightOfEnemyAreaComponent(gameComponent);
    double enemyDisplayWidth = myCellWidth * myGame.getWidthOfEnemyAreaComponent(gameComponent);
    GameComponentDisplay gridComponent = new EnemyGridComponentDisplay(gameComponent, myImages,
        enemyDisplayHeight, enemyDisplayWidth, col * myCellWidth,
        row * myCellHeight, health);
    this.getChildren().add(gridComponent);
    setID(gridComponent);
  }


  private void setID(GameComponentDisplay displayComponent) {
    displayComponent.setId(String.format("enemy%d", zombieNumber));
    zombieNumber++;
  }

  /**
   * Clear and update the zombies in the EnemyGridView.
   */
  @Override
  public void update() {
    zombieNumber = 0;
    this.getChildren().clear();
    setupGrid();
  }

}
