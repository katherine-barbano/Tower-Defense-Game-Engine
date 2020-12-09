/***
 * API in view that communicates with controller and view. Used only for the splash screen for setup, before
 * the playable game is begun.
 */
public interface SplashView{

  /***
   * Used by controller, so controller can know which variation of the game to create.
   * @return Class type of game variation that was selected. Used for reflection
   */
  public abstract Class getGameVariationSelected();
}