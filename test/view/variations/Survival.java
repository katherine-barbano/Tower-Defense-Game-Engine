package view.variations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.gameComponents.SingleState;
import org.junit.jupiter.api.Test;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridPanel;

public class Survival extends DukeApplicationTest {

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
    myLevel = new Level("survivalLevel",
        new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = myLevel.getLevelScene();
    stage.setScene(myScene);
    stage.setTitle("GridViewTest");
    stage.show();

  }


  @Test
  void testWaveGeneratesAgain() {
    assertThrows(EmptyNodeQueryException.class, () -> lookup("#enemy0").query());
    javafxRun(() -> myLevel.update(Duration.millis(1000)));
    assertDoesNotThrow(() -> lookup("#enemy0").query());
    assertThrows(EmptyNodeQueryException.class, () -> lookup("#enemy1").query());
    javafxRun(() -> myLevel.update(Duration.millis(10000)));
    javafxRun(() -> myLevel.update(Duration.millis(10000)));
    assertDoesNotThrow(() -> lookup("#enemy1").query());

  }
}