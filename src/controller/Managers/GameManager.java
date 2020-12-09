package controller.Managers;

import com.opencsv.exceptions.CsvException;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import java.io.IOException;

/**
 * Defines the behavior that a game manager would need in order to have this be extendable to any
 * type of Game.
 *
 * @author Megan Richards
 */
public interface GameManager {

  /**
   * Updates the game according to each level's specifications
   * @param animationSpeed
   * @throws ReaderExceptions.IncorrectCSVFormat
   * @throws IOException
   * @throws CsvException
   * @throws GeneralExceptions.NullNameException
   */
  void update(int animationSpeed)
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, GeneralExceptions.NullNameException;

  /**
   * Checks to see if the game has been won
   * @throws ReaderExceptions.IncorrectCSVFormat
   * @throws IOException
   * @throws CsvException
   * @throws GeneralExceptions.NullNameException
   */
  void checkGameStatus()
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, GeneralExceptions.NullNameException;
}
