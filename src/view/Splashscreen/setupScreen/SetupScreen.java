package view.Splashscreen.setupScreen;

import controller.Customization.CustomGameHandler;
import controller.Customization.CustomLevelComponentHandler;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import view.Splashscreen.SelectionBox;
import view.Splashscreen.SetupScreenAPI;
import view.Splashscreen.setupScreen.custom.CustomGameSelection;

/**
 * SetupScreen is the second splash screen in our series of two splash screens.  This screen
 * implements the SetupScreenAPI.  The SetupScreen allows the user to enter their name and choose
 * a game to play, or even create their own customized game (with customized levels, themes, and
 * configurations).
 * @author Heather Grune (hlg20)
 */
public class SetupScreen implements SetupScreenAPI {

  public static final String TITLE_KEY = "GameTitle";
  public static final String NAME_LABEL_KEY = "NameLabel";
  private final String myButtonText;
  private final String myCSS;

  private final Map<String, String> mySetupScreenProperties;
  private final Map<String, String[]> mySetupScreenOptions;
  private final CustomGameHandler myGameHandler;
  private final CustomLevelComponentHandler myComponentHandler;
  private Text myTitle;
  private VBox myRoot;
  private HBox myPlayerNameBar;
  private TextField myPlayerName;
  private HBox myMainRow;
  private SelectionBox myGameSelection;
  private VBox myCustomGameSelection;
  private Button myStartButton;

  public SetupScreen(Map<String, String> setupScreenProperties,
      Map<String, String[]> dropdownOptions,
      CustomGameHandler gameHandler, CustomLevelComponentHandler componentHandler) {
    this.mySetupScreenProperties = setupScreenProperties;
    this.mySetupScreenOptions = dropdownOptions;
    this.myGameHandler = gameHandler;
    this.myComponentHandler = componentHandler;
    String myGameLabel = setupScreenProperties.get("GamesLabel");
    this.myButtonText = setupScreenProperties.get("StartButtonText");
    this.myCSS = setupScreenProperties.get("CSS");
  }

  @Override
  public String getGameSelection() {
    return this.myGameSelection.getMySelection();
  }

  @Override
  public Button getStartButton() {
    return this.myStartButton;
  }

  @Override
  public String getPlayerName() {
    return myPlayerName.getText();
  }

  @Override
  public Scene setupScene(int height, int width) {
    createUIComponents();
    addUIComponentsToRoot();
    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets().add(myCSS);
    return scene;
  }

  private void createUIComponents() {
    this.myRoot = new VBox();
    addTitle();
    createPlayerNameBar();
    this.myGameSelection = new GameSelection(this, myCSS, mySetupScreenOptions,
        mySetupScreenProperties,myGameHandler);
    this.myStartButton = new StartButton(myButtonText);
    this.myCustomGameSelection = new CustomGameSelection(myCSS, mySetupScreenOptions,
        mySetupScreenProperties, myGameHandler, myComponentHandler);
  }

  private void addTitle() {
    this.myTitle = new Text(mySetupScreenProperties.get(TITLE_KEY));
    this.myTitle.setId("title");
    this.myTitle.wrappingWidthProperty().bind(myRoot.widthProperty());
    this.myTitle.setTextAlignment(TextAlignment.CENTER);
  }

  private void createPlayerNameBar() {
    this.myPlayerNameBar = new HBox();
    Text playerNameText = new Text(mySetupScreenProperties.get(NAME_LABEL_KEY));
    this.myPlayerName = new TextField();
    this.myPlayerNameBar.getChildren().addAll(playerNameText, myPlayerName);
    this.myPlayerNameBar.getStyleClass().add("horizontal-setup");
    this.myPlayerNameBar.setPadding(new Insets(0, 0, 20, 0));
  }

  private void addUIComponentsToRoot() {
    this.myMainRow = new HBox();
    this.myMainRow.getStyleClass().add("horizontal-setup");
    this.myMainRow.getChildren().add(myGameSelection);

    this.myRoot.getStyleClass().add("splash-screen");
    this.myRoot.getChildren().addAll(myTitle, myPlayerNameBar, myMainRow, myStartButton);
  }

  public void addCustomOptionsToScreen() {
    myMainRow.getChildren().add(myCustomGameSelection);
  }

}
