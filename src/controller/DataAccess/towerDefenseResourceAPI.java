package controller.DataAccess;

import java.io.FileNotFoundException;
import java.util.Map;
import javafx.scene.image.Image;

public interface towerDefenseResourceAPI {

  String getGridCSV(String gridCSV);

  Map<String, Image> getPlantImageMap(String[] plants, String theme) throws FileNotFoundException;

  Map<String, String> getEnemyNameMap(String enemyMap);

  String getWaveCSV(String zombieBoardCSVPath);

  String getEnemyConfigName(String enemyConfig);

  String getEnemyPropertiesFilePath(String zombie);

  Map<String, Image> getComponentImageMap(String theme) throws FileNotFoundException;

  Map<String, Double> getComponentSizing();

  Map<String, String> getLevelProperties(String levelPropertiesFilePath);

  String getCSS(String theme);

  Map<String, String> getResourcePathMap();

  Map<Integer, String> getLevelPropertyFileNamesForGame(String game);

}
