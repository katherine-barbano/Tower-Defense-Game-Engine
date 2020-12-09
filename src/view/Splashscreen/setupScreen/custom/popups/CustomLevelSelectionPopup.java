package view.Splashscreen.setupScreen.custom.popups;

import controller.Customization.CustomGameHandler;
import controller.Customization.CustomLevelComponentHandler;
import java.util.HashMap;
import java.util.Map;
import view.Splashscreen.SelectionBox;
import view.Splashscreen.setupScreen.custom.dropdowns.CustomSelection;
import view.Splashscreen.setupScreen.custom.dropdowns.EnemyWaveSelection;
import view.Splashscreen.setupScreen.custom.dropdowns.GridSelection;
import view.Splashscreen.setupScreen.custom.dropdowns.LossConditionSelection;
import view.Splashscreen.setupScreen.custom.dropdowns.ThemeSelection;
import view.Splashscreen.setupScreen.custom.dropdowns.WinConditionSelection;

/**
 * Creates the popup to design a custom level.  The customizable fields in this popup are theme,
 * grid configuration, enemy wave configuration, and win/loss conditions.
 * @author Heather Grune (hlg20)
 */
public class CustomLevelSelectionPopup extends CustomPopupWithTextField {

  private final CustomGameHandler myGameHandler;
  private final CustomLevelComponentHandler myComponentHandler;
  private final Map<String, String[]> myOptions;
  private final int myLevelIndex;
  private CustomSelection myThemeSelection;
  private CustomSelection myGridSelection;
  private CustomSelection myEnemyWaveSelection;
  private SelectionBox myWinConditionSelection;
  private SelectionBox myLossConditionSelection;

  public CustomLevelSelectionPopup(String css, Map<String, String[]> options,
      Map<String, String> labels, int index, CustomGameHandler gameHandler,
      CustomLevelComponentHandler componentHandler) {
    super(css, labels);
    this.myOptions = options;
    this.myGameHandler = gameHandler;
    this.myComponentHandler = componentHandler;
    this.myLevelIndex = index;
    super.setupScene();
    this.myRoot.setId("custom-level-popup");
  }

  @Override
  protected void addUIComponents() {
    addNameField();
    myThemeSelection = new ThemeSelection(myCSS, myOptions, myLabels, myComponentHandler);
    myGridSelection = new GridSelection(myCSS, myOptions, myLabels, myComponentHandler);
    myEnemyWaveSelection = new EnemyWaveSelection(myCSS, myOptions, myLabels, myComponentHandler);
    myWinConditionSelection = new WinConditionSelection(myOptions, myLabels);
    myLossConditionSelection = new LossConditionSelection(myOptions, myLabels);

    myRoot.getChildren().add(myThemeSelection);
    myRoot.getChildren().add(myGridSelection);
    myRoot.getChildren().add(myEnemyWaveSelection);
    myRoot.getChildren().add(myWinConditionSelection);
    myRoot.getChildren().add(myLossConditionSelection);
  }

  @Override
  protected void applyChoices() {
    try {
      Map<String, String> selections = createLevelMap();
      String levelName = myCustomName.getText();
      myGameHandler.makeLevel(selections, levelName, myLevelIndex);
      this.close();
    } catch (Exception e) {
      makeAlert("Custom Level Creation Error", e.getMessage());
    }
  }

  private Map<String, String> createLevelMap() {
    Map<String, String> levelMap = new HashMap<>();
    levelMap.put("WinConditions", myWinConditionSelection.getMySelection());
    levelMap.put("LossConditions", myLossConditionSelection.getMySelection());
    levelMap.put("GridCSV", myGridSelection.getMySelection());

    levelMap.put("WavesCSV", myEnemyWaveSelection.getMySelection());
    levelMap.put("Theme", myThemeSelection.getMySelection());
    return levelMap;
  }

}
