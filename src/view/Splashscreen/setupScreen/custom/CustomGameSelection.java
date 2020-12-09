package view.Splashscreen.setupScreen.custom;

import controller.Customization.CustomGameHandler;
import controller.Customization.CustomLevelComponentHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.Splashscreen.SelectionBox;
import view.Splashscreen.setupScreen.SetupScreenAlertMessage;
import view.Splashscreen.setupScreen.custom.dropdowns.LevelNumSelection;
import view.Splashscreen.setupScreen.custom.dropdowns.LevelSelection;

/**
 * CustomGameSelection creates the menu on the setupScreen when the user chooses to make a custom
 * Game.  It includes dynamic level selection boxes with a custom level selection boxes.
 * @author Heather Grune (hlg20)
 */
public class CustomGameSelection extends VBox {

  private static final String GAME_NAME_KEY = "CustomGameName";
  private final Map<String, String> myLabels;
  private final CustomGameHandler myGameHandler;
  private final CustomLevelComponentHandler myComponentHandler;
  private final Map<String, String[]> myOptions;
  private final String myCSS;
  private final List<LevelSelection> myLevelDropdowns;
  private HBox myNameInputBar;
  private TextField myNameInput;
  private SelectionBox levelNumSelection;
  private VBox myDynamicDropdownSection;
  private Button myCustomGameButton;

  public CustomGameSelection(String css, Map<String, String[]> options,
      Map<String, String> dropdownLabels, CustomGameHandler gameHandler,
      CustomLevelComponentHandler componentHandler) {
    this.myGameHandler = gameHandler;
    this.myComponentHandler = componentHandler;
    this.myLabels = dropdownLabels;
    this.myOptions = options;
    this.myLevelDropdowns = new ArrayList<>();
    this.myCSS = css;

    createUIComponents();
    addComponentsToRoot();
    this.getStyleClass().add("splash-screen");
    this.setId("custom-game-selection");
  }

  private void createUIComponents() {
    createNameInputBar();

    this.levelNumSelection = new LevelNumSelection(myOptions, myLabels, this);
    this.myDynamicDropdownSection = new VBox();
    this.myDynamicDropdownSection.setSpacing(5);
    this.myCustomGameButton = new Button(myLabels.get("CustomGameButton"));
    this.myCustomGameButton.setOnAction(event -> createCustomGame());
    this.myCustomGameButton.setId("custom-game-button");
  }

  private void createNameInputBar() {
    this.myNameInputBar = new HBox();
    Text nameInputLabel = new Text(myLabels.get(GAME_NAME_KEY));
    this.myNameInput = new TextField();
    this.myNameInputBar.setId("custom-game-name");
    this.myNameInputBar.getChildren().addAll(nameInputLabel, myNameInput);
  }

  private void addComponentsToRoot() {
    this.getChildren()
        .addAll(myNameInputBar, levelNumSelection, myDynamicDropdownSection, myCustomGameButton);

  }

  /**
   * Add Level Selection dropdowns based on the number of selected levels.
   * @param numLevels number of selected levels
   */
  public void addLevelSelectionBoxes(int numLevels) {
    clearLevelDropdowns();
    for (int level = 0; level < numLevels; level++) {
      LevelSelection newLevel = new LevelSelection(myGameHandler, myComponentHandler, myCSS,
          myOptions, myLabels, level + 1);
      this.myLevelDropdowns.add(newLevel);
    }
    myDynamicDropdownSection.getChildren().addAll(myLevelDropdowns);
  }

  private void clearLevelDropdowns() {
    myDynamicDropdownSection.getChildren().removeAll(myLevelDropdowns);
    myLevelDropdowns.clear();
  }

  /**
   * Get the number of levels chosen in the level number selection dropdown.
   * @return number of levels chosen by user
   */
  public int getNumChosenLevels() {
    return myLevelDropdowns.size();
  }

  private void createCustomGame() {
    try {
      List<String> selections = createLevelList();
      myGameHandler.makeGame(selections, myNameInput.getText());
    } catch (Exception e) {
      new SetupScreenAlertMessage("Custom Game Creation Error",
          e.getMessage());
    }
  }

  private List<String> createLevelList() {
    List<String> levelList = new ArrayList<>();
    for (LevelSelection levelDropdown : myLevelDropdowns) {
      levelList.add(levelDropdown.getMySelection());
    }
    return levelList;
  }


}
