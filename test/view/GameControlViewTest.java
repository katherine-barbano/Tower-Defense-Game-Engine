package view;

import controller.GamePlay.Level;
import controller.Managers.ApplicationManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.gameComponents.SingleState;
import model.gameplay.MVCInteraction.concreteModel.GameControl;
import model.gameplay.MVCInteraction.concreteModel.GameStatus;
import model.gameplay.MVCInteraction.concreteModel.GridGameControl;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GameControlViewTest extends DukeApplicationTest {

  private GameControl myGameControl;
  private Scene myScene;

  private List<List<SingleState>> initializeEmptyBoard(int rowSize, int columnSize) {
    List<List<SingleState>> initialBoard = new ArrayList<>();
    for (int row = 0; row < rowSize; row++) {
      List<SingleState> rowList = new ArrayList<>();
      for (int column = 0; column < columnSize; column++) {
        rowList.add(SingleState.EMPTY);
      }
      initialBoard.add(rowList);
    }
    return initialBoard;
  }

  @Override
  public void start(Stage stage) throws Exception {
    List<List<SingleState>> initialBoard = initializeEmptyBoard(5, 5);
    myGameControl = new GridGameControl(initialBoard, 5, 5, new GameStatus(1000, 150));
    Map<String, String> gameSelections = new HashMap<>();
    gameSelections.put("theme", "Space");
    Level current = new Level("level1", new ApplicationManager(new Stage(), "defaultApplication"));
    myScene = current.getLevelScene();
    stage.setScene(myScene);
    stage.setTitle("GridViewTest");
    stage.show();
  }

  @Test
  void testDisplay() {
    sleep(3000);
  }


}

