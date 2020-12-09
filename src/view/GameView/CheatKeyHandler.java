package view.GameView;

import controller.GamePlay.Timing;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This class handles all of the cheat keys that the user can click while playing a game
 * @author alex chao
 */
public class CheatKeyHandler {

  private final Map<KeyCode, Runnable> myKeyActions = new HashMap<>();
  private final GameStatusAPI myGame;
  private final Timing myTiming;

  public CheatKeyHandler(GameStatusAPI game, Timing timing) {
    myGame = game;
    myTiming = timing;
    makeKeyActionsMap();
  }

  private void makeKeyActionsMap() {
    addKeyInput(KeyCode.SPACE, this::pausePlayGame);
    addKeyInput(KeyCode.RIGHT, this::increaseSun);
    addKeyInput(KeyCode.UP, this::increaseGameSpeed);
    addKeyInput(KeyCode.DOWN, this::decreaseGameSpeed);
    addKeyInput(KeyCode.R, this::resetLevel);
  }

  private void increaseGameSpeed() {
    myGame.changeGameSpeed(2);
  }

  private void decreaseGameSpeed() {
    myGame.changeGameSpeed(.5);
  }

  private void resetLevel() {
    myTiming.reset();
  }

  private void increaseSun() {
    myGame.changeSunAmount(50);
  }

  private void pausePlayGame() {
    myTiming.togglePause();
  }

  private void addKeyInput(KeyCode code, Runnable executable) {
    myKeyActions.put(code, executable);
  }

  /**
   * Performs the action associated with the input passed in
   *
   * @param code a keyboard input
   */
  public void handleKeyInput(KeyCode code) {
    myKeyActions.getOrDefault(code, () -> {
    }).run();
  }
}
