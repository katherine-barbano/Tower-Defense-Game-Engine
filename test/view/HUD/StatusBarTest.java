package view.HUD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.opencsv.exceptions.CsvException;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import controller.GamePlay.Level;
import controller.Managers.ApplicationManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;
import view.GameView.PlantSelection.TowerBox;

class StatusBarTest extends DukeApplicationTest {

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
    stage.setTitle("GridViewTest");
    stage.show();
  }

  @Test
  void initialSunIsReadFromProperties() {
    Text sunAmount = lookup("#sunAmount").query();
    assertEquals("150", sunAmount.getText());
  }

  @Test
  void plantingPeaShooterDecreasesSun() {
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    Text sunAmount = lookup("#sunAmount").query();
    assertEquals("150", sunAmount.getText());
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    assertEquals("50", sunAmount.getText());
  }

  @Test
  void plantingSunFlowerDecreasesSun() {
    TowerBox towerBox = lookup("#Sunflower").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    Text sunAmount = lookup("#sunAmount").query();
    assertEquals("150", sunAmount.getText());
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    assertEquals("100", sunAmount.getText());
  }

  @Test
  void sunFlowerIncreasesSun() {
    TowerBox towerBox = lookup("#Sunflower").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    Text sunAmount = lookup("#sunAmount").query();
    assertEquals("150", sunAmount.getText());
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    assertEquals("100", sunAmount.getText());
    int i = 0;
    while (i < 75) {
      javafxRun(() -> currentLevel.update(Duration.millis(1)));
      i++;
    }
    assertEquals("150", sunAmount.getText());
  }

  @Test
  void timeUpdates() {
    Text timePassed = lookup("#time").query();
    assertEquals("0", timePassed.getText());
    int i = 0;
    while (i < 15) {
      javafxRun(() -> currentLevel.update(Duration.millis(50)));
      i++;
    }
    assertEquals("1", timePassed.getText());
  }

  @Test
  void invalidPlantDoesNotDecreaseSun() {
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player4,0").query();
    assertEquals("BLOCKED", playerCell.getName());
    Text sunAmount = lookup("#sunAmount").query();
    assertEquals("150", sunAmount.getText());
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    assertEquals("150", sunAmount.getText());
  }

  @Test
  void progressBarUpdatesAsWavesGenerate() {
    ProgressBar enemyProgress = lookup("#enemyProgress").query();
    assertEquals(0, enemyProgress.getProgress());
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    javafxRun(() -> currentLevel.update(Duration.millis(1000)));
    assertNotEquals(0, enemyProgress.getProgress());
  }
}