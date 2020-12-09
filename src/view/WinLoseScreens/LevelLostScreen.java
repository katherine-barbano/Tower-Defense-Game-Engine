package view.WinLoseScreens;

import java.util.Map;

/**
 * Extends the abstract LevelScreen API to display the Level losing message to the player (accesses
 * message from properties files)
 * @author Heather Grune (hlg20)
 */
public class LevelLostScreen extends LevelScreen {

  public LevelLostScreen(Map<String, String> text) {
    super(text);
  }
}
