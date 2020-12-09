package model.factory;

import controller.ConfigObjects.Config;
import java.lang.reflect.Constructor;
import model.gameComponents.GameComponent;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.gameplayResources.ModelException;

/**
 * @author Alex Chao, Priya Rathinavelu
 * The purpose of this class is to abstractly define a design pattern, specifically the design pattern
 * of the factory which is helpful for removing duplication in instantiation and intialization. This
 * class is important for creating the different types of gameComponentFactories that end up
 * intializing their own objects in the game. This class takes in an object that extends the Game
 * Component which is helpful for the method for creating the Game Component, because the different
 * types of factories that extend this abstract class will create different objects for their
 * game components. This was used so that we could remove duplication in the specific implementations
 * of the factories. This class can be used by creating a subclass of it that is needed for
 * creating a game component similar to how this factory does so. This subclass will need to indicate
 * the class it needs to use to get the right type of object as well. Once those methods are overridded
 * to reflect the new classes needed within the subclass implementation of this factory, it can be
 * used to create certain new game components.
 */
public abstract class GameComponentFactory<T extends GameComponent> {

  private final int numberOfRows;
  private final int numberOfColumns;
  private final int animationSpeed;
  private GameStatusAPI myGameStatus;

  public GameComponentFactory(int numberOfRows, int numberOfColumns, int animationSpeed) {
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.animationSpeed = animationSpeed;
  }


  public GameComponentFactory(int numberOfRows, int numberOfColumns, int animationSpeed,
      GameStatusAPI
          currentStatus) {
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.animationSpeed = animationSpeed;
    myGameStatus = currentStatus;
  }


  /**
   * This method is used for creating the game component based on a config object passed in
   * @param config a Config object that stores information found in properties files for the objects
   * @return the correct Object created from the Config object - this depends on each subclass
   * implementation of this abstract class
   */
  public T createGameComponent(Config config) {
    try {
      //code referenced from https://java2blog.com/invoke-constructor-using-reflection-java/ provided on course website
      String fullClassName = getClassName(config.getType());
      Class<?> cl = Class.forName(fullClassName);
      Class<?>[] types = getClasses();
      Constructor<?> cons = cl.getConstructor(types);
      Object[] constructorArguments = getConstructorArguments(config);
      return (T) cons.newInstance(constructorArguments);
    } catch (Exception e) {
      throw new ModelException("Subclass not correct for reflection");
    }
  }

  /**
   * This abstract method is used for getting the right type of classes needed for the constructor
   * of the object being created from the create game component method
   * @return the class of the object needing to be created
   */
  protected abstract Class<?>[] getClasses();

  /**
   * This method is used for getting the specific constructor arguments needed for initializing the
   * game component to be created
   * @param config a config object representing all of the information found within the respective
   * properties file
   * @return an array of Objects used for creating the specific game component with those parameters
   */
  protected abstract Object[] getConstructorArguments(Config config);

  /**
   * This method is used for getting the specific class name
   * @param type A string read in from a properties file that indicates which type of object needs
   *             to be instantiated
   * @return a string that has the correct class name for the object to be created
   */
  public abstract String getClassName(String type);

  protected int getAnimationSpeed() {
    return animationSpeed;
  }

  protected int getNumberOfRows() {
    return numberOfRows;
  }

  protected int getNumberOfColumns() {
    return numberOfColumns;
  }

  protected GameStatusAPI getGameStatus() {
    return myGameStatus;
  }

}
