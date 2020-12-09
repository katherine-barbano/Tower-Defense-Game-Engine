package model.gameplay.gameplayResources;

/***
 * Used by lambda functions throughout the code that need to pass around any 4 types.
 * @param <T> Generic type
 * @param <T2> Generic type
 * @param <T3> Generic type
 * @param <T4> Generic type
 *
 * @author Katherine Barbano
 */
@FunctionalInterface
public interface QuadConsumer<T, T2, T3, T4> {

  /***
   * Call this method to enact a lambda function
   * @param type1 Generic type
   * @param type2 Generic type
   * @param type3 Generic type
   * @param type4 Generic type
   */
  void accept(T type1, T2 type2, T3 type3, T4 type4);
}
