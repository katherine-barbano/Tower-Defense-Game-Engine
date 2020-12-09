package view.Splashscreen.setupScreen.custom.dropdowns;

import controller.Customization.CustomLevelComponentHandler;
import java.util.Map;
import view.Splashscreen.setupScreen.custom.popups.CustomVariationSelectionPopup;

/**
 * Variation should have been deleted -- was used to choose between predefined variations before we
 * chose to implement separate win/loss conditions.
 */
public class VariationSelection extends CustomLevelComponentSelection {

  public VariationSelection(String css, Map<String, String[]> options, Map<String, String> labels,
      CustomLevelComponentHandler componentHandler) {
    super(css, options, labels, componentHandler);
  }

  @Override
  protected void constructPopup() {
    new CustomVariationSelectionPopup(myCSS, myOptions,
        myLabels, myComponentHandler);
  }
}
