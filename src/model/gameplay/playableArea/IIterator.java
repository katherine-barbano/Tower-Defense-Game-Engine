package model.gameplay.playableArea;

/***
 * Iterates through data using the Iterator design pattern.
 * @param <T> Which class should be iterated through (might correspond to a subclass like Movable,
 *           or just a GameComponent)
 */
public interface IIterator<T> {

  /***
   * Call this before calling .next to determine if there is any items left in the iterator.
   * @return true if there is another item in the iterator
   */
  boolean hasNext();

  /***
   * Gets the next item to be iterated through. Returns type T instead of a GameComponent
   * so that it is possible to cast the object to a subclass for a TypeIterator, so casting
   * does not have to occur in GameControl.
   * @return The next item to be iterated through
   */
  T next();
}
