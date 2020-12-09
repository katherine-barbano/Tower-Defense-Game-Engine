package view.Splashscreen.setupScreen;

import controller.Customization.CustomGameHandler;
import java.util.Map;
import view.Splashscreen.setupScreen.custom.dropdowns.CustomSelection;

/**
 * GameSelection creates the dropdown menu and label for selecting a Game.  It extends the CustomSelection
 * abstract class. When custom is chosen from the dropdown, it adds the custom options menu to the
 * setup screen.
 * @author Heather Grune (hlg20)
 */
public class GameSelection extends CustomSelection {

  private final SetupScreen mySetupScreen;
  private final CustomGameHandler myGameHandler;

  public GameSelection(SetupScreen setupScreen, String css, Map<String, String[]> options,
      Map<String, String> labels, CustomGameHandler gameHandler) {
    super(css, options, labels);
    this.setId("game-selection");
    this.mySetupScreen = setupScreen;
    this.myGameHandler = gameHandler;
  }

  @Override
  protected void constructPopup() {
    mySetupScreen.addCustomOptionsToScreen();
  }

  @Override
  protected String getCustomName() {
    return myGameHandler.getGameName();
  }

}
