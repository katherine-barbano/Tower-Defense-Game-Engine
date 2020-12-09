package view.GameView.PlantSelection;


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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;

class PlantCreationTest extends DukeApplicationTest {

  private Level currentLevel;
  private Scene myScene;


  @Override
  public void start(Stage stage)
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, GeneralExceptions.NullNameException {
    Map<String, String> gameSelections = new HashMap<>();
    gameSelections.put("theme", "Default");
    currentLevel = new Level("level1", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = currentLevel.getLevelScene();
    currentLevel.update(Duration.millis(1));
    stage.setScene(myScene);
    stage.setTitle("GridViewTest");
    stage.show();
  }

  @Test
  void testPeashooterCreation() {
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
  }

  @Test
  void testSunflowerCreation() {
    TowerBox towerBox = lookup("#Sunflower").query();
    PlayerGridComponentDisplay playerCell = lookup("#player1,0").query();
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> clickOn(towerBox));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertEquals("Sunflower", playerCell.getName());
  }

  @Test
  void testMultiplePlantCreation() {
    TowerBox sunflowerSeed = lookup("#Sunflower").query();
    TowerBox peashooterSeed = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player1,0").query();
    PlayerGridComponentDisplay playerCell1 = lookup("#player0,1").query();
    sleep(1000);
    assertEquals("EMPTY", playerCell.getName());
    javafxRun(() -> clickOn(sunflowerSeed));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertEquals("EMPTY", playerCell1.getName());
    assertEquals("Sunflower", playerCell.getName());
    javafxRun(() -> clickOn(peashooterSeed));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell1));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertEquals("Peashooter", playerCell1.getName());
  }

  @Test
  void testPlantOnExistingPlant() {
    TowerBox sunflowerSeed = lookup("#Sunflower").query();
    TowerBox peashooterSeed = lookup("#Peashooter").query();
    PlayerGridComponentDisplay playerCell = lookup("#player1,0").query();
    javafxRun(() -> clickOn(sunflowerSeed));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(peashooterSeed));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    javafxRun(() -> clickOn(playerCell));
    javafxRun(() -> currentLevel.update(Duration.millis(1
    )));
    assertEquals("Sunflower", playerCell.getName());
  }


}

