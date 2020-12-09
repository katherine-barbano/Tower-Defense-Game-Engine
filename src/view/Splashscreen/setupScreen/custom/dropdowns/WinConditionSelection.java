package view.Splashscreen.setupScreen.custom.dropdowns;

import java.util.Map;
import view.Splashscreen.SelectionBox;

/**
 * The WinConditionSelection box appears on the Custom level popup with a defined list of possible
 * win conditions.
 * @author Heather Grune (hlg20)
 */
public class WinConditionSelection extends SelectionBox {

  public WinConditionSelection(Map<String, String[]> options, Map<String, String> labels) {
    super(options, labels);
    this.setId("win-condition-selection");
  }
}
