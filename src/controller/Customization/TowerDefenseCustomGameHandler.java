package controller.Customization;


import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.Validation.Validation;
import controller.DataAccess.Writer;
import controller.DataAccess.towerDefenseResourceAPI;
import controller.Exceptions.GeneralExceptions.EmptySelectionException;
import controller.Exceptions.GeneralExceptions.NullNameException;
import controller.Exceptions.GeneralExceptions.UnsupportedLevelException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates custom games with properties specific to tower defense
 *
 * @author Megan Richards
 */

public class TowerDefenseCustomGameHandler implements CustomGameHandler {

  private final towerDefenseResourceAPI resourceAPI = new ResourceAccessor();
  private final Validation validation = new Validation(resourceAPI);
  private final Writer writer = new Writer(resourceAPI.getResourcePathMap());
  private final Map<Integer,String> myCustomLevels = new HashMap<>();
  private String myGameName;

  /**
   * @see CustomGameHandler#makeGame(List, String)
   * @param levels
   * @param name
   * @throws NullNameException
   * @throws EmptySelectionException
   * @throws UnsupportedLevelException
   * @throws IOException
   */
  @Override
  public void makeGame(List<String> levels, String name)
      throws NullNameException, EmptySelectionException, UnsupportedLevelException, IOException {
    validation.checkCustomGame(levels, name);
    writer.WriteNewGame(levels, name);
    myGameName=name;
  }

  /**
   * @see CustomGameHandler#makeLevel(Map, String, int)
   * @param selections
   * @param name
   * @param index
   * @throws NullNameException
   * @throws IOException
   */
  @Override
  public void makeLevel(Map<String, String> selections, String name, int index)
      throws NullNameException, IOException {
    validation.checkCustomLevel(name);
    writer.WriteNewLevel(selections, name);
    myCustomLevels.put(index, name);
  }

  @Override
  public String getLevelName(int index) {
    return myCustomLevels.get(index);
  }

  @Override
  public String getGameName() {
    return myGameName;
  }


}
