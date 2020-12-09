package controller.GamePlay;

import java.util.List;
import javafx.util.Duration;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This is a subclass of WaveGenerator where the waves are generated until the end of the file is
 * reached and then no enemies are generated anymore
 *
 * @author alex chao
 */
public class StandardWaveGenerator extends WaveGenerator {

  public StandardWaveGenerator(List<List<String>> enemiesInDatFile,
      GameControlAPI game,
      GameStatusAPI gameStatus) {
    super(enemiesInDatFile, game, gameStatus);
  }

  /**
   * For the standard wave generator, waves are only generated until the end of the file has been
   * reached.
   * @see WaveGenerator#updateEnemyWaves(Duration)
   * @param animationSpeed
   */
  @Override
  public void updateEnemyWaves(Duration animationSpeed) {
    if (isTimeToMakeWave(animationSpeed) && waveLeftInDataFile()) {
      makeEnemies();
    }
  }
}
