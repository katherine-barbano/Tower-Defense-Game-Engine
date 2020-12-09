package view.Splashscreen.setupScreen.custom.dropdowns;

import java.util.Map;
import view.Splashscreen.SelectionBox;
import view.Splashscreen.setupScreen.custom.CustomGameSelection;

/**
 * LevelNum Selection appears on the custom game selection menu and allows the user to select
 * a number of levels for their custom game.  Dynamically adds/removes level selection boxes upon selection
 * of a new number of levels.
 * @author Heather Grune (hlg20)
 */
public class LevelNumSelection extends SelectionBox {

  private final CustomGameSelection myCustomGameSelection;

  public LevelNumSelection(Map<String, String[]> options, Map<String, String> labels,
      CustomGameSelection customGameSelection) {
    super(options, labels);
    this.setId("level-num-selection");

    myCustomGameSelection = customGameSelection;
  }

  @Override
  protected void setOnSelection(int selectedIndex) {
    super.setOnSelection(selectedIndex);
    int numLevels = Integer.parseInt(mySelection);
    myCustomGameSelection.addLevelSelectionBoxes(numLevels);
  }
}
