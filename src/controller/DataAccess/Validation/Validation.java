package controller.DataAccess.Validation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import controller.DataAccess.towerDefenseResourceAPI;
import controller.Exceptions.GeneralExceptions.EmptyCSVException;
import controller.Exceptions.GeneralExceptions.EmptySelectionException;
import controller.Exceptions.GeneralExceptions.NullNameException;
import controller.Exceptions.GeneralExceptions.UnsupportedLevelException;
import controller.Exceptions.ReaderExceptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class defines the validation for making a custom game
 *
 * @author Megan Richards
 */
public class Validation {

  towerDefenseResourceAPI resources;

  public Validation(towerDefenseResourceAPI resourceAPI) {
    resources = resourceAPI;
  }

  private void isNameNull(String name) throws NullNameException {
    if (name.equals("")) {
      throw new NullNameException();
    }
  }

  /**
   * Checks to make sure that all of the levels in the game are actual levels
   * @param levelSelections
   * @param name
   * @throws NullNameException
   * @throws EmptySelectionException
   * @throws UnsupportedLevelException
   */
  public void checkCustomGame(List<String> levelSelections, String name)
      throws NullNameException, EmptySelectionException, UnsupportedLevelException {
    isNameNull(name);
    if (levelSelections.equals(new ArrayList<>())) {
      throw new EmptySelectionException();
    }
    for (String level : levelSelections) {
      try {
        resources.getLevelProperties(level);
      } catch (Exception e) {
        //throw new UnsupportedLevelException(level);
      }
    }
  }

  /**
   * Checks to see that the level's name is properly formatted
   * @param name
   * @throws NullNameException
   */
  public void checkCustomLevel(String name) throws NullNameException {
    isNameNull(name);
  }

  /**
   * Checks to see that the rows and columns match in the CSV
   * @param s
   * @throws ReaderExceptions.IncorrectCSVFormat
   * @throws IOException
   * @throws CsvException
   * @throws NullNameException
   */
  public void checkWaveCSV(String s)
      throws ReaderExceptions.IncorrectCSVFormat, IOException, CsvException, NullNameException {
    File csv = new File(s);
    checkWaveCSVFile(csv);
  }

  public void checkWaveCSVFile(File file)
      throws IOException, CsvException, ReaderExceptions.IncorrectCSVFormat, NullNameException {
    InputStream input = new FileInputStream(file);
    CSVReader reader = new CSVReader(new InputStreamReader(input));
    List<String[]> data = reader.readAll();
    String[] csvCharacteristics = data.get(0);
    if (Integer.parseInt(csvCharacteristics[0]) != data.size() - 1) {
      throw new ReaderExceptions.IncorrectCSVFormat(file.getName());
    }
    if (Integer.parseInt(csvCharacteristics[1]) != data.get(1).length) {
      throw new ReaderExceptions.IncorrectCSVFormat(file.getName());
    }
  }

  /**
   * Checks to see that the custom grid file is a CSV
   * @param file
   * @throws IOException
   * @throws CsvException
   * @throws EmptyCSVException
   */
  public void checkCustomGridCSV(File file)
      throws IOException, CsvException, EmptyCSVException {
    InputStream input = new FileInputStream(file);
    CSVReader reader = new CSVReader(new InputStreamReader(input));
    List<String[]> data = reader.readAll();
    try {
      data.get(0);
    } catch (Exception e) {
      throw new EmptyCSVException(file.getName());
    }
  }

  /**
   * Checks to see that the custom theme has a valid name and valid selections
   * @param selections
   * @param name
   * @throws EmptySelectionException
   * @throws NullNameException
   */
  public void checkCustomTheme(Map<String, File> selections, String name)
      throws EmptySelectionException, NullNameException {
    if (name.equals("")) {
      throw new NullNameException();
    }
    if (selections.isEmpty()) {
      throw new EmptySelectionException();
    }
  }

  /**
   * Checks to see that the custom variations are properly formatted
   * @param WinSelections
   * @param lossSelections
   * @param name
   * @throws EmptySelectionException
   * @throws NullNameException
   */
  public void checkCustomVariations(List<String> WinSelections, List<String> lossSelections,
      String name) throws EmptySelectionException, NullNameException {
    if (name.equals("")) {
      throw new NullNameException();
    }
    if (WinSelections.isEmpty() || lossSelections.isEmpty()) {
      throw new EmptySelectionException();
    }
  }

}
