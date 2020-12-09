package view.Splashscreen.setupScreen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controller.Customization.TowerDefenseCustomGameHandler;
import controller.Customization.TowerDefenseCustomLevelComponentHandler;
import controller.DataAccess.ResourceAccessor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.Splashscreen.SelectionBox;
import view.Splashscreen.setupScreen.custom.CustomGameSelection;

class CustomGameSelectionTest extends DukeApplicationTest {

  private Scene myScene;
  private CustomGameSelection myGameSelection;

  @Override
  public void start(Stage stage) throws Exception {
    String[] levelOptions = {"level1", "level2", "customLevel"};
    String[] numLevels = {"1", "2", "3"};
    ResourceAccessor resources = new ResourceAccessor();
    myGameSelection = new CustomGameSelection("splashscreen.css",
        resources.getSetupOptionsMap("English"),
        resources.getSetupPropertiesMap("English"), new TowerDefenseCustomGameHandler(),
        new TowerDefenseCustomLevelComponentHandler());
    myScene = new Scene(myGameSelection, 400, 400);
    stage.setScene(myScene);
    stage.show();
  }

  @Test
  void testDynamicDropdownNumbers() {
    SelectionBox levelNumChoice = lookup("#level-num-selection").query();
    javafxRun(() -> levelNumChoice.getMyChoiceBox().setValue("1"));
    assertEquals(myGameSelection.getNumChosenLevels(), 1);
    sleep(3000);

    javafxRun(() -> levelNumChoice.getMyChoiceBox().setValue("2"));
    sleep(3000);
    assertEquals(myGameSelection.getNumChosenLevels(), 2);

    javafxRun(() -> levelNumChoice.getMyChoiceBox().setValue("3"));
    assertEquals(myGameSelection.getNumChosenLevels(), 3);
  }

  @Test
  void testCustomLevelSelection() {
    /*SelectionBox levelNumChoice = lookup("#level-num-selection").query();
    javafxRun(()->levelNumChoice.getMyChoiceBox().setValue("1"));
    SelectionBox levelSelection = lookup("#level-selection").query();
    javafxRun(()->levelSelection.getMyChoiceBox().setValue("Custom"));

    verifyThat("#custom-level-popup", NodeMatchers.isVisible());*/
  }


}