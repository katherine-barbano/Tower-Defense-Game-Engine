package model.gameplay.MVCInteraction.API;

import java.util.Map;
import java.util.function.Consumer;

/***
 * The Game Status API acts as the communication
 * about the status of the game, including all of the information about the game that the player
 * sees when playing and the speed of the game. It allows the user to change the values of the
 * statuses and also supplies information to the controller about the condition of winning and losing
 * the game.
 * @author alex chao
 */
public interface GameStatusAPI {

  /**
   * Resets the values in the game status to their starting values at instantiation
   */
  void reset();

  /**
   * Given an amount, changes the current sun by this amount
   * @param amount
   */
  void changeSunAmount(int amount);

  /**
   * Given a double, multiplies the current game speed by this amount
   * @param modifier
   */
  void changeGameSpeed(double modifier);

  /**
   * This method takes in a Consumer and applies the current sun as the parameter. This is used by
   * the front end to update the sun while encapsulating the actual amount.
   * @param statusUpdater
   */
  void applySun(Consumer<Integer> statusUpdater);

  /**
   * This method takes in a Consumer and applies the current time as the parameter. This is used by
   * the front end to update the time while encapsulating the actual amount.
   * @param statusUpdater
   */
  void applyTime(Consumer<Integer> statusUpdater);

  /**
   * This method takes in a Consumer and applies the current points as the parameter. This is used by
   * the front end to update the points while encapsulating the actual amount.
   * @param statusUpdater
   */
  void applyPoints(Consumer<Integer> statusUpdater);

  /**
   * This method takes in a Consumer and applies the current progress as the parameter. This is used by
   * the front end to update the progress while encapsulating the actual amount.
   * @param statusUpdater
   */
  void applyProgress(Consumer<Double> statusUpdater);

  /**
   * Gets the current animation speed
   * @return
   */
  int getAnimationSpeed();

  /**
   * Returns true if the current sun amount is less than the target
   * @param target
   * @return
   */
  boolean sunLessThan(int target);

  /**
   * Returns true if the current point amount is greater than points
   * @param points
   * @return
   */
  boolean pointsGreaterThan(int points);

  /**
   * Returns true if the amount of sun present is able to cover for the cost passed in
   * @param cost
   * @return
   */
  boolean validateCost(int cost);

  /**
   * Updates all of the information that should change at every step in the game.
   */
  void update();

  /**
   * Given a double, sets the current progress of the game to this value.
   * @param progress
   */
  void updateProgress(double progress);

  /**
   * Returns true if the progress has reached one
   * @return
   */
  boolean progressComplete();

  Map<String, String> getStatus();

  /**
   * Given an amount of points, adds this to the current points
   * @param increaseScore
   */
  void updatePoints(int increaseScore);

  /**
   * Gets the time that is currently stored in the status
   * @return
   */
  int getTimeElapsed();

}
