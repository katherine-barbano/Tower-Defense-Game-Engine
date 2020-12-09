package model.gameplay.gameplayResources;

/***
 * Used by lambda functions throughout the code that need to pass around any 3 types.
 * @param <T> Generic type
 * @param <T2> Generic type
 * @param <T3> Generic type
 *
 * @author Katherine Barbano
 */
@FunctionalInterface
public interface TriConsumer<T, T2, T3> {

  /***
   * Call this method to enact a lambda function
   * @param type1 Generic type
   * @param type2 Generic type
   * @param type3 Generic type
   */
  void accept(T type1, T2 type2, T3 type3);
}
