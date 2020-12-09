package view.WinLossConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import controller.Managers.ApplicationManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;
import view.GameView.PlantSelection.TowerBox;
import view.Splashscreen.SelectionBox;

public class LossConditions extends DukeApplicationTest {

  ApplicationManager app;
  private Scene myScene;
  private Stage stage;

  @Override
  public void start(
      Stage stage) {
    this.stage = stage;
    app = new ApplicationManager(stage, "defaultApplication");
    myScene = stage.getScene();
  }

  @Test
  void defeatingAllZombiesWinsWithoutEnoughSunLosesGame() {
    startGame("sunGame");
    Text timePassed = lookup("#time").query();
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player0,0").query();
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> clickOn(playerCell));
    runGame(30);
    assertTrue(Integer.parseInt(timePassed.getText()) <= 1);
  }

  @Test
  void zombieCrossesOverLineResetsLevel() {
    startGame("testGame");
    Text timePassed = lookup("#time").query();
    assertTrue(Integer.parseInt(timePassed.getText()) <= 2);
    press(myScene, KeyCode.UP);
    press(myScene, KeyCode.UP);
    runGame(7);
    assertTrue(Integer.parseInt(timePassed.getText()) > 5);
    runGame(15);
    assertTrue(Integer.parseInt(timePassed.getText()) <= 1);
  }

  private void runGame(int steps) {
    int i = 0;
    while (i < steps) {
      javafxRun(() -> {
        app.step();
      });
      i++;
    }
  }

  private void startGame(String gameType) {
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
    javafxRun(() -> gamebox.setValue(gameType));
    javafxRun(() -> start.fire());
    myScene = stage.getScene();
    runGame(1);
  }
}
