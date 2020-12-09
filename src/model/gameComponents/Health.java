package model.gameComponents;

/**
 * @author Priya Rathinavelu, Alex Chao, Katherine Barbano
 * The purpose of this interface is to have common functionality for objects that have a health that
 * updates as the game progresses. As of now, this includes the different towers and enemies that
 * are implemented. These objects need to be able to keep track of their health, and that health
 * needs to be updated as interactions with other components in the game occur. This interface
 * extends the Game Component interface, so any object that implements this interface will also
 * need to override the methods from GameComponent as well. This is because any object that
 * needs to store its health will also need to be categorized as a game component as well for ease
 * of determining interactions within the grid. Using this interface means that any information about
 * storing the health, and updating it as the game goes on, will need to be set.
 */
public interface Health extends GameComponent {

  /**
   * This method is used for setting the initial health of the object
   * @param health an integer representing the initial health
   */
  void setInitialHealth(int health);

  /**
   * This method is used for updating the health of the object and takes in a Damager object which is
   * what inflicts the damage to decrease health
   * @param damager a Damager object that has some integer value of damage that will be deducted from
   *                the health of the object
   */
  void updateHealth(Damager damager);

  /**
   * This method is used for checking if the object is still alive (has enough health)
   * @return a boolean indicating whether the object is still alive
   */
  boolean isAlive();

  /**
   * This method is used to get the current health of the object
   * @return the current health of the object
   */
  int getHealth();


  /**
   * This method is used for getting the score of the object. Whatever object has health will also
   * store some sort of score used for updating the game
   * @return an integer for the score of the object
   */
  int getScore();

  /**
   * This method is helpful for determining the percentage of health lost - this is helpful
   * for the front end with displaying the object and updating it as it loses health
   * @return a double representing the percentage of health left from the starting health
   */
  double getPercentageHealth();

}
