package controller.Exceptions;

public class ReaderExceptions {

  public static class IncorrectCSVFormat extends Exception {

    /**
     * thrown if CSV file has rows and cols that do not match
     * @param filepath - file path referenced for the CSV
     */
    public IncorrectCSVFormat(String filepath) {
      super(String.format(
          "The chosen initial state file %s has data that doesn't match it's row and column properties",
          filepath));
    }
  }
}
