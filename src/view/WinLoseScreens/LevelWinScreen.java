package view.WinLoseScreens;

import java.util.Map;

/**
 * Extends the abstract LevelScreen API to display the level winning message to the player (accesses
 * message from properties files)
 * @author Heather Grune (hlg20)
 */
public class LevelWinScreen extends LevelScreen {

  public LevelWinScreen(Map<String, String> text) {
    super(text);
  }


}
