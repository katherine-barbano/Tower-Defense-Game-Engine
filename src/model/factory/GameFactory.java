package model.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ResourceBundle;
import model.gameComponents.SingleState;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import model.gameplay.MVCInteraction.concreteModel.GameControl;
import model.gameplay.gameplayResources.ModelException;

/***
 * Used by controller to create a new subclass of GameControl (either path or grid implementation).
 * Uses reflection to do this.
 *
 * @author Katherine Barbano
 */
public class GameFactory {

  public static final String GAME_CLASS_NAME_PREFIX = "gameClassNamePrefix";
  public static final String GAME_CLASS_NAME_SUFFIX = "gameClassNameSuffix";
  public static final String INVALID_GAME_VARIATION_EXCEPTION = "invalidGameVariationException";

  private final ResourceBundle modelProperties;
  private final String invalidMessage;

  /***
   * Retrieve exception messages to use in this class.
   */
  public GameFactory() {
    modelProperties = ResourceBundle.getBundle(GameControl.MODEL_RESOURCE_PATH);
    invalidMessage = modelProperties.getString(INVALID_GAME_VARIATION_EXCEPTION);
  }

  /***
   * Tries to instantiate a Game with name "[gameVariation]GameControl"
   * @param gameVariation String for "Path" or "Grid"
   * @param initialPlayerBoard List<List<SingleState>>
   * @param numberOfRows int
   * @param numberOfColumns int
   * @param gameStatus GameStatusAPI
   * @return GameControl object
   */
  public GameControl createGame(String gameVariation, List<List<SingleState>> initialPlayerBoard,
      int numberOfRows, int numberOfColumns,
      GameStatusAPI gameStatus) {
    try {
      //code referenced from https://java2blog.com/invoke-constructor-using-reflection-java/ provided on course website
      String fullClassName = formatInputString(gameVariation);
      Class<?> cl = Class.forName(fullClassName);
      Class<?>[] type = {List.class, int.class, int.class, GameStatusAPI.class};
      Constructor<?> cons = cl.getConstructor(type);
      Object[] constructorArguments = {initialPlayerBoard, numberOfRows, numberOfColumns,
          gameStatus};
      return (GameControl) cons.newInstance(constructorArguments);
    } catch (Exception e) {
      throw new ModelException(invalidMessage, e);
    }
  }

  private String formatInputString(String inputSubclass) {
    String lowercase = inputSubclass.toLowerCase();
    String correctCaseSubclass = makeFirstLetterUppercase(lowercase);
    String gameClassNamePrefix = modelProperties.getString(GAME_CLASS_NAME_PREFIX);
    String gameClassNameSuffix = modelProperties.getString(GAME_CLASS_NAME_SUFFIX);
    return String.format("%s%s%s", gameClassNamePrefix, correctCaseSubclass, gameClassNameSuffix);
  }

  private String makeFirstLetterUppercase(String inputSubclass) {
    try {
      String firstLetter = inputSubclass.substring(0, 1).toUpperCase();
      String restOfWord = inputSubclass.substring(1);
      return String.format("%s%s", firstLetter, restOfWord);
    } catch (Exception e) {
      throw new ModelException(invalidMessage, e);
    }
  }

}
