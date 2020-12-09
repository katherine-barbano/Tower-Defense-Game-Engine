package view.Splashscreen.setupScreen.custom.dropdowns;

import controller.Customization.CustomLevelComponentHandler;
import java.util.Map;
import view.Splashscreen.setupScreen.custom.popups.CustomEnemyWaveSelectionPopup;

/**
 * Construct custom enemy wave selection dropdown.  This dropdown will contain predefined enemy wave
 * files along with a custom option. When custom is selected create a popup to select a file.
 * @author Heather Grune (hlg20)
 */
public class EnemyWaveSelection extends CustomLevelComponentSelection {

  public EnemyWaveSelection(String css, Map<String, String[]> options, Map<String, String> labels,
      CustomLevelComponentHandler componentHandler) {
    super(css, options, labels, componentHandler);
  }

  @Override
  protected void constructPopup() {
    new CustomEnemyWaveSelectionPopup(myCSS, myLabels, myComponentHandler);
  }

}
