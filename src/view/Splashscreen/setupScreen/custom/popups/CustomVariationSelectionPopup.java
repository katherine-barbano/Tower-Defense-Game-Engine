package view.Splashscreen.setupScreen.custom.popups;

import controller.Customization.CustomLevelComponentHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.Splashscreen.SelectionBox;
import view.Splashscreen.setupScreen.custom.dropdowns.LossConditionSelection;
import view.Splashscreen.setupScreen.custom.dropdowns.WinConditionSelection;

/**
 * Should have been deleted along with Variation Selection.  We decided to allow the user to choose
 * win/loss conditons rather than variations.
 */
public class CustomVariationSelectionPopup extends CustomPopupWithTextField {

  private final CustomLevelComponentHandler myComponentHandler;
  private final Map<String, String[]> myOptions;
  private SelectionBox myWinConditionSelector;
  private SelectionBox myLossConditionSelector;

  public CustomVariationSelectionPopup(String css, Map<String, String[]> options,
      Map<String, String> labels,
      CustomLevelComponentHandler componentHandler) {
    super(css, labels);
    this.myComponentHandler = componentHandler;
    this.myOptions = options;
    super.setupScene();
  }

  @Override
  protected void addUIComponents() {
    addNameField();
    this.myWinConditionSelector = new WinConditionSelection(myOptions, myLabels);
    this.myLossConditionSelector = new LossConditionSelection(myOptions, myLabels);

    myRoot.getChildren().add(myWinConditionSelector);
    myRoot.getChildren().add(myLossConditionSelector);
  }

  @Override
  protected void applyChoices() {
    try {
      String levelName = myCustomName.getText();
      List<String> selectedWinConditions = new ArrayList<>(
          List.of(myWinConditionSelector.getMySelection()));
      List<String> selectedLossConditions = new ArrayList<>(
          List.of(myLossConditionSelector.getMySelection()));
      myComponentHandler
          .makeCustomVariation(selectedWinConditions, selectedLossConditions, levelName);
      this.close();
    } catch (Exception e) {
      makeAlert("Custom Variation Error", e.getMessage());
    }
  }
}
