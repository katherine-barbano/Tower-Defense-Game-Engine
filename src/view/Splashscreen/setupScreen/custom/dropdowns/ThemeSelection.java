package view.Splashscreen.setupScreen.custom.dropdowns;

import controller.Customization.CustomLevelComponentHandler;
import java.util.Map;
import view.Splashscreen.setupScreen.custom.popups.CustomThemeSelectionPopup;

/**
 * User can choose between a list of predefined themes or a custom theme.  Constructs a Custom Theme
 * popup when Custom is chosen.
 * @author Heather Grune (hlg20)
 */
public class ThemeSelection extends CustomLevelComponentSelection {

  public ThemeSelection(String css, Map<String, String[]> options, Map<String, String> labels,
      CustomLevelComponentHandler componentHandler) {
    super(css, options, labels, componentHandler);
  }

  @Override
  protected void constructPopup() {
    new CustomThemeSelectionPopup(myCSS, myOptions, myLabels,
        myComponentHandler);
  }
}
