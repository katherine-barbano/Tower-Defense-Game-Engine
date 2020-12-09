package model.gameplay.playableArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import model.gameComponents.GameComponent;
import model.gameComponents.Health;
import model.gameComponents.SingleState;
import model.gameplay.MVCInteraction.concreteModel.GameControl;
import model.gameplay.gameplayResources.ModelException;
import model.gameplay.gameplayResources.Position;
import model.gameplay.gameplayResources.QuadConsumer;

/***
 * PlayableAreaData implements PlayableArea interface. It uses a List<List<GameComponent>> called gameComponentData
 * as the data structure to store GameComponents. The higher level model modules like GameControl
 * reference the PlayableArea interface rather than the concrete subclass.
 * This effectively separates out the List<List<GameComponent>> data structure out so no upper level Model modules that
 * reference PlayableArea, like GameControl, can have access to the data structure implementation itself.
 * Instead, PlayableArea has helper methods to manipulate the data without providing access
 * to the data structure itself
 *
 * @author Katherine Barbano
 */
public class PlayableAreaData implements PlayableArea {

  public static final String ITERATOR_EXCEPTION = "iteratorException";
  public static final String COLLISION_EXCEPTION = "collisionReplacementException";
  public static final String COMPONENT_NOT_FOUND_EXCEPTION = "componentNotFoundException";
  public static final String POSITION_OUT_OF_RANGE_EXCEPTION = "positionOutOfRangeException";
  public static final String UNEQUAL_ROWS_EXCEPTION = "unequalNumberOfRowsException";
  public static final String DIFFERENT_SIZE_EXCEPTION = "differentSizeException";
  private final int numberOfRows;
  private final int numberOfColumns;
  private final String iteratorException;
  private final String collisionReplacementException;
  private final String componentNotFoundException;
  private final String positionOutOfRangeException;
  private final String unequalNumberOfRowsException;
  private final String differentSizeException;
  private List<List<GameComponent>> gameComponentData;

  /***
   * Initializes the GameComponent data as completely empty. Initializes many strings from Model.properties
   * to use as exception messages throughout the class.
   * @param numberOfRows
   * @param numberOfColumns
   */
  public PlayableAreaData(int numberOfRows, int numberOfColumns) {
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    ResourceBundle modelProperties = ResourceBundle.getBundle(GameControl.MODEL_RESOURCE_PATH);
    iteratorException = modelProperties.getString(ITERATOR_EXCEPTION);
    collisionReplacementException = modelProperties.getString(COLLISION_EXCEPTION);
    componentNotFoundException = modelProperties.getString(COMPONENT_NOT_FOUND_EXCEPTION);
    positionOutOfRangeException = modelProperties.getString(POSITION_OUT_OF_RANGE_EXCEPTION);
    unequalNumberOfRowsException = modelProperties.getString(UNEQUAL_ROWS_EXCEPTION);
    differentSizeException = modelProperties.getString(DIFFERENT_SIZE_EXCEPTION);
    initializeGameComponentDataAsEmpty();
  }

  /***
   * Initializes all GameComponents in the gameComponentData to be EMPTY state.
   */
  private void initializeGameComponentDataAsEmpty() {
    gameComponentData = new ArrayList<>();
    for (int row = 0; row < numberOfRows; row++) {
      List<GameComponent> rowList = new ArrayList<>();
      for (int column = 0; column < numberOfColumns; column++) {
        rowList.add(SingleState.EMPTY);
      }
      gameComponentData.add(rowList);
    }
  }

  /***
   * @see PlayableArea#initializeGameComponentDataFromBoard(List)
   */
  @Override
  public void initializeGameComponentDataFromBoard(List<List<GameComponent>> initialBoard) {
    if (initialBoard.size() != numberOfRows || initialBoard.get(0).size() != numberOfColumns) {

      throw new ModelException(differentSizeException);

    }
    gameComponentData = initialBoard;
  }

  /***
   * @see PlayableArea#createTypeIterator(Class)
   */
  @Override
  public <T> IIterator<T> createTypeIterator(Class tClass) {
    return new TypeIterator(tClass);
  }

