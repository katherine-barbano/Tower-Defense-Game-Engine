package controller.Managers;

import controller.Alerts;
import controller.Conditions.WinLossCondition;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * Handles the winning and losing of a level based off of conditions that are dynamically created
 * from a list of the names of the conditions.
 *
 * @author Megan Richards and Alex Chao
 */
public class ConditionsManager {

  private final ArrayList<WinLossCondition> winConditions = new ArrayList<>();
  private final ArrayList<WinLossCondition> lossConditions = new ArrayList<>();
  private final Alerts alerts = new Alerts();

  /**
   * handles the condition checking for a given level
   * @param levelWinConditions
   * @param levelLossConditions
   */
  public ConditionsManager(String[] levelWinConditions, String[] levelLossConditions) {
    for (String condition : levelLossConditions) {
      lossConditions.add((createCondition("Loss", condition)));
    }
    for (String condition : levelWinConditions) {
      winConditions.add((createCondition("Win", condition)));
    }
  }

  /**
   * iterates the lose conditions and checks them on the game
   * @param game - game to check conditions on
   * @param gameStatus - status used for some conditions
   * @return if the level is lost
   */
  public boolean checkLevelLost(GameControlAPI game, GameStatusAPI gameStatus) {
    for (WinLossCondition lossCondition : lossConditions) {
      if (lossCondition.checkCondition(game, gameStatus)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param game - game to check conditions on
   * @param gameStatus - status used for some conditions
   * @return if the level is won
   */
  public boolean checkLevelWon(GameControlAPI game, GameStatusAPI gameStatus) {
    for (WinLossCondition winCondition : winConditions) {
      if (winCondition.checkCondition(game, gameStatus)) {
        return true;
      }
    }
    return false;
  }

  private WinLossCondition createCondition(String type, String name) {
    Class<?> condition;
    WinLossCondition winLossCondition = null;
    try {
      condition = Class.forName("controller.Conditions." + type + "." + name);
      winLossCondition = (WinLossCondition) condition
          .getConstructor()
          .newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      alerts.makeAlert(e.getMessage());
    }
    return winLossCondition;
  }

//    private void setUpGameConditions(String variationSelection)
//      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//    if (winConditions.size() > 0) {
//      winConditions.clear();
//    }
//    ResourceBundle variationProperties = resources.getVariationProperties(variationSelection);
//    String[] winConditionClassNames = variationProperties.getStringArray("WinCondition");
//    String[] lossConditionClassNames = variationProperties.getStringArray("LossCondition");
//    for (String winClassName : winConditionClassNames) {
//      Class<?> a = Class.forName(winClassName);
//      Constructor c = a.getConstructor();
//      WinLossCondition w = (WinLossCondition) c.newInstance();
//      winConditions.add(w);
//    }
//    for (String lossClassName : lossConditionClassNames) {
//      Class<?> a = Class.forName(lossClassName);
//      Constructor c = a.getConstructor();
//      WinLossCondition l = (WinLossCondition) c.newInstance();
//      lossConditions.add(l);
//    }
//  }
}
