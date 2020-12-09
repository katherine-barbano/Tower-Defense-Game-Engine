package view.Splashscreen.setupScreen.custom.dropdowns;


import controller.Customization.CustomGameHandler;
import controller.Customization.CustomLevelComponentHandler;
import java.util.Map;
import view.Splashscreen.setupScreen.custom.popups.CustomLevelSelectionPopup;

/**
 * Allows the user to choose between predefined levels or create their own level.  Upon selecting
 * custom, a popup is constructed to design the custom level.
 * @author Heather Grune (hlg20)
 */
public class LevelSelection extends CustomSelection {

  private final CustomGameHandler myGameHandler;
  private final CustomLevelComponentHandler myComponentHandler;
  private final int myIndex;
  private final Map<String, String[]> myOptions;

  public LevelSelection(CustomGameHandler gameHandler, CustomLevelComponentHandler componentHandler,
      String css, Map<String, String[]> options, Map<String, String> labels, int number) {
    super(css, options, labels);
    this.setId("level-selection");
    this.myGameHandler = gameHandler;
    this.myComponentHandler = componentHandler;
    this.myIndex = number;
    this.myOptions = options;
    this.myLabel.setText(String.format(myLabel.getText(), number));
  }

  @Override
  protected void constructPopup() {
    new CustomLevelSelectionPopup(myCSS, myOptions, myLabels,
        myIndex,
        myGameHandler, myComponentHandler);
  }

  @Override
  protected String getCustomName() {
    return myGameHandler.getLevelName(myIndex);
  }

}