  /***
   * @see PlayableArea#getRowRangeToRight(Position, Class)
   * I am aware that this duplicates code with the other 3 getRowRange methods (I just didn't have
   * time to refactor it). A solution to this would be to consolidate these 4 methods into 1
   * that takes a third argument of directionToLookIn that is an int array of 2 elements, where each
   * int is either 1, 0, or -1 to indicate the direction it is facing. This would allow me to combine
   * these 4 methods into a single method and return the row range of the direction the component is facing.
   */
  @Override
  public Map<Position, GameComponent> getRowRangeToRight(Position startPosition,
      Class stopType) {
    int row = startPosition.getRow();
    int columnStart = startPosition.getColumn();
    Map<Position, GameComponent> range = new HashMap<>();
    for (int column = columnStart + 1; column < numberOfColumns; column++) {
      if (putInRangeBeforeStop(row, column, range, stopType)) {
        return range;
      }
    }
    return range;
  }

  /***
   * @see PlayableArea#getRowRangeToLeft(Class, Position)
   *    * I am aware that this duplicates code with the other 3 getRowRange methods (I just didn't have
   *    * time to refactor it). A solution to this would be to consolidate these 4 methods into 1
   *    * that takes a third argument of directionToLookIn that is an int array of 2 elements, where each
   *    * int is either 1, 0, or -1 to indicate the direction it is facing. This would allow me to combine
   *    * these 4 methods into a single method and return the row range of the direction the component is facing.
   */
  @Override
  public Map<Position, GameComponent> getRowRangeToLeft(Class stopType, Position startPosition) {
    int row = startPosition.getRow();
    int columnStart = startPosition.getColumn();
    Map<Position, GameComponent> range = new HashMap<>();
    for (int column = columnStart - 1; column >= 0; column--) {
      if (putInRangeBeforeStop(row, column, range, stopType)) {
        return range;
      }
    }
    return range;
  }

  /***
   * @see PlayableArea#getColumnRangeUp(Position, Class)
   *    * I am aware that this duplicates code with the other 3 getRowRange methods (I just didn't have
   *    * time to refactor it). A solution to this would be to consolidate these 4 methods into 1
   *    * that takes a third argument of directionToLookIn that is an int array of 2 elements, where each
   *    * int is either 1, 0, or -1 to indicate the direction it is facing. This would allow me to combine
   *    * these 4 methods into a single method and return the row range of the direction the component is facing.
   */
  @Override
  public Map<Position, GameComponent> getColumnRangeUp(Position startPosition, Class stopType) {
    int rowStart = startPosition.getRow();
    int columnStart = startPosition.getColumn();
    Map<Position, GameComponent> range = new HashMap<>();
    for (int row = rowStart - 1; row >= 0; row--) {
      if (putInRangeBeforeStop(row, columnStart, range, stopType)) {
        return range;
      }
    }
    return range;
  }

  /***
   * @see PlayableArea#getColumnRangeDown(Position, Class)
   *    * I am aware that this duplicates code with the other 3 getRowRange methods (I just didn't have
   *    * time to refactor it). A solution to this would be to consolidate these 4 methods into 1
   *    * that takes a third argument of directionToLookIn that is an int array of 2 elements, where each
   *    * int is either 1, 0, or -1 to indicate the direction it is facing. This would allow me to combine
   *    * these 4 methods into a single method and return the row range of the direction the component is facing.
   */
  @Override
  public Map<Position, GameComponent> getColumnRangeDown(Position startPosition, Class stopType) {
    int rowStart = startPosition.getRow();
    int columnStart = startPosition.getColumn();
    Map<Position, GameComponent> range = new HashMap<>();
    for (int row = rowStart + 1; row < numberOfRows; row++) {
      if (putInRangeBeforeStop(row, columnStart, range, stopType)) {
        return range;
      }
    }
    return range;
  }

  private boolean putInRangeBeforeStop(int row, int column, Map<Position, GameComponent> range,
      Class stopType) {
    Position positionOfSpot = new Position(row, column);
    GameComponent componentAtSpot = gameComponentData.get(row).get(column);
    range.put(positionOfSpot, componentAtSpot);
    return stopType.isInstance(componentAtSpot);
  }

