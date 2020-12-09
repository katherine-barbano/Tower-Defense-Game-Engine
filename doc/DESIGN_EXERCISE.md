# OOGA Lab Discussion
## Names and NetIDs
Katherine Barbano (kab134), Alex Chao (ac590), Heather Grune (hlg20),
Priya Rathinavelu (plr11), Megan Richards (mer91)


## Fluxx

### High Level Design Ideas

We would include interfaces for Play, Rule, Goal, Action, Ungoal.

There would also be a Card interface that is implemented by abstract classes
for RuleCard and GoalCard.

The Player class would have a hand list of the Cards they can play.

The Game class has the current active Goals and Ungoals.


### CRC Card Classes

This class's purpose or value is to manage something:
```java
 public class Player {
     private Hand myHand;
     public void playCard(String cardName);
     public void choosePlayer(Player player);
 }
```

This class's purpose or value is to be useful:
```java
 public class Game {
     private void checkGameWon();
     private void checkGameLost();
 }
```

### Use Cases

### Use Cases

 * A player plays a Goal card, changing the current goal, and wins the game.
 ```java
game.updateGoal();

public void step(double elapsedTime) {
  if(isWon())
    winGame();
}
 ```

 * A player plays an Action card, allowing him to choose cards from another player's hand and play them.
 ```java
player1.playCard(action);
game.getPlayers();
player1.choosePlayer(player2);
player2hand.getCardNames();
player1.choose(cardName);
player2.playCard(cardName);

```
