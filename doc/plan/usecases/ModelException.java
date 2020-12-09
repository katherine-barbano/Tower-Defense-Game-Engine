public class ModelException extends RuntimeException {
  //custom exception mocked for model. Contains the 2 standard ModelException constructors
  private String message;
  public String getMessage() {
    return message;
  }
}