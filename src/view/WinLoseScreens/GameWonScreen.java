package view.WinLoseScreens;

import java.util.Map;

/**
 * Extends the abstract LevelScreen API to display the Game winning message to the player (accesses
 * message from properties files)
 * @author Heather Grune (hlg20)
 */
public class GameWonScreen extends LevelScreen {

  public GameWonScreen(Map<String, String> text) {
    super(text);
  }
}
