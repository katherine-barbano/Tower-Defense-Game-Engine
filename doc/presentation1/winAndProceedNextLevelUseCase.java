/***
 * Located in Controller.
 *
 * User wins a level and progresses to next level within the same variation
 */
public class winAndProceedNextLevelUseCase {
  private Game modelAPI;
  private GameView viewAPI;

  public winAndProceedNextLevelUseCase() {
    modelAPI = new GameConcrete();
    viewAPI = new GameViewConcrete();
  }

  private void checkAndHandleAllWavesOverWinningConditions() {
    if(allWavesOver()) {
      createNewLevel();
    }
  }

  private boolean allWavesOver() {
    //implemented in controller by reading data files
  }

  private void createNewLevel() {
    //level is over. Instantiate new game to begin next one.
    modelAPI = new GameConcrete();
    //moves the view to a new level's game view
    viewAPI = new GameViewConcrete();
    updateModelWithEnemies();
    updateViewWithEnemies();
  }

  private updateModelWithEnemies() {
    //following line would be in some type of loop to be read in from data files in controller.
    //modelAPI.createEnemy(subclassFromPropertiesFile, positionFromDataFile);
  }

  private updateViewWithEnemies() {
    //transfers info from the model about enemies currently in enemy grid to be displayed in view
    Set<Position> allPositionsInEnemyGrid = modelAPI.getAllPositionsInEnemyGrid();
    for(Position position:allPositionsInEnemyGrid) {
      Class enemyType = modelAPI.getEnemyGridComponentTypeAtPosition();
      viewAPI.updateEnemyDisplay(enemyType,position);
    }
  }
}