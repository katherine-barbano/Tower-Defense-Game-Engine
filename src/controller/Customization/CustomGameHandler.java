package controller.Customization;

import controller.Exceptions.GeneralExceptions;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Defines the behavior for creating a custom game
 *
 * @author Megan Richards
 */
public interface CustomGameHandler {

  /**
   * Writes a game properties file with the given levels
   * @param levels
   * @param name
   * @throws GeneralExceptions.NullNameException
   * @throws GeneralExceptions.EmptySelectionException
   * @throws GeneralExceptions.UnsupportedLevelException
   * @throws IOException
   */
  void makeGame(List<String> levels, String name)
      throws GeneralExceptions.NullNameException, GeneralExceptions.EmptySelectionException, GeneralExceptions.UnsupportedLevelException, IOException;

  /**
   * Writes a level properties file from the given selections
   * @param selections
   * @param name
   * @param index
   * @throws GeneralExceptions.NullNameException
   * @throws IOException
   */
  void makeLevel(Map<String, String> selections, String name, int index)
      throws GeneralExceptions.NullNameException, IOException;

  String getLevelName(int index);

  String getGameName();

}
