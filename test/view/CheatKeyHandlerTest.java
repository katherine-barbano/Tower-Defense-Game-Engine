package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.opencsv.exceptions.CsvException;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import controller.GamePlay.Level;
import controller.Managers.ApplicationManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;
import view.GameView.PlantSelection.TowerBox;


class CheatKeyHandlerTest extends DukeApplicationTest {

  private Level currentLevel;
  private Scene myScene;

  @Override
  public void start(Stage stage)
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, GeneralExceptions.NullNameException {
    Map<String, String> gameSelections = new HashMap<>();
    gameSelections.put("theme", "Default");

    currentLevel = new Level("level1", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = currentLevel.getLevelScene();
    currentLevel.update(Duration.millis(50));
    stage.setScene(myScene);
    stage.show();
  }

  @Test
  void gamePausesOnSpace() {
    Text timePassed = lookup("#time").query();
    assertEquals("0", timePassed.getText());
    stepGame();
    assertEquals("1", timePassed.getText());
    press(myScene, KeyCode.SPACE);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    stepGame();
    assertEquals("1", timePassed.getText());
  }

  @Test
  void sunIncreasesOnRight() {
    Text sunAmount = lookup("#sunAmount").query();
    assertEquals("150", sunAmount.getText());
    press(myScene, KeyCode.RIGHT);
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    assertEquals("200", sunAmount.getText());
  }

  @Test
  void gameSpeedIncreasesOnUp() {
    Text timePassed = lookup("#time").query();
    assertEquals("0", timePassed.getText());
    stepGame();
    assertEquals("1", timePassed.getText());
    press(myScene, KeyCode.UP);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    stepGame();
    assertEquals("4", timePassed.getText());
  }

  @Test
  void gameSpeedDecreasesOnDown() {
    Text timePassed = lookup("#time").query();
    assertEquals("0", timePassed.getText());
    stepGame();
    assertEquals("1", timePassed.getText());
    press(myScene, KeyCode.UP);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    stepGame();
    assertEquals("4", timePassed.getText());
    press(myScene, KeyCode.DOWN);
    stepGame();
    assertEquals("5", timePassed.getText());
  }

  @Test
  void timeResetsOnR() {
    Text timePassed = lookup("#time").query();
    assertEquals("0", timePassed.getText());
    stepGame();
    assertEquals("1", timePassed.getText());
    press(myScene, KeyCode.R);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("0", timePassed.getText());
  }

  @Test
  void plantsResetOnR() {
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertEquals("Peashooter", playerCell.getName());
    press(myScene, KeyCode.R);
    assertEquals("EMPTY", playerCell.getName());
  }

  private void stepGame() {
    int i = 0;
    while (i < 15) {
      javafxRun(() -> currentLevel.update(Duration.millis(50)));
      i++;
    }
  }
}