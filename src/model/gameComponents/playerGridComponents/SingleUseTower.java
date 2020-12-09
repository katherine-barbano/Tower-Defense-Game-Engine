package model.gameComponents.playerGridComponents;

import controller.ConfigObjects.TowerConfig;
import java.util.HashMap;
import java.util.Map;
import model.gameComponents.Fire;
import model.gameComponents.GameComponent;
import model.gameComponents.Removable;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**
 * The single use tower is a type of tower that performs an action and then is removed from the
 * game
 *
 * @author alex chao
 */
public class SingleUseTower extends Tower implements Removable {

  private final int numberOfRows;
  private final int numberOfColumns;
  private final int animationSpeed;
  private boolean exploded;

  public SingleUseTower(TowerConfig towerConfig, int numberOfRows, int numberOfColumns,
      int animationSpeed) {
    super(towerConfig, animationSpeed);
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.animationSpeed = animationSpeed;
    this.exploded = false;
  }

  /**
   * This method lights all of the states around it on fire if they are empty
   * @param currentPosition
   * @param gameStatus
   * @return
   */
  @Override
  public Map<Position, GameComponent> enactAction(Position currentPosition,
      GameStatusAPI gameStatus) {
    Map<Position, GameComponent> newSurroundingStates = new HashMap<>();
    int currentRow = currentPosition.getRow();
    int currentCol = currentPosition.getColumn();
    for (int newRow = currentRow - 1; newRow <= currentRow + 1; newRow++) {
      for (int newCol = currentCol - 1; newCol <= currentCol + 1; newCol++) {
        if (!(newRow == currentRow && newCol == currentCol) && fireInRange(newRow, newCol)) {
          Position newPosition = new Position(newRow, newCol);
          newSurroundingStates.put(newPosition, new Fire(getAnimationSpeed(), this, Tower.class));
        }
      }
    }
    exploded = true;
    return newSurroundingStates;
  }

  private boolean fireInRange(int newRow, int newCol) {
    return newRow >= 0 && newCol >= 0 && newRow < (numberOfRows / animationSpeed) && newCol < (
        numberOfColumns / animationSpeed);
  }

  @Override
  public boolean isOutOfRange() {
    return exploded;
  }

}
