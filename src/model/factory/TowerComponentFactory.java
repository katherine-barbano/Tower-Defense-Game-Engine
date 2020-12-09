package model.factory;


import controller.ConfigObjects.Config;
import controller.ConfigObjects.TowerConfig;
import model.gameComponents.playerGridComponents.Tower;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * @author Alex Chao
 * The purpose of this class is to be the specific instance of the game component factory that is
 * able to create solely tower objects without having to constantly instantiate new towers. This
 * class depends on the GameComponentFactory class because it extends from that abstract class
 * to remove duplication, such as for the createGameComponent method which is what is actually
 * able to create the different towers in this case. This class can be used by creating a new
 * instance of the TowerComponentFactory. The most useful method is the createGameCOmponent method
 * which will allow for the easy creation of all of the different types of towers (such as Shoot,
 * SingleUse, and Sun.
 */

public class TowerComponentFactory extends GameComponentFactory<Tower> {

  private final GameStatusAPI myStatus;

  public TowerComponentFactory(int numberOfRows, int numberOfColumns, int animationSpeed,
      GameStatusAPI status) {
    super(numberOfRows, numberOfColumns, animationSpeed);
    myStatus = status;
  }


  /**
   * This method is used for getting the full class name based on the type of enemy to be created
   * @param type A string read in from a properties file that indicates which type of object needs
   *             to be instantiated
   * @return the full class name of the type of enemy that needs to be created
   */
  @Override
  public String getClassName(String type) {
    return String.format("model.gameComponents.playerGridComponents.%sTower", type);
  }

  /**
   * This method is used for indicating the specific type of classes needed for instantiating
   * the correct object
   * @return an array of Classes needed for reflection
   */
  @Override
  protected Class<?>[] getClasses() {
    return new Class<?>[]{TowerConfig.class, int.class, int.class, int.class};
  }

  /**
   * This method is used for getting the specific arguments for instantiating the object
   * @param config a config object representing all of the information found within the respective
   * properties file
   * @return an array of objects of the actual parameters needed for creating the object, in this case
   * the enemy object
   */
  @Override
  protected Object[] getConstructorArguments(Config config) {
    Object[] constructorArguments = {config, getNumberOfRows(), getNumberOfColumns(),
        getAnimationSpeed()};
    return constructorArguments;
  }

}
