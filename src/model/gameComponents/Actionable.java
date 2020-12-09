package model.gameComponents;

import java.util.Map;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.Position;

/**
 * @author Alex Chao, Katherine Barbano
 * The purpose of this interface is to be able to distinguish between game components that need
 * to have some sort of "action" occur and game components without that feature. These shared methods
 * allow for easier common functionality between the different classes that extend this interface.
 * The methods within this interface are helpful for determining when an action needs to occur,
 * if the conditions are met for the action to occur, and most importantly, what needs to actually
 * happen for the action to occur. This interface also extends the Game Component, so every class
 * that extends this Actionable interface will also end up implementing the Game Component interface
 *  and extracting those methods as well. This was done because any object that performs some sort
 *  of special action will need to be a game component so that it is easy for the grid to be able to
 *  keep track of these actions of the various objects and update the grid as new actions occur.
 */
public interface Actionable extends GameComponent {

  /** This method is used for determining whether an action should be occuring for a certain object
   * @return a boolean indicating whether the conditions for being able to do an action have all been
   * met - this can differ based on which type of object is needing to do an action
   */
  boolean checkAction();

  /**
   * This method is used for setting the rate at which each action should occur
   * @param rate an integer representing the rate at which an action needs to occur - this is
   * especially useful for objects that have repeating actions (such as shooters, updating status)
   */
  void setStepsBeforeNextAction(int rate);

  /**
   * This is the method that is what really allows for different actions to occur because objects
   * are able to create different game components or update the status of the game with this method
   * @param currentPosition the current Position of the object
   * @param gameStatus GameStatusAPI that is storing the current status of the game
   * @return a map of new game components stored with their respective position that indiciates
   * whatever action needed to take place (such as creating new game components)
   */
  Map<Position, GameComponent> enactAction(Position currentPosition, GameStatusAPI gameStatus);

}
