package model.gameComponents;


/**
 * GameComponent is the parent interface of all of the moving parts of the back end, including the
 * towers, enemies, and projectiles. This allows them to be stored together in the same data structure
 * to organize their positions.
 * @author alex chao
 */
public interface GameComponent {

  String getName();

  String toString();

  int getWidth();

  int getHeight();
}
