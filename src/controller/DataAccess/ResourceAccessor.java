package controller.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

/**
 * Acceses resources from a centralized location
 *
 * @author Megan richards
 */
public class ResourceAccessor implements AppResourceAPI, towerDefenseResourceAPI, setupResourceAPI {

  public static final String COMPONENT_SIZE_PROPERTIES = "GameView";

  /**
   * gets the paths for the set up properties for a given language
   * @param language  selected language
   * @return map of the paths for the set up properties for the language chosen
   */
  @Override
  public Map<String, String> getSetupPropertiesMap(String language) {
    Map<String, String> setupMap = makeStringMap(
        ResourceBundle.getBundle("resources/visual/SetupScreen" + language));
    setupMap.put("CSS", String.format("resources/visual/css/%s", setupMap.get("CSS")));
    return setupMap;
  }


  /**
   * gets the resource paths for the set up options for the customization
   * @param language -language chosen
   * @return map of selection name -> resource path to those files
   */
  @Override
  public Map<String, String[]> getSetupOptionsMap(String language) {
    Map<String, String[]> setupMap = new HashMap<>();
    setupMap.put("GameSelection", getFilesFromDirectory("src/resources/games/"));
    setupMap.put("GridSelection", getFilesFromDirectory("data/grids/"));
    setupMap.put("EnemyWaveSelection", getFilesFromDirectory("data/waves/"));
    setupMap.put("LevelSelection", getFilesFromDirectory("src/resources/levels/"));
    setupMap.put("ThemeSelection", getFilesFromDirectory("src/resources/visual/themes/"));
    setupMap.put("ComponentNames", getComponentNames(language));
    setupMap.put("WinConditionSelection", getWinConditions());
    setupMap.put("LossConditionSelection", getLossConditions());
    setupMap.put("LevelNumSelection", new String[]{"1", "2", "3", "4", "5"});
    setupMap.put("VariationSelection", getFilesFromDirectory("src/resources/variations/"));
    return setupMap;
  }

  private String[] getLossConditions() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/general/LossConditions");
    return makeStringArrayFromSet(resourceBundle.getKeys());
  }

  private String[] getWinConditions() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/general/WinConditions");
    return makeStringArrayFromSet(resourceBundle.getKeys());
  }

  private String[] getComponentNames(String language) {
    ResourceBundle gameObjects = ResourceBundle
        .getBundle("resources/general/gameObjects" + language);
    return makeStringArrayFromSet(gameObjects.getKeys());
  }

  private String[] makeStringArrayFromSet(Enumeration<String> keys) {
    List<String> componentNames = Collections.list(keys);
    String[] components = new String[componentNames.size()];
    return componentNames.toArray(components);
  }

  private String[] getFilesFromDirectory(String path) {
    File file = new File(path);
    String[] a = file.list();
    String[] b = new String[a.length + 1];
    for (int i = 0; i < a.length; i++) {
      b[i] = a[i].replace(".properties", "");
    }
    b[a.length] = "Custom";
    return b;
  }

  /**
   * gets the resources for an app as a map
   * @param appPropertiesFileName - file name for the app properties file
   * @return
   */
  @Override
  public Map<String, String> getAppResources(String appPropertiesFileName) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/" + appPropertiesFileName);
    Map<String, String> appResources = new HashMap<>();
    for (String key : resourceBundle.keySet()) {
      appResources.put(key, resourceBundle.getString(key));
    }
    return appResources;
  }

  /**
   * gets the splash screen properties
   * @return map of the properties for the splashscreen
   */
  @Override
  public Map<String, String> getSplashScreenProperties() {
    Map<String, String> splashScreenMap = makeStringMap(
        ResourceBundle.getBundle("resources/visual/SplashScreen"));
    splashScreenMap.put("CSS",
        String.format("resources/visual/css/%s", splashScreenMap.get("CSS").replace("\"", "")));
    return splashScreenMap;
  }

  /**
   * gets the name for the enemy config
   * @param enemyConfig name of the enemy subclass
   * @return the full name for reflection
   */
  @Override
  public String getEnemyConfigName(String enemyConfig) {
    return "controller.ConfigObjects." + enemyConfig;
  }

  /**
   * gets the file path for the enemies properties resource bundles
   * @param basicZombie name of the zombie
   * @return full file path for the enemies properties file
   */
  @Override
  public String getEnemyPropertiesFilePath(String basicZombie) {
    return "src/resources/enemies/" + basicZombie + ".properties";
  }

  /**
   * gets the resource path map for the general app
   * @return map of the resoure paths maps for the objects in the game
   */
  @Override
  public Map<String, String> getResourcePathMap() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/general/resourcePathMaps");
    return makeStringMap(resourceBundle);
  }

  /**
   * gets the game status map for a given game
   * @param gameName game to check
   * @return map of metric -> value for the game status
   */
  @Override
  public Map<String, String> getGameStatus(String gameName) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/scoring/gameName");
    return makeStringMap(resourceBundle);
  }

  //
