package view.HUD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controller.Managers.ApplicationManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GameControlPanel.GameControlComponent;
import view.Splashscreen.SelectionBox;

public class HomeButtonTest extends DukeApplicationTest {

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
  public void testHomeButtonReturnsToSetUpScreen() {
    startGame("defaultGame");
    GameControlComponent homeButton = lookup("#HomeButton").query();
    clickOn(homeButton);
    Button b = lookup("#setup-button").queryButton();
    SelectionBox languageSelection = lookup("#language-selection").query();
    ChoiceBox languagebox = languageSelection.getMyChoiceBox();
    javafxRun(() -> languagebox.setValue("English"));
    javafxRun(() -> b.fire());
    myScene = stage.getScene();
    Button start = lookup("#start-button").queryButton();
    assertEquals(start, myScene.lookup("#start-button"));
  }


  private void startGame(String gameType) {
    Button b = lookup("#setup-button").queryButton();
    SelectionBox languageSelection = lookup("#language-selection").query();
    ChoiceBox languagebox = languageSelection.getMyChoiceBox();
    javafxRun(() -> languagebox.setValue("English"));
    javafxRun(() -> b.fire());
    Button start = lookup("#start-button").queryButton();
    SelectionBox gameSelection = lookup("#game-selection").query();
    ChoiceBox gamebox = gameSelection.getMyChoiceBox();
    javafxRun(() -> gamebox.setValue(gameType));
    javafxRun(() -> start.fire());
    javafxRun(() -> {
      app.step();
    });

  }
}
