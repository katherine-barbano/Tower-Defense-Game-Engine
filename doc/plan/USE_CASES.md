### First Sprint: 
1. User clicks ‘Start’ and a game is initialized, with a grid of components/towers and enemy data is read. 
2. User clicks on a tower, and the tower highlights to show what the player is placing
3. The user clicks on an empty grid component, and a tower is placed in that spot, bank amount updates 
4. The user clicks on a grid component already filled with a tower, exception is thrown 
5. User tries to place a tower with insufficient sun, exception is thrown in the backend 
6. Enemy crosses left border of the screen (player loses)
7. All enemies are defeated in a normal variation game (player wins)
8. An enemy is read in from a data file 
9. Projectile hits enemy and enemy dies 
10. Tower shoots projectile 
11. An enemy encounters a tower and starts eating it 
12. Sunflower makes sun and the bank updates accordingly 
13. User changes the variation
14. User tries to place a tower in a blocked location and exception is thrown
15. User wins a level and progresses to next level within the same variation

### Later Sprints: 
16. The user creates a new variation of game, inputting image and styling choices for the game, which is then initialized. 
17. The user makes a level, setting the win/lose conditions, the number of enemies, grid size
18. The user clicks the pause button and the game is paused.
19. The user makes a new type of tower
20. The user’s score surpassed the global high score, and the global high score is updated and the user is notified.  
21. User tries to load a properties file in a format different from the required data file, throwing an exception
22. Player creates a new tower from a click on the queue
23. Player saves game 
24. Player loads game 
25. User changes color mode of the view to different theme
26. User changes the language at the beginning of the game on the splashscreen
27. Jumping enemy collides with tower and jumps to next cell
28. A cherry bomb is placed and explodes after X amount of time
29. Time runs out and the score is lower than what is needed to surpass to go to the next level (player loses)
30. A user digs up a tower to remove it

### Uses cases to mock for PLAN
1. The user clicks on an empty grid component, and a tower is placed in that spot, bank amount updates 
2. User tries to place a tower with insufficient sun, exception is thrown in the backend 
3. Enemy crosses left border of the screen (player loses)
4. User wins a level and progresses to next level within the same variation