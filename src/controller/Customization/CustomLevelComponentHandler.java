package controller.Customization;

import com.opencsv.exceptions.CsvException;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Defines the behavior of creating a custom Level
 *
 * @author Megan Richards
 */
public interface CustomLevelComponentHandler {

  void makeCustomGrid(File file)
      throws GeneralExceptions.EmptyCSVException, CsvException, IOException;

  void makeCustomWaveCSV(File file)
      throws ReaderExceptions.IncorrectCSVFormat, CsvException, IOException, GeneralExceptions.NullNameException;

  void makeCustomTheme(Map<String, File> componentImages, String name)
      throws GeneralExceptions.NullNameException, GeneralExceptions.EmptySelectionException, IOException;

  void makeCustomVariation(List<String> WinConditions, List<String> LossConditions,
      String name)
      throws GeneralExceptions.NullNameException, GeneralExceptions.EmptySelectionException, IOException;

  String getGridSelectionName();

  String getEnemyWaveSelectionName();

  String getThemeSelectionName();

}
