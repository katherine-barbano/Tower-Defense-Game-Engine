package controller.GamePlay;

/**
 * Defines behavior for something that can be paused. Is used by the GameControlPanel to pause or
 * play a level
 */
public interface Timing {

  /**
   * Pauses or plays
   */
  void togglePause();

  /**
   * Resets the timing to its original value
   */
  void reset();
}
