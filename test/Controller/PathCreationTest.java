package Controller;

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
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridComponents.PlayerGridComponentDisplay;
import view.GameView.GridPanel.GridPanel;

public class PathCreationTest extends DukeApplicationTest {

  private final Map<String, String> gameSelections = new HashMap<>();
  private Level currentLevel;
  private Scene myScene;
  private GridPanel myGrid;

  @Override
  public void start(Stage stage)
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, GeneralExceptions.NullNameException {
    Map<String, String> gameSelections = new HashMap<>();
    gameSelections.put("theme", "Space");
    currentLevel = new Level("level2", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = currentLevel.getLevelScene();
    stage.setScene(myScene);
    stage.setTitle("GridViewTest");
    stage.show();
    myGrid = lookup("#grid-view").query();
  }

  @Test
  public void firstColumnOfPathGridInitializesWithAppropriateState() {
    PlayerGridComponentDisplay cellDisplay00 = lookup("#player0,0").query();
    assertEquals("EMPTY", cellDisplay00.getName());
    PlayerGridComponentDisplay cellDisplay10 = lookup("#player1,0").query();
    assertEquals("PATH", cellDisplay10.getName());
    PlayerGridComponentDisplay cellDisplay20 = lookup("#player2,0").query();
    assertEquals("EMPTY", cellDisplay20.getName());
    PlayerGridComponentDisplay cellDisplay30 = lookup("#player3,0").query();
    assertEquals("BLOCKED", cellDisplay30.getName());
  }


}