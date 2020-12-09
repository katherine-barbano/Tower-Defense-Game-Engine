package controller.Customization;

import com.opencsv.exceptions.CsvException;
import controller.DataAccess.AppResourceAPI;
import controller.DataAccess.ResourceAccessor;
import controller.DataAccess.Validation.Validation;
import controller.DataAccess.Writer;
import controller.Exceptions.GeneralExceptions;
import controller.Exceptions.ReaderExceptions;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Creates custom levels with properties specific to tower defense
 *
 * @author Megan Richards
 */

public class TowerDefenseCustomLevelComponentHandler implements CustomLevelComponentHandler {

  private final AppResourceAPI resourceAPI = new ResourceAccessor();
  private final Validation validation = new Validation(new ResourceAccessor());
  private final Writer writer = new Writer(resourceAPI.getResourcePathMap());
  private String myGridName = "";
  private String myWaveCSVName = "";
  private String myThemeName = "";

  /**
   * @see CustomLevelComponentHandler#makeCustomGrid(File)
   * @param file
   * @throws GeneralExceptions.EmptyCSVException
   * @throws CsvException
   * @throws IOException
   */
  @Override
  public void makeCustomGrid(File file)
      throws GeneralExceptions.EmptyCSVException, CsvException, IOException {
    validation.checkCustomGridCSV(file);
    writer.WriteNewGrid(file);
    myGridName = file.getName();
  }

  /**
   * @see CustomLevelComponentHandler#makeCustomWaveCSV(File)
   * @param file
   * @throws ReaderExceptions.IncorrectCSVFormat
   * @throws CsvException
   * @throws IOException
   * @throws GeneralExceptions.NullNameException
   */
  @Override
  public void makeCustomWaveCSV(File file)
      throws ReaderExceptions.IncorrectCSVFormat, CsvException, IOException, GeneralExceptions.NullNameException {
    validation.checkWaveCSVFile(file);
    writer.WriteNewWavesCSV(file);
    myWaveCSVName = file.getName();
  }

  /**
   * @see CustomLevelComponentHandler#makeCustomTheme(Map, String)
   * @param componentImages
   * @param name
   * @throws GeneralExceptions.NullNameException
   * @throws GeneralExceptions.EmptySelectionException
   * @throws IOException
   */
  @Override
  public void makeCustomTheme(Map<String, File> componentImages, String name)
      throws GeneralExceptions.NullNameException, GeneralExceptions.EmptySelectionException, IOException {
    validation.checkCustomTheme(componentImages, name);
    writer.WriteNewTheme(componentImages, name);
    myThemeName = name;
  }

  /**
   * @see CustomLevelComponentHandler#makeCustomVariation(List, List, String)
   * @param WinConditions
   * @param LossConditions
   * @param name
   * @throws GeneralExceptions.NullNameException
   * @throws GeneralExceptions.EmptySelectionException
   * @throws IOException
   */
  @Override
  public void makeCustomVariation(List<String> WinConditions, List<String> LossConditions,
      String name)
      throws GeneralExceptions.NullNameException, GeneralExceptions.EmptySelectionException, IOException {
    validation.checkCustomVariations(WinConditions, LossConditions, name);
    writer.WriteNewVariation(WinConditions, LossConditions, name);
  }

  @Override
  public String getGridSelectionName() {
    return myGridName;
  }

  @Override
  public String getEnemyWaveSelectionName() {
    return myWaveCSVName;
  }

  @Override
  public String getThemeSelectionName() {
    return myThemeName;
  }

//  @Override
//  public String getWinConditionSelectionName() {
//    return this.myWinCondition;
//  }
//
//  @Override
//  public String getLossConditionSelectionName() {
//    return this.myLossCondition;
//  }

}
