package view.Splashscreen.setupScreen.custom.dropdowns;

import controller.Customization.CustomLevelComponentHandler;
import java.util.Map;
import view.Splashscreen.setupScreen.custom.popups.CustomGridSelectionPopup;

/**
 * Construct custom grid configuration selection dropdown.  This dropdown will contain predefined
 * files along with a custom option. When custom is selected create a popup to select a file.
 * @author Heather Grune (hlg20)
 */
public class GridSelection extends CustomLevelComponentSelection {

  public GridSelection(String css, Map<String, String[]> options, Map<String, String> labels,
      CustomLevelComponentHandler componentHandler) {
    super(css, options, labels, componentHandler);
  }

  @Override
  protected void constructPopup() {
    new CustomGridSelectionPopup(myCSS, myLabels, myComponentHandler);
  }
}
