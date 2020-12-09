package view.HUD;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GameControlPanel.PlayPauseButton;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;
import view.GameView.PlantSelection.TowerBox;

public class TimingTest extends DukeApplicationTest {

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
  void timePausesWhenGamePauses() {
    PlayPauseButton pause = lookup("#Pause").query();
    Text timePassed = lookup("#time").query();
    assertEquals("0", timePassed.getText());
    int i = 0;
    while (i < 15) {
      javafxRun(() -> currentLevel.update(Duration.millis(50)));
      i++;
    }
    assertEquals("1", timePassed.getText());
    clickOn(pause);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    i = 0;
    while (i < 30) {
      javafxRun(() -> currentLevel.update(Duration.millis(50)));
      i++;
    }
    assertEquals("1", timePassed.getText());
  }

  @Test
  void plantIsNotPlacedWhenGamePauses() {
    PlayPauseButton pause = lookup("#Pause").query();
    clickOn(pause);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("EMPTY", playerCell.getName());
  }

  @Test
  void clickingPauseAgainResumesGame() {
    PlayPauseButton pause = lookup("#Pause").query();
    Text timePassed = lookup("#time").query();
    clickOn(pause);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    int i = 0;
    while (i < 30) {
      javafxRun(() -> currentLevel.update(Duration.millis(50)));
      i++;
    }
    assertEquals("0", timePassed.getText());
    clickOn(pause);
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    while (i < 40) {
      javafxRun(() -> currentLevel.update(Duration.millis(1)));
      i++;
    }
    assertEquals("1", timePassed.getText());
  }


}