//  private Map<String, String> makeStringMap(ResourceBundle bundle) {
//    Map<String, String> propertiesMap = new HashMap<>();
//    for (String key : bundle.keySet()) {
//      propertiesMap.put(key, bundle.getString(key));
//    }
//    return propertiesMap;
//  }
//
//  private Map<String, String[]> makeStringArrayMap(ResourceBundle bundle) {
//    Map<String, String[]> propertiesMap = new HashMap<>();
//    for (String key : bundle.keySet()) {
//      propertiesMap.put(key, bundle.getString(key).split("\\,"));
//    }
//    return propertiesMap;
//  }

  /**
   * gets the level properties files for the name as a map of index -> filename
   * @param gameName name of game
   * @return map of index -> level properties file name
   */
  @Override
  public Map<Integer, String> getLevelPropertyFileNamesForGame(String gameName) {
    return makeIntegerMap(ResourceBundle.getBundle("resources/games/" + gameName));
  }

  private Map<Integer, String> makeIntegerMap(ResourceBundle bundle) {
    Map<Integer, String> propertiesMap = new HashMap<>();
    for (String key : bundle.keySet()) {
      propertiesMap.put(Integer.valueOf(key), bundle.getString(key));
    }
    return propertiesMap;
  }

  /**
   * gets the properties for a level
   * @param levelName level to search for
   * @return map of level property -> level value
   */
  @Override
  public Map<String, String> getLevelProperties(String levelName) {
    ResourceBundle levelResourceBundle = ResourceBundle.getBundle("resources/levels/" + levelName);
    return makeStringMap(levelResourceBundle);
  }

  /**
   * gets the variation properties resource bundle
   * @param variationSelection variation name
   * @return resource bundle for the given variation
   */
  public ResourceBundle getVariationProperties(String variationSelection) {
    return ResourceBundle.getBundle("resources/variations/" + variationSelection);
  }

  /**
   * gets the CSS for the given theme
   * @param theme theme name
   * @return css file path for the theme
   */
  @Override
  public String getCSS(String theme) {
    return String.format("resources/visual/css/%s" + ".css", theme);
  }

  /**
   * gets the map of game objects -> images for a theme
   * @param theme theme choice
   * @return  map of game objects -> images
   * @throws FileNotFoundException
   */
  @Override
  public Map<String, Image> getComponentImageMap(String theme) throws FileNotFoundException {
    ResourceBundle imageFileNames = ResourceBundle
        .getBundle("resources/visual/themes/" + theme.toLowerCase());
    Map<String, Image> imageMap = new HashMap<>();
    Enumeration<String> imageKeys = imageFileNames.getKeys();
    while (imageKeys.hasMoreElements()) {
      String key = imageKeys.nextElement();
      String fileName = imageFileNames.getString(key).replace("\"", "");
      String path = "data/images/" + fileName;
      InputStream inputstream = new FileInputStream(path);
      Image image = new Image(inputstream);
      imageMap.put(key, image);
    }
    return imageMap;
  }

  /**
   * gets the component sizing for the front end
   * @return map of sizing for the front end
   */
  @Override
  public Map<String, Double> getComponentSizing() {
    ResourceBundle componentSizingResource = ResourceBundle
        .getBundle("resources/visual/" + COMPONENT_SIZE_PROPERTIES);
    Map<String, Double> componentSizing = new HashMap<>();
    Enumeration<String> components = componentSizingResource.getKeys();
    while (components.hasMoreElements()) {
      String key = components.nextElement();
      componentSizing.put(key, Double.valueOf(componentSizingResource.getString(key)));
    }
    return componentSizing;
  }

  /**
   * gets the images for the given plants for this theme
   * @param plantNames names of the plants
   * @param theme theme selected
   * @return map of plant -> image for given theme
   * @throws FileNotFoundException
   */
  @Override
  public Map<String, Image> getPlantImageMap(String[] plantNames, String theme)
      throws FileNotFoundException {
    Map<String, Image> allImages = getComponentImageMap(theme);
    Map<String, Image> plantMap = new HashMap<>();
    for (String plantName : plantNames) {
      plantMap.put(plantName, allImages.get(plantName + "Seed"));
    }
    return plantMap;
  }

  /**
   * gets the grid CSV path
   * @param gridCSV csv name
   * @return full grid csv path
   */
  @Override
  public String getGridCSV(String gridCSV) {
    return "data/grids/" + gridCSV;
  }

  /**
   * gets the CSV path
   * @param zombieBoardCSVPath csv name
   * @return full csv path
   */
  @Override
  public String getWaveCSV(String zombieBoardCSVPath) {
    return "data/waves/" + zombieBoardCSVPath;
  }

  /**
   * gets the enemy name map used by the reader to take in the wave csv
   * @param enemyMapName name of the enemy map in the resources file
   * @return map of enemy name -> enemy class name
   */
  @Override
  public Map<String, String> getEnemyNameMap(String enemyMapName) {
    ResourceBundle enemyMap = ResourceBundle.getBundle("resources/enemyMaps/" + enemyMapName);
    return makeStringMap(enemyMap);
  }

  private Map<String, String> makeStringMap(ResourceBundle bundle) {
    Map<String, String> propertiesMap = new HashMap<>();
    for (String key : bundle.keySet()) {
      propertiesMap.put(key, bundle.getString(key));
    }
    return propertiesMap;
  }

  private Map<String, String[]> makeStringArrayMap(ResourceBundle bundle) {
    Map<String, String[]> propertiesMap = new HashMap<>();
    for (String key : bundle.keySet()) {
      propertiesMap.put(key, bundle.getString(key).split("\\,"));
    }
    return propertiesMap;
  }
}
