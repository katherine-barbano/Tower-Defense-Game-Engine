package view.Splashscreen.FirstSplashscreen;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import controller.DataAccess.ResourceAccessor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import view.Splashscreen.FirstSplashscreenAPI;

class FirstSplashscreenTest extends DukeApplicationTest {

  private final ResourceAccessor resources = new ResourceAccessor();
  private Scene myScene;
  private FirstSplashscreen mySplashscreen;

  @Override
  public void start(Stage stage) throws Exception {
    FirstSplashscreenAPI splashscreen = new FirstSplashscreen(
        resources.getSplashScreenProperties());
    myScene = splashscreen.setupScene(500, 500);
    stage.setScene(myScene);
    splashscreen.getSetUpButton().setOnMouseClicked((event -> {
      moveToSetUpScreen(splashscreen);
    }));
    stage.show();
  }

  private void moveToSetUpScreen(FirstSplashscreenAPI splashscreen) {

  }

  @Test
  void testDisplay() {
    sleep(2000);
    Node b = myScene.lookup("#setup-button");
    assertNotEquals(null, b);
    assertNotEquals(null, myScene.lookup("#language-selection"));

  }

}