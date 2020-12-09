package view.GameView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.opencsv.exceptions.CsvException;
import controller.Exceptions.GeneralExceptions.NullNameException;
import controller.Exceptions.ReaderExceptions.IncorrectCSVFormat;
import controller.GamePlay.Level;
import controller.Managers.ApplicationManager;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class GameViewDimensionsTest extends DukeApplicationTest {

  private Level myLevel;
  private Scene myScene;


  @Override
  public void start(Stage stage)
      throws IncorrectCSVFormat, CsvException, NullNameException, IOException {
    myLevel = new Level("level1", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = myLevel.getLevelScene();
    stage.setScene(myScene);
    stage.setTitle("GridViewTest");
    stage.show();
  }

  @Test
  public void testLevelDimensions(){
    assertEquals(myScene.getWidth(),Level.SCENE_WIDTH);
    assertEquals(myScene.getHeight(),Level.SCENE_HEIGHT);
  }

}
