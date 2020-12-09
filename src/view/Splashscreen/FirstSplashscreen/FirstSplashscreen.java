package view.Splashscreen.FirstSplashscreen;

import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import view.Splashscreen.FirstSplashscreenAPI;
import view.Splashscreen.SelectionBox;

/**
 * The FirstSplashscreen creates the language selection dropdown and the Setup Button. It implements
 * the FirstSplashScreenAPI, to return data from user input.
 * @author Heather Grune (hlg20)
 */
public class FirstSplashscreen implements FirstSplashscreenAPI {

  private final Map<String, String> mySplashScreenProperties;
  private VBox myRoot;
  private SelectionBox myLanguageSelection;
  private Button mySetupButton;

  public FirstSplashscreen(Map<String, String> splashscreenProperties) {
    this.mySplashScreenProperties = splashscreenProperties;
  }

  /**
   * Create the scene for the splashcreen. Create and add components to the scene's root.
   * @param height Height of the splashscreen.
   * @param width Width of the splashscreen.
   * @return the scene to be displayed on the main stage.
   */
  @Override
  public Scene setupScene(int height, int width) {
    createUIComponents();
    addUIComponentsToRoot();
    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets().add(mySplashScreenProperties.get("CSS"));
    return scene;
  }

  private void createUIComponents() {
    myRoot = new VBox();
    myLanguageSelection = new LanguageSelection(
        mySplashScreenProperties.get("LanguageChoices").split(","),
        mySplashScreenProperties);
    mySetupButton = new SetupButton(mySplashScreenProperties.get("SetupButtonText"));
  }

  private void addUIComponentsToRoot() {
    myRoot.getStyleClass().add("splash-screen");
    myRoot.getChildren().add(myLanguageSelection);
    myRoot.getChildren().add(mySetupButton);
  }

  /**
   * Return the string which represents the user's language selection.
   * @return
   */
  @Override
  public String getLanguageSelection() {
    return myLanguageSelection.getMySelection();
  }

  /**
   * Accessor for the Setup Button
   * @return the setup button
   */
  @Override
  public Button getSetUpButton() {
    return mySetupButton;
  }
}
