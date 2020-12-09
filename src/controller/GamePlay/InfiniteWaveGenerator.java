package controller.GamePlay;

import java.util.List;
import javafx.util.Duration;
import model.gameplay.MVCInteraction.API.GameControlAPI;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

/**
 * This is a subclass of WaveGenerator where the waves are generated infinitely
 * @author alex chao
 */
public class InfiniteWaveGenerator extends WaveGenerator {

  public InfiniteWaveGenerator(List<List<String>> enemiesInDatFile,
      GameControlAPI game,
      GameStatusAPI gameStatus) {
    super(enemiesInDatFile, game, gameStatus);
  }

  /**
   * After the end of the file has been reached, this starts generating waves again from the top of
   * the file
   * @see WaveGenerator#updateEnemyWaves(Duration)
   * @param animationSpeed
   */
  @Override
  public void updateEnemyWaves(Duration animationSpeed) {
    if (isTimeToMakeWave(animationSpeed)) {
      if (waveLeftInDataFile()) {
        makeEnemies();
      } else {
        reset();
      }
    }
  }
}
