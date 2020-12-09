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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;
import view.GameView.PlantSelection.TowerBox;


class DiggingToolTest extends DukeApplicationTest {

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
  void shovelRemovesPlant() {
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("Peashooter", playerCell.getName());
    Node shovel = lookup("#Shovel").query();
    javafxRun(() -> clickOn(shovel));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("EMPTY", playerCell.getName());
  }

  @Test
  void shovelOnBlockedState() {
    PlayerGridComponentDisplay playerCell = lookup("#player4,0").query();
    assertEquals("BLOCKED", playerCell.getName());
    Node shovel = lookup("#Shovel").query();
    javafxRun(() -> clickOn(shovel));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("BLOCKED", playerCell.getName());
  }

  @Test
  void shovelClicksOnPlantTwice() {
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("Peashooter", playerCell.getName());
    Node shovel = lookup("#Shovel").query();
    javafxRun(() -> clickOn(shovel));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> clickOn(shovel));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("EMPTY", playerCell.getName());
  }

  @Test
  void plantAfterShovel() {
    TowerBox towerBox = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player3,3").query();
    Node shovel = lookup("#Shovel").query();
    javafxRun(() -> clickOn(shovel));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1)));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(50)));
    assertEquals("Peashooter", playerCell.getName());
  }
}
