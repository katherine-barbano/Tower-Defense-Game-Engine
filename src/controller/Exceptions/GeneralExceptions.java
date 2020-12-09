package controller.Exceptions;

/**
 * Exceptions that are used when validating files
 */
public class GeneralExceptions {

  public static class NullNameException extends Exception {

    /**
     * used in the selection fields for the set up to ensure the user inputs a name
     */
    public NullNameException() {
      super(
          "The chosen selection has a null name");
    }
  }

  public static class EmptySelectionException extends Exception {

    /**
     *  used in the selection fields for the set up to ensure the user inputs a name
     */
    public EmptySelectionException() {
      super("Please make a selection");
    }
  }

  public static class UnsupportedLevelException extends Exception {

    /**
     * Thrown if the level attempting to be initialized has no properties file
     * @param level - the level to be initialized
     */
    public UnsupportedLevelException(String level) {
      super(String.format("The level %s is not supported", level));
    }
  }

  public static class EmptyCSVException extends Exception {

    /**
     * thrown if the CSV is empty
     * @param fileName - the CSV file being checked
     */
    public EmptyCSVException(String fileName) {
      super(String.format("The CSV %s is empty", fileName));
    }
  }

  public static class NoSelectionException extends Exception {

    /**
     * used in the set up to ensure the user makes all selections.
     */
    public NoSelectionException() {
      super(String.format("Please make a selection"));
    }
  }

  public static class InvalidResourceAccessException extends Exception {

    /**
     * Thrown if a resource bundle for a game is not found.
     */
    public InvalidResourceAccessException() {
      super(String.format("This game has an invalid resource access"));
    }
  }


}