  /***
   * @see PlayableArea#setGameComponentAtPosition(Position, GameComponent)
   */
  @Override
  public boolean setGameComponentAtPosition(Position position, GameComponent newGameComponent) {
    GameComponent oldGameComponent = getGameComponentAtPosition(position);
    if (oldGameComponent.equals(SingleState.EMPTY)) {
      int row = position.getRow();
      int column = position.getColumn();
      gameComponentData.get(row).set(column, newGameComponent);
      return true;
    } else {
      return false;
    }
  }

  /***
   * @see PlayableArea#getPositionOfGameComponent(GameComponent)
   */
  @Override
  public Position getPositionOfGameComponent(GameComponent gameComponentTarget) {
    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        GameComponent currentComponent = gameComponentData.get(row).get(column);
        if (currentComponent == gameComponentTarget) {
          return new Position(row, column);
        }
      }
    }
    throw new ModelException(componentNotFoundException);
  }

  /***
   * @see PlayableArea#getPositionsOfAllEmptySpots()
   */
  @Override
  public Set<Position> getPositionsOfAllEmptySpots() {
    Set<Position> emptySpotsList = new HashSet<>();
    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        GameComponent currentComponent = gameComponentData.get(row).get(column);
        if (currentComponent == SingleState.EMPTY) {
          Position emptySpot = new Position(row, column);
          emptySpotsList.add(emptySpot);
        }
      }
    }
    return emptySpotsList;
  }

  /***
   * @see PlayableArea#removeGameComponent(GameComponent)
   */
  @Override
  public void removeGameComponent(GameComponent gameComponentToDelete) {
    Position position = getPositionOfGameComponent(gameComponentToDelete);
    int row = position.getRow();
    int column = position.getColumn();
    gameComponentData.get(row).set(column, SingleState.EMPTY);
  }

  /***
   * @see PlayableArea#contains(GameComponent)
   */
  @Override
  public boolean contains(GameComponent component) {
    IIterator areaIterator = new AreaIterator();
    while (areaIterator.hasNext()) {
      if (component == areaIterator.next()) {
        return true;
      }
    }
    return false;
  }

  /***
   * @see PlayableArea#getGameComponentAtPosition(Position)
   */
  @Override
  public GameComponent getGameComponentAtPosition(Position position) {
    try {
      int row = position.getRow();
      int column = position.getColumn();
      return gameComponentData.get(row).get(column);
    } catch (IndexOutOfBoundsException e) {
      throw new ModelException(positionOutOfRangeException);
    }
  }

  /***
   * @see PlayableArea#enactFunctionOnAllGridComponents(QuadConsumer)
   */
  @Override
  public void enactFunctionOnAllGridComponents(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent) {
    enactFunctionOnAllGridComponentsExcept(updateGameComponent, new HashSet<>());
  }

  /***
   * @see PlayableArea#enactFunctionOnAllGridComponentsExcept(QuadConsumer, Set)
   */
  @Override
  public void enactFunctionOnAllGridComponentsExcept(
      QuadConsumer<GameComponent, Integer, Integer, Double> updateGameComponent,
      Set<GameComponent> typeToIgnore) {
    for (int row = 0; row < numberOfRows; row++) {
      for (int column = 0; column < numberOfColumns; column++) {
        GameComponent componentAtPosition = gameComponentData.get(row).get(column);
        double health = getHealthPercentOfComponent(componentAtPosition);
        if (!typeToIgnore.contains(componentAtPosition)) {
          updateGameComponent.accept(componentAtPosition, row, column, health);
        }
      }
    }
  }

  /***
   * Helper for enactFunction methods that returns what the percentage of Health the GameComponent has.
   * This allows the view to implement transparency as the enemies are killed. If it is not a Health,
   * the GameComponent transparency gets set to 1 in the View.
   * @param component GameComponent to get the health percentage of.
   * @return percentage
   */
  private double getHealthPercentOfComponent(GameComponent component) {
    double healthPercent = 1;
    if (component instanceof Health) {
      healthPercent = ((Health) component).getPercentageHealth();
    }
    return healthPercent;
  }

  /***
   * @see PlayableArea#setLastColumnOfGameComponents(List)
   */
  @Override
  public void setLastColumnOfGameComponents(List<GameComponent> gameComponentsToAdd) {
    if (gameComponentsToAdd.size() != numberOfRows) {
      throw new ModelException(unequalNumberOfRowsException);
    }
    for (int row = 0; row < numberOfRows; row++) {
      Position position = new Position(row, numberOfColumns - 1);
      if (getGameComponentAtPosition(position) == SingleState.EMPTY) {
        setGameComponentAtPosition(position, gameComponentsToAdd.get(row));
      }
    }
  }

  /***
   * @see PlayableArea#getNumberOfRows()
   */
  @Override
  public int getNumberOfRows() {
    return numberOfRows;
  }

  /***
   * @see PlayableArea#getNumberOfColumns()
   */
  @Override
  public int getNumberOfColumns() {
    return numberOfColumns;
  }

  /***
   * Iterates through a row first, then through the columns. Used to implement the TypeIterator
   */
  private class AreaIterator implements IIterator<GameComponent> {

    private int rowPosition;
    private int columnPosition;

    /***
     * Initializes row and column position
     */
    private AreaIterator() {
      rowPosition = 0;
      columnPosition = 0;
    }

    /***
     * Returns true if any component in the entire PlayableArea has not yet been returned
     * @return true if there are gameComponents that have not been iterated through yet
     */
    @Override
    public boolean hasNext() {
      return columnPosition < numberOfColumns - 1 || rowPosition < numberOfRows;
    }

    /***
     * Returns the next GameComponent in the row. If the row is finished, returns the next
     * GameComponent in the 0th index of the next row down.
     * @return GameComponent
     */
    @Override
    public GameComponent next() {
      GameComponent nextGameComponent;
      if (rowPosition < numberOfRows) {
        nextGameComponent = gameComponentData.get(rowPosition).get(columnPosition);
      } else if (hasNext()) {
        rowPosition = 0;
        columnPosition++;
        nextGameComponent = gameComponentData.get(rowPosition).get(columnPosition);
      } else {
        throw new ModelException(iteratorException);
      }
      rowPosition++;
      return nextGameComponent;
    }
  }

  /***
   *    * An Iterator of a specific subclass (or superclass type). This iterator returns the
   *    * next value in the game component data that is of the type specified by tClass and <T>. For example, if
   *    * the type is Shooter, all components returned by this IIterator will be Shooters. All other components
   *    * will be ignored by the iterator.
   * @param <T> Target type to iterate through
   */
  private class TypeIterator<T> implements IIterator<T> {

    private final Class<T> type;
    private final AreaIterator areaIterator;
    private int numberOfTypeInArea;
    private int numberOfTypeSeenSoFar;

    /***
     * Creates a new type iterator where the target is type
     * @param type targetType
     */
    private TypeIterator(Class type) {
      this.type = type;
      areaIterator = new AreaIterator();
      numberOfTypeSeenSoFar = 0;
      setNumberOfTypeInArea();
    }

    /***
     * Used to set the number of the target type that are currently present in the PlayableARea
     * using reflection.
     */
    private void setNumberOfTypeInArea() {
      numberOfTypeInArea = 0;
      for (List<GameComponent> rowList : gameComponentData) {
        for (GameComponent component : rowList) {
          if (type.isInstance(component)) {
            numberOfTypeInArea++;
          }
        }
      }
    }

    /***
     * Returns true if any component of the target type in the entire PlayableArea has not yet been returned
     * @return true if there are gameComponents of target type that have not been iterated through yet
     */
    @Override
    public boolean hasNext() {
      return numberOfTypeInArea != numberOfTypeSeenSoFar;
    }

    /***
     * Returns the next T in the row of the target type. If the row is finished, returns the next
     * GameComponent in the 0th index of the next row down.
     * @return object casted to the target type
     */
    @Override
    public T next() {
      while (hasNext()) {
        GameComponent nextComponent = areaIterator.next();
        if (type.isInstance(nextComponent)) {
          numberOfTypeSeenSoFar++;
          return type.cast(nextComponent);
        }
      }
      throw new ModelException(iteratorException);
    }
  }
}