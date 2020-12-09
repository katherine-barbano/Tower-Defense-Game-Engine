Final
====

This project implements a tower defense game, modelled after the game Plants vs Zombies.

Names: Katherine Barbano (kab134), Alex Chao (ac590), Heather Grune (hlg20),
Priya Rathinavelu (plr11), Megan Richards (mer91)


### Timeline

Start Date: 10/25/2020

Finish Date: 11/19/2020

Hours Spent: around 130 per person

### Primary Roles
* Katherine - Model : GamePlay (managing the backend grids and collisions, interaction between GameComponents)
* Alex - Model: TowerGridComponents (Different plants), View: (Queue version of displaying plant selections), Controller: (Win/Loss conditions)
* Heather - View (Frontend - Displaying backend objects, splash screens, custom themes)
* Megan - Controller (General progression/workflow of the game, creating levels, reading in all data files, validation, saving)
* Priya - Model: EnemyGridComponents (Different Enemies, different Projectiles)

### Resources Used

Used DukeApplicationTest class provided by Professor Duvall for testing JavaFX.

For reflection (found within the GameComponentFactory abstract class), the following website was used: https://java2blog.com/invoke-constructor-using-reflection-java/ provided on course website
    
Images for different zombies/plants found from : https://plantsvszombies.fandom.com/wiki/Main_Page
### Running the Program

For collision between two rectangle: https://www.baeldung.com/java-check-if-two-rectangles-overlap


Main class: src/controller/GamePlay/Main

Data files needed: The data files needed to run this game are the main level properties files, which indicate different features about each level that can be played. These properties files
will reference other CSV files (specifically a file for the initial set up of the grid, and a file that represents the wave of enemies that will be created). These files should be within the
data/grids and data/waves folder, respectively.

Features implemented:

Model:
* Three APIs for communication between controller-model, view-model, and any object that needs to know the status of GameComponents.
* An implementation of the game board being a grid vs a path, where enemies can either come in from any side on the right in the grid or move only along the path.
* A grid structure (PlayableArea), which stores the towers/players, enemies/zombies and projectiles, that handles moving, updating, and
removing the different components.
* Movement of enemies to follow a path, or to move from right to left across the screen (without needing to know which type of implementation it is).
* Enemies with different powers (enemy that shoots projectiles, enemy that can "explode" causing nearby states to
catch on fire)
* Towers with different powers (towers that accumulate sun over time, towers that can shoot projectiles, towers
that can explode to cause nearby states catch on fire)
* Projectiles that can travel differently (Can travel the full length of the grid, some have limited distance!)
* Range detection of projectiles, so projectiles are only shot when an enemy is in range (for a tower) or a tower is in range (for an enemy)
* New enemies/towers with the powers above can easily be instantiated with properties files - these files are flexible
so that they can be created with different damage, speed, projectile type, and direction values
* Enemies, Towers implement several interfaces that allow for their unique properties, such as being able to inflict move,
have an action, store health, and shoot.
* Several factories used for instantiating the creation of various objects, such as different Towers,
different Enemies, different Projectiles, etc
  * A hierarchy within factory, specifically for the TowerComponentFactory and the EnemyComponentFactory
* An extensive Collision checker which checks for any collisions between any of the components within the backend, such as
Projectile-projectile, tower-enemy, or a health object- fire.
* Several APIs used for communicating the important information needed for the frontend to be able to display the information and
updates from the backend model.
* A GameStatus object which keeps track of the status information, such as elapsed time, score, amount of sun.

Controller:
* Different conditions representing the various loss and win conditions for each level
* Extensive reader classes that handle reading in various properties files and initializing the appropriate
features in the game. This also performs error checking, and can handle invalid property files.
* TowerDefenseCustomHandlers that deal with the custom features of the game that the player chooses to use.
* Various "Manager" classes that help with the set up and initialization of the game and encapsulate state/functionality.  
  * TowerDefenseManager initializes the backend level depending on the version of the game based on the files listed within the properties files- this includes
   enemy wave generation and initial board set up
  * ApplicationManager that handles the general timeline and progression of the game animation, alternating between the set up portion and the game portion of the app. 
  * BasicGameManager that handles transitions between game levels and timings, specifically by checking when a level has been won and moving to the next level
  * BasicSetUpManager that deals with the first set up of the game based on the inputs from the splash screen selections
  * ConditionsManager which handles the win and loss conditions for the game
  * High score that is specific to the game and includes the user’s name
  * Backend writer classes for custom game, level, theme, grids, waves of enemies. 

