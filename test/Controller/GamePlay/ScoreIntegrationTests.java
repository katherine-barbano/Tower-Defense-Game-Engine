package Controller.GamePlay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.opencsv.exceptions.CsvException;
import controller.DataAccess.Writer;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import controller.GamePlay.Level;
import controller.Managers.ApplicationManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.gameplay.MVCInteraction.API.GameStatusAPI;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.GameView.GridPanel.GridPanel;

public class ScoreIntegrationTests extends DukeApplicationTest {

  private final Map<String, String> gameSelections = new HashMap<>();
  private final Map<String, String> testResourcePathMap = new HashMap<>();
  private Level currentLevel;
  private Writer writer;
  private Scene myScene;
  private GridPanel myGrid;
  private GameStatusAPI gameStatusAPI;
  private Map<String, String> status;


  @Override
  public void start(Stage stage)
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, GeneralExceptions.NullNameException {
    Map<String, String> gameSelections = new HashMap<>();
    gameSelections.put("theme", "Space");
    currentLevel = new Level("level2", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = currentLevel.getLevelScene();
    stage.setScene(myScene);
    stage.setTitle("ScoreIntegrationTest");
    stage.show();
    myGrid = lookup("#grid-view").query();
  }

  @Test
  public void statusIsAccessibleFromLevel() {
    status = currentLevel.getGameStatusAPI().getStatus();
    assertNotNull(status);
  }

  @Test
  public void statusTimeUpdates() {
    moveOneSecond();
    status = currentLevel.getGameStatusAPI().getStatus();
    assertEquals("1", status.get("timeElapsed"));
  }

  private void moveOneSecond() {
    int i = 0;
    while (i < 15) {
      javafxRun(() -> currentLevel.update(Duration.millis(50)));
      i++;
    }
  }

  @Test
  public void statusIsWrittenWithAppropriateValues() throws IOException {
    moveOneSecond();
    status = currentLevel.getGameStatusAPI().getStatus();
    Properties p = writeNewPropertiesFile();
    assertEquals("1", p.get("timeElapsed"));
  }

  @Test
  public void statusGetsOverWrittenOnSecondWriterCall() throws IOException {
    status = currentLevel.getGameStatusAPI().getStatus();
    Properties p = writeNewPropertiesFile();
    assertEquals("0", p.get("timeElapsed"));
  }

  private Properties writeNewPropertiesFile() throws IOException {
    testResourcePathMap.put("Score", "test/TestData/");
    writer = new Writer(testResourcePathMap);
    writer.writeHighScore("defaultGame", status, "Megan");
    FileInputStream inputStream = new FileInputStream("test/TestData/defaultGame.properties");
    Properties p = new Properties();
    p.load(inputStream);
    return p;
  }


}