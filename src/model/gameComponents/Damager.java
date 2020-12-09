package model.gameComponents;

/**
 * @author Priya Rathinavelu, Katherine Barbano, Alex Chao
 * The purpose of this interface is to have shared commonality for any game components that inflict
 * damage on other game components. This is most useful for the enemy and projectile classes because
 * those are the two objects that actually take away damage from other objects. As a result, by
 * having this shared interface, we are able to organize which components can do damage to others.
 * This also makes it easy for when new objects are added that may also do damage somehow to other
 * components. This interface also extends GameComponent so that anything that implements this
 * interface will also need to override the methods from GameComponent as well. This was done because
 * anything that is able to do damage to other objects should also be categorized as a game component
 * so that it is easy to better understand interactions within the grid.
 */
public interface Damager extends GameComponent {

  /**
   * This method is used to get the stored damage that the object inflicts on other components
   * @return an integer representing the damage that the object does
   */
  int getDamage();
}