View:
* A StatusView which shows the amount of sun, the user's score, and the elapsed time. There is also a progress bar
representing the waves of incoming enemies. All of these update as the user progresses through the level.
* PlantSelection - this can either be a panel (where all possible plants are displayed and the user can click on which plant they
want and then the open grid cell where it should be placed) or a queue (a display of usable plants that update as a user selects one
to plant)
* Dig - a digging tool, shown as a shovel, that allows the user to "dig" up one of the plants already placed on the grid
* Play/pause button
* HomeButton
* The grid of all game components that updates as component updates/interactions occur in the backend (As enemies lose their health,
they become more transparent). The images for the components can be changed based on which theme the user selects.
* SetupScreen allows the user to create custom games, custom levels, custom themes, custom grid files, custom enemy waves
* Game Winning and Game Losing screens

### Notes/Assumptions

Assumptions or Simplifications:
* Model
- Fire and projectiles only damage/kill towers if they are shot from enemies, and vice versa. You can’t hurt a game component that’s on your same side.
- Projectiles only get shot from enemies if there is a tower in the range of an enemy. For example, if the enemy shoots upwards, if there is a tower above the enemy, it will shoot. Otherwise it won’t shoot. This applies vice versa for towers shooting at enemies.

* Controller
-Custom win/loss only can have one selection 
- if your game has an unsupported level you have to restart (Get sent back to the home screen)
- user must create game with path games in the properties files
- Assume a fair number of key names from properties files, especially setupScreenPropertiesMap, setupScreenOptions

* View
- Theme is unique to each level.  User must specify theme in custom game creation
- Custom games are only compatible with GameWithGrid not game with path (no dropdown selection for this variation)
- Grid cells are squares, are sized so that the grid fits inside the allotted space in both dimensions (would rather not take up the 
entire space than go outside the window)
- The enemyGrid cell dimensions are calculated using the dimensions of the playerGridCells


Interesting data files: The data file representing the initial grid with a path shows a different
variation of the game where zombies follow a path when moving, rather than entering in every row.

Known Bugs:
* Some of the view tests only work on Windows, not on Macs (specifically because of clickOn calls not producing a click)
* For the grid with path implementation (mentioned above) there is a small bug with movement, where the zombie slightly goes off path when making a turn, before
returning back to the path and continuing forward. This is due to the fact that the position of zombies within the grid is tied to the top left corner of the image of the zombie,
making it seem that it has gone off the path even if the zombie's actual position is an open state on the path.

Extra credit:
* Level Customization - When setting up the initial game, there is an option to create a custom level. Here the user can
indicate how many levels they wish to create, as well as which variations they want for each. They can also indicate
which win and loss conditions they would like for each level. Here, the user can choose .csv files that represent the
initial grid set up as well as the wave of enemies.

* Visual Customization - When setting up the initial game, there is also an option to select which images the user would
like to be included for the various enemies/towers present within the game.

* High scores - high scores are recorded for each round the user plays, and are based on faster game times or higher scores. 

Cheat Keys:
* SPACE: pause/play game
* RIGHT ARROW: increase sun
* UP ARROW: increase game speed
* DOWN ARROW: decrease game speed
* R: reset level

### Impressions

This project illustrated the planning and organization needed to create a functional and complicated game, while still
remaining as flexible as possible. Although we had a very detailed plan when beginning this assignment, as we actually
implemented the game and thought about what features needed to be included, we realized some of the design flaws within our
earlier plans. However, after refactoring and reconsidering our design structure for certain features, we were able to create a more
flexible game that we could easily update to represent our newer features.


