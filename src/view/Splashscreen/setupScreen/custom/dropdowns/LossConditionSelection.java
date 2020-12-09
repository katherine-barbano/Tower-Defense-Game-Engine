package view.Splashscreen.setupScreen.custom.dropdowns;

import java.util.Map;
import view.Splashscreen.SelectionBox;

/**
 * The LossConditionSelection box appears on the Custom level popup with a defined list of possible
 * loss conditions.
 * @author Heather Grune (hlg20)
 */
public class LossConditionSelection extends SelectionBox {

  public LossConditionSelection(Map<String, String[]> options, Map<String, String> labels) {
    super(options, labels);
    this.setId("loss-condition-selection");
  }
}
