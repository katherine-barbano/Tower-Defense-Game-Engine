package view.GameView;

import controller.Alerts;
import controller.GamePlay.Timing;
import controller.Managers.AppControl;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.gameplay.MVCInteraction.API.GameDisplayAPI;
import view.GameView.GameControlPanel.GameControlPanel;
import view.GameView.GridPanel.GridPanel;
import view.GameView.PlantSelection.PlantHandler;
import view.GameView.PlantSelection.PlantSelection;
import view.GameView.StatusDisplay.StatusBar;

/**
 * GameView Implement the GameViewAPI.  It is responsible for creating and displaying all view
 * components (GridPanel, StatusBar,PlantSelection, GameControlPanel).
 * @author Heather Grune (hlg20).
 */
public class GameView implements GameViewAPI {

  public static final String RESOURCES = "resources/";
  public static final String HEIGHT_RATIO_DELIMITER = "HeightRatio";
  public static final String WIDTH_RATIO_DELIMITER = "WidthRatio";
  private final String cssFile;
  private final GameDisplayAPI myGame;
  private final PlantHandler myPlantCreationHandler;
  private final Map<String, Image> myImages;
  private final Map<String, Image> myPlantImages;
  private final Map<String, Double> myComponentSizing;
  private final Alerts alerts = new Alerts();
  private final Timing myTiming;
  private final CheatKeyHandler myKeyHandler;
  private final String plantSelectionType;
  private final String[] myStatuses;
  private final AppControl myAppControl;
  private VBox myRoot;
  private StatusBar myStatusBar;
  private GridPanel myGrid;
  private PlantSelection myPlantSelection;
  private GameControlPanel myGameControlPanel;
  private int myHeight;
  private int myWidth;


  public GameView(GameDisplayAPI game, Map<String, Image> images, String themeCSSfilePath,
      Map<String, Double> componentSizing, Map<String, Image> plantMap, Timing timing,
      String plantSelection, String[] statuses, AppControl appControl) {
    myGame = game;
    myComponentSizing = componentSizing;
    cssFile = themeCSSfilePath;
    myPlantCreationHandler = new PlantHandler(game);
    myImages = images;
    myPlantImages = plantMap;
    myTiming = timing;
    plantSelectionType = plantSelection;
    myKeyHandler = new CheatKeyHandler(game.getGameStatusAPI(), timing);
    myStatuses = statuses;
    myAppControl = appControl;
  }

  /**
   * Create the scene to be displayed on the main stage.  Create and add all main view components to the
   * root.
   * @param height Height of the scene
   * @param width Width of the scene
   * @return the Scene to be displayed on main stage.
   */
  @Override
  public Scene setupScene(int height, int width) {
    myWidth = width;
    myHeight = height;
    createUIComponents();
    addUIComponentsToRoot();
    Scene scene = new Scene(myRoot, myWidth, myHeight);
    scene.getStylesheets().add(cssFile);
    scene.setOnKeyPressed(e -> myKeyHandler.handleKeyInput(e.getCode()));
    return scene;
  }

  /**
   * Update the enemyGridView, PlayerGridView, StatusBar, and PlantSelection each time the
   * controller steps.
   */
  @Override
  public void step() {
    myGrid.updateEnemyGrid();
    myGrid.updatePlayerGrid();
    myStatusBar.update();
    myPlantSelection.updatePlantSelection();
  }

  private void createUIComponents() {
    myStatusBar = new StatusBar(myGame.getGameStatusAPI(), myImages,
        getComponentHeight(StatusBar.class), myStatuses);
    myPlantSelection = choosePlantSelectionType(plantSelectionType);
    myGrid = new GridPanel(myGame, myImages, getComponentHeight(GridPanel.class),
        getComponentWidth(GridPanel.class), myPlantCreationHandler);
    myGameControlPanel = new GameControlPanel(myImages, getComponentHeight(GameControlPanel.class),
        myPlantCreationHandler, myTiming, myAppControl);
  }

  private void addUIComponentsToRoot() {
    myRoot = new VBox();
    myRoot.getStyleClass().add("root");
    myRoot.getChildren().add(myStatusBar);
    addMainGridAndBank();
    myRoot.getChildren().add(myGameControlPanel);
  }

  private void addMainGridAndBank() {
    HBox mainRow = new HBox();
    mainRow.getChildren().add(myPlantSelection.getPlantSelect());
    mainRow.getChildren().add(myGrid);
    myRoot.getChildren().add(mainRow);
  }


  private double getComponentHeight(Class<?> myClass) {
    double ratio = parseRatioFromResources(myClass, HEIGHT_RATIO_DELIMITER);
    return myHeight * ratio;
  }

  private double getComponentWidth(Class<?> myClass) {
    double ratio = parseRatioFromResources(myClass, WIDTH_RATIO_DELIMITER);
    return myWidth * ratio;
  }

  private double parseRatioFromResources(Class<?> myClass, String dimensionDelimiter) {
    return myComponentSizing.get(myClass.getSimpleName() + dimensionDelimiter);
  }

  private PlantSelection choosePlantSelectionType(String selectionType) {
    Class<?> selection;
    PlantSelection plantSelection = null;
    try {
      selection = Class.forName("view.GameView.PlantSelection." + selectionType);
      plantSelection = (PlantSelection) selection
          .getConstructor(Map.class, PlantHandler.class)
          .newInstance(myPlantImages, myPlantCreationHandler);
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      alerts.makeAlert(e.getMessage());
    }
    return plantSelection;
  }

}
