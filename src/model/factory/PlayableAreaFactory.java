package model.factory;

import java.util.ArrayList;
import java.util.List;
import model.gameComponents.GameComponent;
import model.gameComponents.SingleState;
import model.gameplay.gameplayResources.Position;
import model.gameplay.playableArea.PlayableArea;
import model.gameplay.playableArea.PlayableAreaData;

/***
 * Contains helper methods used by both GridGameControl and PathGameControl to instantiate new
 * playerAreas and enemyAreas with varying number of rows and columns.
 *
 * @author Katherine Barbano
 */
public class PlayableAreaFactory {

  /***
   * Creates an instance of a PlayableArea where the number of rows and columns are the same as
   * initialBoard, and the states in the area are the same as in initialBoard too. Typically
   * used to instantiate the playerArea instance.
   * @param initialBoard List<List<SingleState>>
   * @param rowSize int
   * @param columnSize int
   * @return PlayableArea
   */
  public PlayableArea createPlayerPlayableArea(List<List<SingleState>> initialBoard, int rowSize,
      int columnSize) {
    PlayableArea emptyPlayerArea = new PlayableAreaData(rowSize, columnSize);
    List<List<GameComponent>> initialBoardOfComponents = new ArrayList<>();
    for (int row = 0; row < rowSize; row++) {
      List<GameComponent> rowList = new ArrayList<>(initialBoard.get(row));
      initialBoardOfComponents.add(rowList);
    }
    emptyPlayerArea.initializeGameComponentDataFromBoard(initialBoardOfComponents);
    return emptyPlayerArea;
  }

  /***
   * Creates an instance of a PlayableArea where the number of rows and columns are equal to
   * player row size multiplied by animation speed, and player column size multiplied by animation speed.
   * Fills the Playable Area only with empty states.
   * @param playerRowSize int
   * @param playerColumnSize int
   * @param animationSpeed int
   * @return PlayableArea
   */
  public PlayableArea createEnemyPlayableAreaOverlapNoPath(int playerRowSize, int playerColumnSize,
      int animationSpeed) {
    int numberOfTotalRows = (playerRowSize * animationSpeed);
    int numberOfTotalColumns = (playerColumnSize * animationSpeed);
    return new PlayableAreaData(numberOfTotalRows, numberOfTotalColumns);
  }

  /***
   * Creates an instance of a PlayableArea where the number of rows and columns are equal to
   * player row size multiplied by animation speed, and player column size multiplied by animation speed.
   * Fills the PlayableArea with BLOCKED states, except it fills the positions corresponding to the PATH states
   * in initialBoard with EMPTY states in the returned PlayableArea.
   * @param initialBoard int
   * @param playerRowSize int
   * @param playerColumnSize int
   * @param animationSpeed int
   * @return PlayableArea
   */
  public PlayableArea createEnemyPlayableAreaOverlapWithPath(List<List<SingleState>> initialBoard,
      int playerRowSize, int playerColumnSize, int animationSpeed) {
    int numberOfTotalRows = (playerRowSize * animationSpeed);
    int numberOfTotalColumns = (playerColumnSize * animationSpeed);
    PlayableArea emptyArea = new PlayableAreaData(numberOfTotalRows, numberOfTotalColumns);
    for (int row = 0; row < playerRowSize; row++) {
      for (int column = 0; column < playerColumnSize; column++) {
        SingleState state = initialBoard.get(row).get(column);
        if (state != SingleState.PATH) {
          setComponentsAtPosition(emptyArea, SingleState.BLOCKED, row, column, animationSpeed);
        }
      }
    }
    return emptyArea;
  }

  private void setComponentsAtPosition(PlayableArea emptyArea, SingleState setToState,
      int playerRow, int playerCol, int animationSpeed) {
    int enemyRowStart = playerRow * animationSpeed;
    int enemyColumnStart = playerCol * animationSpeed;
    for (int row = enemyRowStart; row < enemyRowStart + animationSpeed; row++) {
      for (int column = enemyColumnStart; column < enemyColumnStart + animationSpeed; column++) {
        Position position = new Position(row, column);
        emptyArea.setGameComponentAtPosition(position, setToState);
      }
    }
  }
}
