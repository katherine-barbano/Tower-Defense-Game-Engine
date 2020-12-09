package model.gameplay.collision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.factory.CollisionFactory;
import model.gameComponents.GameComponent;
import model.gameplay.gameplayResources.Position;
import model.gameplay.playableArea.IIterator;
import model.gameplay.playableArea.PlayableArea;

/***
 * Detects and creates collisions between any two specified Classes between the PlayableAreas.
 * Can deal with collisions occurring between both playerArea and enemyArea (the collision does
 * not have to be confined to occurring in just one of these). Creates the collisions using a
 * collision factory.
 *
 * @author Katherine Barbano
 */
public class CollisionDetector {

  public static final int OBJ1_START_INDEX = 0;
  public static final int OBJ2_START_INDEX = 1;
  public static final int OBJ1_DIAGONAL_INDEX = 2;
  public static final int OBJ2_DIAGONAL_INDEX = 3;

  private final CollisionFactory collisionFactory;

  /***
   * Creates a collision factory used to instantiate new collisions throughout the class
   */
  public CollisionDetector() {
    this.collisionFactory = new CollisionFactory();
  }

  /***
   * Returns a set of collision objects for all collisions between the two target classes that
   * are currently occurring in either of the two PlayableAreas. This also detects collisions
   * occurring between the PlayableAreas, if one Class is in enemyArea and the other is in
   * playerArea. This also detects collisions at ANY point of the colliding process, not just
   * at the initial time when the two objects first collide.
   *
   * Assumes object types are all contained in the same PlayableArea - eg all projectiles must
   * be in the same Playable Area.
   * @param object1Type First class type to create the collisions between
   * @param object2Type Second class type to create the collisions between
   * @param object1Area PlayableArea that contains object1Type
   * @param object2Area PlayableArea that contains object2Type
   * @return Set of Collisions
   */
  public Set<Collision> createCollisions(Class object1Type, Class object2Type,
      PlayableArea object1Area, PlayableArea object2Area) {
    Set<Collision> collisions = new HashSet<>();
    IIterator<GameComponent> object1Iterator = object1Area.createTypeIterator(object1Type);
    while (object1Iterator.hasNext()) {
      GameComponent object1 = object1Iterator.next();
      IIterator<GameComponent> object2Iterator = object2Area.createTypeIterator(object2Type);
      while (object2Iterator.hasNext()) {
        GameComponent object2 = object2Iterator.next();
        if (collisionIsDetected(object1, object2, object1Area, object2Area)) {
          Collision newCollision = collisionFactory
              .createCollision(object1Type, object2Type, object1, object2, object1Area,
                  object2Area);
          collisions.add(newCollision);
        }
      }
    }
    return collisions;
  }

  /***
   * In the returned list, index 0 is start Position of object 1, index 1 is start Position of object 2,
   * index 2 is opposite diagonal Position of object 1, index 3 is opposite diagonal Position of object 2
   */
  private List<Position> convertMinimalGridPosition(GameComponent object1, GameComponent object2,
      PlayableArea object1Area, PlayableArea object2Area) {
    List<Position> convertedStartPositions = new ArrayList<>();
    boolean object1AreaIsSmaller = object1Area.getNumberOfRows() < object2Area.getNumberOfRows()
        && object1Area.getNumberOfColumns() < object2Area.getNumberOfColumns();
    boolean object2AreaIsSmaller = object2Area.getNumberOfRows() < object1Area.getNumberOfRows()
        && object2Area.getNumberOfColumns() < object1Area.getNumberOfColumns();
    convertedStartPositions.add(object1Area.getPositionOfGameComponent(object1));
    convertedStartPositions.add(object2Area.getPositionOfGameComponent(object2));
    convertedStartPositions.add(
        getDiagonalPosition(convertedStartPositions.get(0), object1.getWidth(),
            object1.getHeight()));
    convertedStartPositions.add(
        getDiagonalPosition(convertedStartPositions.get(1), object2.getWidth(),
            object2.getHeight()));
    if (object1AreaIsSmaller) {
      convertSmallToLargePosition(convertedStartPositions, object1, object1Area, object2Area, 0);
    } else if (object2AreaIsSmaller) {
      convertSmallToLargePosition(convertedStartPositions, object2, object2Area, object1Area, 1);
    }
    return convertedStartPositions;
  }

  private Position getDiagonalPosition(Position startPosition, int objectWidth, int objectHeight) {
    int endRow = startPosition.getRow() + objectHeight;
    int endColumn = startPosition.getColumn() + objectWidth;
    return new Position(endRow, endColumn);
  }

  private void convertSmallToLargePosition(List<Position> convertedStartPositions,
      GameComponent smallAreaComponent, PlayableArea smallArea, PlayableArea bigArea,
      int listIndex) {
    Position unconvertedStartSmallArea = smallArea.getPositionOfGameComponent(smallAreaComponent);
    int rowConversionFactor = getRowConversionFactor(bigArea, smallArea);
    int columnConversionFactor = getColumnConversionFactor(bigArea, smallArea);
    int convertedRow = unconvertedStartSmallArea.getRow() * rowConversionFactor;
    int convertedColumn = unconvertedStartSmallArea.getColumn() * columnConversionFactor;
    convertedStartPositions.set(listIndex, new Position(convertedRow, convertedColumn));

    int object1Width =
        smallAreaComponent.getWidth() * getColumnConversionFactor(bigArea, smallArea);
    int object1Height = smallAreaComponent.getHeight() * getRowConversionFactor(bigArea, smallArea);
    convertedStartPositions.set(listIndex + 2,
        getDiagonalPosition(convertedStartPositions.get(0), object1Width, object1Height));
  }

  private int getRowConversionFactor(PlayableArea bigArea, PlayableArea smallArea) {
    return bigArea.getNumberOfRows() / smallArea.getNumberOfRows();
  }

  private int getColumnConversionFactor(PlayableArea bigArea, PlayableArea smallArea) {
    return bigArea.getNumberOfColumns() / smallArea.getNumberOfColumns();
  }

  private boolean collisionIsDetected(GameComponent object1, GameComponent object2,
      PlayableArea object1Area, PlayableArea object2Area) {
    //referenced https://www.baeldung.com/java-check-if-two-rectangles-overlap
    List<Position> convertedStartAndDiagonalPositions = convertMinimalGridPosition(object1, object2,
        object1Area, object2Area);
    Position object1Start = convertedStartAndDiagonalPositions.get(OBJ1_START_INDEX);
    Position object2Start = convertedStartAndDiagonalPositions.get(OBJ2_START_INDEX);
    Position object1Diagonal = convertedStartAndDiagonalPositions.get(OBJ1_DIAGONAL_INDEX);
    Position object2Diagonal = convertedStartAndDiagonalPositions.get(OBJ2_DIAGONAL_INDEX);
    boolean noYIntersection = getTopRightY(object1Start) >= getBottomLeftY(object2Diagonal)
        || getBottomLeftY(object1Diagonal) <= getTopRightY(object2Start);
    boolean noXIntersection = getTopRightX(object1Diagonal) <= getBottomLeftX(object2Start)
        || getBottomLeftX(object1Start) >= getTopRightX(object2Diagonal);
    return (!noYIntersection && !noXIntersection && object1 != object2);
  }

  private int getTopRightX(Position diagonal) {
    return diagonal.getColumn();
  }

  private int getBottomLeftX(Position start) {
    return start.getColumn();
  }

  private int getTopRightY(Position start) {
    return start.getRow();
  }

  private int getBottomLeftY(Position diagonal) {
    return diagonal.getRow();
  }
}
