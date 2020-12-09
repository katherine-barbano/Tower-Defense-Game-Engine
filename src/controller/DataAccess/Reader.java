package controller.DataAccess;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import controller.DataAccess.Validation.Validation;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import model.gameComponents.SingleState;

/**
 * This class reads in information from resource files. It relies on Validation to validate the files
 * before attempting to read them
 *
 * @author Megan Richards
 */
public class Reader {

  private final Validation validation = new Validation(new ResourceAccessor());

  /**
   * Returns the enemies that are represented in a file
   * @param s
   * @param enemiesToClassNames
   * @return list of enemies
   * @throws IOException
   * @throws CsvException
   * @throws ReaderExceptions.IncorrectCSVFormat
   * @throws GeneralExceptions.NullNameException
   */
  public List<List<String>> getEnemies(String s, Map<String, String> enemiesToClassNames)
      throws IOException, CsvException, ReaderExceptions.IncorrectCSVFormat, GeneralExceptions.NullNameException {
    validation.checkWaveCSV(s);
    InputStream input = new FileInputStream(s);
    CSVReader reader = new CSVReader(new InputStreamReader(input));
    List<String[]> data = reader.readAll();
    List<String[]> states = data.subList(1, data.size());
    List<List<String>> enemies = new ArrayList<>();
    for (String[] row : states) {
      List<String> classRow = new ArrayList<>();
      for (String state : row) {
        if (state.equals("0")) {
          classRow.add("0");
        } else {
          classRow.add(enemiesToClassNames.get(state));
        }
      }
      enemies.add(classRow);
    }
    return enemies;
  }

  /**
   * Returns the initial grid of states for the player grid
   * @param initialBoardCSVFileName
   * @return
   * @throws IOException
   * @throws CsvException
   */
  public List<List<SingleState>> getInitialGrid(String initialBoardCSVFileName)
      throws IOException, CsvException {
    InputStream input = new FileInputStream(initialBoardCSVFileName);
    CSVReader reader = new CSVReader(new InputStreamReader(input));
    List<String[]> data = reader.readAll();
    List<List<SingleState>> initialGrid = new ArrayList<>();
    for (String[] row : data) {
      List<SingleState> gridRow = new ArrayList<>();
      for (String place : row) {
        gridRow.add(getSingleState(place));
      }
      initialGrid.add(gridRow);
    }
    return initialGrid;
  }

  private SingleState getSingleState(String name) {
    return switch (name) {
      case ("E") -> SingleState.EMPTY;
      case ("P") -> SingleState.PATH;
      case ("X") -> SingleState.BLOCKED;
      default -> throw new IllegalStateException("Unexpected value: " + name);
    };
  }

  /**
   * Returns a properties object from a properties file
   * @param filePath
   * @return
   * @throws IOException
   */
  public Properties getProperties(String filePath) throws IOException {
    Properties prop = new Properties();
    InputStream input = new FileInputStream(filePath);
    prop.load(input);
    return prop;
  }


}
