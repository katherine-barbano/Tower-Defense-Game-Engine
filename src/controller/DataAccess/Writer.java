package controller.DataAccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Writes files into the resources root
 *
 * @author Megan Richards
 */
public class Writer {

  private final Map<String, String> paths;

  public Writer(Map<String, String> resourcePaths) {
    paths = resourcePaths;
  }

  /**
   * Writes a new theme properties files
   * @param componentImages - map of object keys -> image files inputted by the user
   * @param name - name of the theme
   * @throws IOException
   */
  public void WriteNewTheme(Map<String, File> componentImages, String name) throws IOException {
    String path = paths.get("Theme");
    String dataPath = "data/images/";
    Map<String, String> properties = new HashMap<>();
    for (String characteristic : componentImages.keySet()) {
      File imageFile = componentImages.get(characteristic);
      Files.move(Paths.get(imageFile.getAbsolutePath()), Paths.get(dataPath + imageFile.getName()));
      properties.put(characteristic, imageFile.getName());
    }
    writePropertiesFileWithString(properties, path, name);
  }

  /**
   * writes a new variation properties file
   * @param WinConditions  list of win condition classnames
   * @param LossConditions list of loss condition classnames
   * @param name name of the variation
   * @throws IOException
   */
  public void WriteNewVariation(List<String> WinConditions, List<String> LossConditions,
      String name) throws IOException {
    String path = paths.get("Variation");
    Map<String, String[]> properties = new HashMap<>();
    properties.put("WinConditions", WinConditions.toArray(String[]::new));
    properties.put("LossConditions", LossConditions.toArray(String[]::new));
    writePropertiesFileWithStringArray(properties, path, name);
  }

  /**
   * writes a new game file
   * @param levels - list of level properties file names in the game
   * @param name - name of the game to be created
   * @throws IOException
   */
  public void WriteNewGame(List<String> levels, String name) throws IOException {
    String path = paths.get("Game");
    Map<String, String> properties = new HashMap<>();
    int i = 0;
    for (String level : levels) {
      properties.put(String.valueOf(i), level);
      i++;
    }
    writePropertiesFileWithString(properties, path, name);
  }

  //WavesCSV=board1.csv
//GridCSV=grid1.csv
//EnemyMap=basic
//Plants=Peashooter,Sunflower,DownPeashooter,FastPeashooter,CherryBomb
//GridType=Grid
//Variation=Standard
//PlantSelection=Panel
//Theme=default
//InitialSun=150
//StatusDisplay=SunDisplay,EnemyProgress,TimeDisplay,ScoreDisplay
//WinConditions=NoZombies
//LossConditions=CrossLine

  /**
   * makes a new level properties files
   * @param selections - map of level properties -> user selection
   * @param name name of the new level
   * @throws IOException
   */
  public void WriteNewLevel(Map<String, String> selections, String name) throws IOException {
    String path = paths.get("Level");
    Map<String, String> properties = new HashMap<>();
    for (String characteristic : selections.keySet()) {
      properties.put(characteristic, selections.get(characteristic));
    }
    properties.put("EnemyMap", "basic");
    properties.put("Plants", "Peashooter,Sunflower,DownPeashooter,FastPeashooter");
    properties.put("GridType", "Grid");
    properties.put("Variation", "Standard");
    properties.put("PlantSelection", "Panel");
    properties.put("InitialSun", "150");
    properties.put("WaveType", "Standard");
    properties.put("StatusDisplay", "SunDisplay,EnemyProgress,TimeDisplay,ScoreDisplay");
    writePropertiesFileWithString(properties, path, name);
  }

  /**
   * saves a given file in the data grids folder
   * @param file file to be saved
   * @throws IOException
   */
  public void WriteNewGrid(File file) throws IOException {
    String path = paths.get("Grid");
    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(path + file.getName()));
  }

  /**
   * writes in the high score for a given game
   * @param gameName - name being played
   * @param status - map of metrics -> values to compare
   * @param name - name of the user currently
   * @throws IOException
   */
  public void writeHighScore(String gameName, Map<String, String> status, String name)
      throws IOException {
    String path = paths.get("Score");
    status.put("User", name);
    writePropertiesFileFromMap(gameName, status, path);
  }

  /**
   * writes a new waves csv by moving the given file to the correct data folder
   * @param file csv file of waves
   * @throws IOException
   */
  public void WriteNewWavesCSV(File file) throws IOException {
    String path = paths.get("WaveCSV");
    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(path + file.getName()));
  }

  private void writePropertiesFileWithStringArray(Map<String, String[]> properties, String path,
      String name) throws IOException {
    File file = new File(path + name + ".properties");
    OutputStream outputStream = new FileOutputStream(file);
    Properties p = new Properties();
    for (String key : properties.keySet()) {
      String comb = String.join(",", properties.get(key));
      p.put(key, comb);
    }
    p.store(outputStream, "");
  }

  private void writePropertiesFileFromMap(String gameName, Map<String, String> status, String path)
      throws IOException {
    File file = new File(path + gameName + ".properties");
    OutputStream outputStream = new FileOutputStream(file);
    Properties properties = new Properties();
    for (String statusName : status.keySet()) {
      properties.put(statusName, status.get(statusName));
    }
    properties.store(outputStream, "");
  }

  private void writePropertiesFileWithString(Map<String, String> properties, String path,
      String name) throws IOException {
    writePropertiesFileFromMap(name, properties, path);
  }

}

