///***
// * Located in Controller.
// *
// * User tries to place a tower with insufficient sun, exception is thrown in the backend
// */
//public class placeTowerWithoutSufficientSunUseCase {
//  private Game modelAPI;
//  private GameView viewAPI;
//
//  public placeTowerWithoutSufficientSunUseCase() {
//    modelAPI = new GameConcrete();
//    viewAPI = new GameViewConcrete();
//  }
//
//  //same method as placeTowerLegelUseCase
//  //used in each step method of controller
//  private void monitorTowerCreation() {
//    //first check if a new tower was created this step in the controller
//    //if yes, get info from view about the tower type and position created, then pass it to model api
//    if(viewAPI.getTowerWasCreated()) {
//      Class towerType = viewAPI.getTowerTypeToCreate();
//      Position position = viewAPI.getNewTowerPlacementPosition();
//      tryToCreateTower();
//    }
//  }
//
//  private void tryToCreateTower() {
//    try {
//      modelAPI.createTower(towerType, position);
//    }
//    catch(ModelException e) {
//      handleTowerCreationInvalid(e.getMessage());
//    }
//  }
//
//  private void handleTowerCreationInvalid(String message) {
//    viewAPI.displayControllerExceptionAsPopup(message);
//  }
//}