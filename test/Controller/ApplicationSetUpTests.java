package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import controller.Managers.ApplicationManager;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.Splashscreen.SelectionBox;


public class ApplicationSetUpTests extends DukeApplicationTest {

  ApplicationManager app;
  private Scene myScene;
  private Stage stage;

  @Override
  public void start(Stage primaryStage) {
    stage = primaryStage;
    app = new ApplicationManager(stage, "defaultApplication");
    myScene = stage.getScene();
  }

  @Test
  public void ApplicationInitializesSetupScreen() {
    Node b = myScene.lookup("#setup-button");
  }

  @Test
  public void initializeGame() {
    Button b = lookup("#setup-button").queryButton();
    SelectionBox languageSelection = (SelectionBox) myScene.lookup("#language-selection");
    ChoiceBox languagebox = languageSelection.getMyChoiceBox();
    javafxRun(() -> languagebox.setValue("English"));
    javafxRun(() -> b.fire());
    myScene = stage.getScene();
    Button start = lookup("#start-button").queryButton();
    assertEquals(start, myScene.lookup("#start-button"));
    SelectionBox gameSelection = (SelectionBox) myScene.lookup("#game-selection");
    ChoiceBox gamebox = gameSelection.getMyChoiceBox();
    javafxRun(() -> gamebox.setValue("testGame"));
    javafxRun(() -> start.fire());
    assertNotNull(lookup("#player0,0").query());
    myScene = stage.getScene();
  }
}