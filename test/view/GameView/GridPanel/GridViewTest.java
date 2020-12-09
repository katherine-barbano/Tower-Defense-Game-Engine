package view.GameView.GridPanel;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.opencsv.exceptions.CsvException;
import controller.DataAccess.Reader;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import controller.GamePlay.Level;
import controller.Managers.ApplicationManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.gameComponents.SingleState;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;

class GridViewTest extends DukeApplicationTest {

  private static final int ANIMATION_SPEED = 500;
  private final Reader reader = new Reader();
  private Scene myScene;
  private Level myLevel;
  private GridPanel myGrid;
  private List<List<SingleState>> level1Grid;

  @Override
  public void start(Stage stage)
      throws IOException, CsvException, ReaderExceptions.IncorrectCSVFormat, GeneralExceptions.NullNameException {
    Map<String, String> gameSelections = new HashMap<>();
    gameSelections.put("theme", "Space");
    myLevel = new Level("level1", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = myLevel.getLevelScene();
    stage.setScene(myScene);
    stage.setTitle("GridViewTest");
    stage.show();

    myGrid = lookup("#grid-view").query();
    level1Grid = reader.getInitialGrid("data/grids/grid1.csv");
  }

  @Test
  void testPlayerGridPlacement() {
    for (int row = 0; row < level1Grid.size(); row++) {
      for (int col = 0; col < level1Grid.get(0).size(); col++) {
        PlayerGridComponentDisplay grid = lookup("#player" + row + "," + col).query();
        assertEquals(level1Grid.get(row).get(col).getName(), grid.getName());
      }
    }
  }

  @Test
  void testEnemyGridPlacement() {
    javafxRun(() -> myLevel.update(Duration.millis(ANIMATION_SPEED)));
    javafxRun(() -> myLevel.update(Duration.millis(ANIMATION_SPEED)));
    javafxRun(() -> myLevel.update(Duration.millis(ANIMATION_SPEED)));
    javafxRun(() -> myLevel.update(Duration.millis(ANIMATION_SPEED)));
    javafxRun(() -> myLevel.update(Duration.millis(ANIMATION_SPEED)));
    ImageView enemy = lookup("#enemy0").query();
    PlayerGridComponentDisplay grid = lookup("#player" + 2 + "," + 7).query();
    assertTrue(enemy.getBoundsInParent().intersects(grid.getBoundsInParent()));

    javafxRun(() -> myLevel.update(Duration.millis(5000)));
    javafxRun(() -> myLevel.update(Duration.millis(ANIMATION_SPEED)));
    ImageView enemy1 = lookup("#enemy0").query();
    ImageView enemy2 = lookup("#enemy1").query();
    assertTrue(enemy1.getBoundsInParent().intersects(grid.getBoundsInParent()));
    assertTrue(enemy2.getBoundsInParent().intersects(grid.getBoundsInParent()));
  }
}


