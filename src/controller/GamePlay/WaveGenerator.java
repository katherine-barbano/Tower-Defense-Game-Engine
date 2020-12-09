package controller.GamePlay;

import controller.ConfigObjects.Config;
import controller.ConfigObjects.EnemyConfigFactory;
import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.towerDefenseResourceAPI;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This class defines the functionality for how a level generates its waves. The abstraction allows
 * each level to generate waves without knowing what the specific type is. Its current subclasses
 * are infinite and standard.
 * @author alex chao
 */
public abstract class WaveGenerator {

  private final List<List<String>> enemiesInDataFile;
  private final towerDefenseResourceAPI resources = new ResourceAccessor();
  private final Duration timeBetweenWaves;
  private final EnemyConfigFactory enemyConfigFactory = new EnemyConfigFactory(resources);
  private final GameStatusAPI gameStatus;
  private final GameControlAPI game;
  private int enemyColumn;
  private Duration timeLeftBeforeNextWave;

  public WaveGenerator(List<List<String>> enemiesInDatFile, GameControlAPI game,
      GameStatusAPI gameStatus) {
    this.enemiesInDataFile = enemiesInDatFile;
    this.gameStatus = gameStatus;
    this.game = game;
    timeBetweenWaves = getTimeBetweenWaves();
    timeLeftBeforeNextWave = timeBetweenWaves;
  }

  /**
   * This resets the wave generation to start at the beginning of the file
   */
  public void reset() {
    enemyColumn = 0;
    timeLeftBeforeNextWave = timeBetweenWaves;
  }

  private Duration getTimeBetweenWaves() {
    int enemyDistance = game.getEnemyAreaSpacesPerPlayerAreaSpace();
    int gameMovementSpeed = 20;
    return Duration.millis((enemyDistance / gameMovementSpeed) * 1000);
  }

  /**
   * This updates the enemy waves by generating a new wave of zombies in the back end if there are
   * still waves left based off a timer set by the animaition Speed
   * @param animationSpeed
   */
  public abstract void updateEnemyWaves(Duration animationSpeed);

  protected boolean isTimeToMakeWave(Duration animationSpeed) {
    timeLeftBeforeNextWave = timeLeftBeforeNextWave.subtract(animationSpeed);
    return timeLeftBeforeNextWave.lessThanOrEqualTo(Duration.ZERO);
  }

  protected boolean waveLeftInDataFile() {
    return enemyColumn < enemiesInDataFile.size();
  }

  protected void makeEnemies() {
    List<String> zombiesToMake = enemiesInDataFile.get(enemyColumn);
    List<Config> zombieConfigs = new ArrayList<>();
    for (String zombie : zombiesToMake) {
      try {
        zombieConfigs.add(enemyConfigFactory.makeConfig(zombie));
      } catch (Exception e) {
        makeAlert(e.getMessage());
      }
    }
    if (!zombieConfigs.isEmpty()) {
      game.createWaveOfEnemies(zombieConfigs);
    }
    gameStatus.updateProgress((double) (enemyColumn + 1) / enemiesInDataFile.size());
    enemyColumn++;
    timeLeftBeforeNextWave = timeBetweenWaves;
  }

  private void makeAlert(String message) {
    Alert a = new Alert(Alert.AlertType.NONE);
    ButtonType close = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
    a.getButtonTypes().addAll(close);
    a.setContentText(message);
    a.show();
  }

}
