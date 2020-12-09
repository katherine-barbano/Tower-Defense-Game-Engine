package model.gameplay.gameplayResources;

import java.util.ResourceBundle;
import model.gameplay.MVCInteraction.concreteModel.GameControl;

/***
 * Stores the row and column associated with a single Position in the PlayableArea.
 * This is used to simplify the process of passing around row/col pairs throughout gameplay package.
 *
 * @author Katherine Barbano
 */
public class Position {

  public static final String POSITION_EQUALS_EXCEPTION = "positionEqualsException";

  private final int row;
  private final int column;

  /***
   * Creates new position. These values cannot be changed, a new position should be made
   * if you want to change the row or column.
   * @param row int row final
   * @param column int column final
   */
  public Position(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /***
   * Gets the row associated with this position
   * @return row
   */
  public int getRow() {
    return row;
  }

  /***
   * Gets the column associated with this position
   * @return column
   */
  public int getColumn() {
    return column;
  }

  /***
   * Overrides Object's equals method. Used to determine if two Positions are the same.
   * @param otherPosition Position to compare this position to
   * @return true if the positions are the same
   */
  @Override
  public boolean equals(Object otherPosition) {
    try {
      Position otherPositionCasted = (Position) otherPosition;
      boolean rowsEqual = otherPositionCasted.getRow() == row;
      boolean columnsEqual = otherPositionCasted.getColumn() == column;
      return rowsEqual && columnsEqual;
    } catch (Exception e) {
      ResourceBundle modelResources = ResourceBundle.getBundle(GameControl.MODEL_RESOURCE_PATH);
      String message = modelResources.getString(POSITION_EQUALS_EXCEPTION);
      throw new ModelException(message);
    }
  }

  /***
   * Overrides Object's toString method. Used only for debugging help.
   * @return String representation of Position
   */
  @Override
  public String toString() {
    return String.format("%d, %d", row, column);
  }

}
