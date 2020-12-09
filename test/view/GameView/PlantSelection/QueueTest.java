package view.GameView.PlantSelection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.opencsv.exceptions.CsvException;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import controller.GamePlay.Level;
import controller.Managers.ApplicationManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;

public class QueueTest extends DukeApplicationTest {

  private Level currentLevel;
  private Scene myScene;

  @Override
  public void start(
      Stage stage)
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, GeneralExceptions.NullNameException {
    Map<String, String> gameSelections = new HashMap<>();
    gameSelections.put("theme", "Default");
    currentLevel = new Level("level2", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = currentLevel.getLevelScene();
    currentLevel.update(Duration.millis(1));
    stage.setScene(myScene);
    stage.setTitle("GridViewTest");
    stage.show();
  }

  @Test
  void plantingPlantRemovesSeed() {
    TilePane plantSelect = lookup("#plantSelect").query();
    TowerBox towerBox = lookup("#seed0").query();
    assertEquals(plantSelect.getChildren().get(0), towerBox);
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertNotEquals("EMPTY", playerCell.getName());
    assertNotEquals(plantSelect.getChildren().get(0), towerBox);
  }

  @Test
  void newSeedGeneratesInQueue() {
    TowerBox towerBox = lookup("#seed0").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    assertThrows(EmptyNodeQueryException.class, () -> lookup("#seed1").query());
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertDoesNotThrow(() -> lookup("#seed1").query());
  }

  @Test
  void newSeedGeneratesInQueueOnRemoval() {
    TowerBox towerBox = lookup("#seed0").query();
    PlayerGridComponentDisplay playerCell = lookup("#player1,3").query();
    assertThrows(EmptyNodeQueryException.class, () -> lookup("#seed6").query());
    for (int i = 0; i < 5; i++) {
      javafxRun(() -> currentLevel.update(Duration.millis(1
      )));
    }
    assertThrows(EmptyNodeQueryException.class, () -> lookup("#seed6").query());
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertNotEquals("EMPTY", playerCell.getName());
    assertDoesNotThrow(() -> lookup("#seed5").query());
  }

}
