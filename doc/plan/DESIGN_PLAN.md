###Introduction 

We want to create a grid format tower defense game. The game will be flexible in the winning
and losing conditions, tower properties, placement of towers, enemy properties, 
and enemy formations. We will split up this project into the view, controller, and model, where the 
model and view will not be able to directly communicate with each other. To do this, we hope to not 
only utilize the controller, but also create APIs that can share information between these separate
parts of the project so that the necessary information can be passed to each other. The model will 
be responsible for storing the various "characters" within the game, such as the enemies, towers,
and projectiles/collisions. As changes are made and interactions start to occur, the view will 
need to be updated as needed so that the user can be shown these interactions and react. The view
will also need to keep track of the user's interactions with the screen (clicking on different 
areas of the screen and trying to add new components), in addition to being able to display separate
counters (such as points or time) that can influence the user's decisions within the game. Between
these two areas of the project will be the controller which will be running the general program and 
checking whether the overall goals of the game have been met. 

###Overview 
Responsibilities: 
We want to create various packages for the view, controller, and model. Within the model, we want to 
create an update status/game information package, gameplay package, actors package, enemies interface,
 and actors interface. The status/game package is responsible for keeping track of the points of the 
 player and time left within the gameplay. The gameplay package will keep track of the various 
 actors within the game, such as the enemies and their actions and the towers and their actions. It
  will also store an actor Grid which is a concrete class that holds actors, and creates plants 
  (throws an exception caught in controller if the plant is not valid). There will also be enemy
   Grid Collision class. This class will keep track of projectiles shot from towers and zombies 
 walking. Using this enemy grid class, this module can keep moving the zombies. In addition, this
  class will contain the projectiles fired from the towers and can check for any collisions
 between the zombies and the projectiles. Using these values, this gameplay module will be able 
 to help the controller in knowing whether any of the win/loss conditions have been met. This
 gameplay package will work very closely with the PlayerGridComponents package, which is responsible 
 for containing the Towers interface and a Towers package. The Towers package will keep track of 
 the towers and whether they have died in addition to checking if they’ve fired a projectile. The
  projectile will be tracked with its coordinate position to check for collision. Within this Towers
 package, there will also be the tower subclasses for implementing the various types of towers the
 user can use. Within the PlayerGridComponent subclass, there will also be 2 classes/states to 
 determine if a cell within the player grid is empty or blocked (plant can’t be placed there). 
 Working with this package is the EnemiesGridComponent interface which will track the enemies’ 
 positions. There will also be a general GridReader interface, that both the EnemiesGridComponent 
 interface and PlayerGridComponent interface will use. This will allow both of those interfaces 
 to use the methods needed for extracting the necessary information from the Grid. Within this 
 EnemiesGridComponent package, there should be an Enemies package which will be responsible for 
 storing the various functionally different zombies we want to include in the game. One of the most
important interfaces we will have is the Collidable interface which will deal with any sort of 
collision, such as between the projectile and the zombie or between the zombie and the plant. 

Within the controller, there will be a splash screen controller module and gameplay controller 
module. Within the view, we will keep track of the grid, splashscreen, plant selector panel, and 
HUD. To be more specific, we would like a Main class that will run the program and work with the 
timeline for the animation of the game. One of the two main modules in this package will be the 
splashscreen controller module which will be responsible for managing the main menu that allows the 
user to see their highest score and choose the game variation, language, and dark/light mode. The 
other main module will be a gameplay controller module which will contain the classes for reading in
the files and general data management as well as classes for maintaining the timeline and stepping 
through. 

Within the view, there will be a GridView that takes the GridReader for the enemy grid and for the 
player grid so that those grids can be displayed for the game. In addition, there will need to be 
classes for displaying the various splash screens that the user can use to interact with the game 
(such as adjusting game variation and color scheme). There will also need to be a class that is 
responsible for building the plant selector panel which will either be some sort of conveyor belt 
where users can select which tower they want to use. The other option is to have a general “store” 
of towers, where the user can buy whichever towers are available on the screen. This will have to 
interact with the model, so that the grid can be properly updated to indicate the new tower type 
and placement. The last major component in the view will be the HUD, which will be responsible for
displaying the points to the user. 

###Details
Model: 
* Game API: The Game API defines methods for:
    * positions of plant/zombie (zombie score (health) 
    * images associated with plants/zombies
    * size of grid 
    * sun amount 
    * plants available to plant 
    * cost for each plant 
    *or count for queue 
    * if game has won/lost, display something else
    *current point total 
    * time left 
    * global high score 
* Game class: Implements the Game API and defines concrete implementations of all methods
* Gameplay package:  This module keeps track of the movement and position of all components in the game.  It does so by creating a grid specifically for playerGrid objects
    * PlayerGrid: Concrete class. Holds playerGrid objects (towers, empty, blocked cells), creates plants (throws exception caught in controller if plant not valid).  Implements the GridReader interface, which contains methods to obtain the position of the plants in the grid as well as to plant a new plant
    * Enemy Grid Collision: Holds projectiles and zombies, and has 10x more cells per row and column to allow for more gradual movement that is separate from the grid.  Tests for collisions and tracks position of projectiles and zombies. Implements GridReader Interface
    * Move zombies interface
    * Fire plants interface
    * Helpers for helping controller check win/loss condition
* PlayerGridComponents package: Contains all of the components that can be placed in the player grid (towers, empty cells, blocked cells, projectiles)
    * Towers interface: implements PlayerGridComponents interface
    * Shootable interface: Defines methods for shootable towers
    * Towers package:
        *Die, shoots projectile
        * Types of tower subclasses
        * Projectile (concrete class) Implements Collidable - XY position - implements EnemyGridComponent interface
    * Empty
    * Blocked 
* EnemyGridComponents package: Contains an Enemy interface and subclasses for all possible enemy types.
    * Enemies interface implements EnemyGridComponent
        * X and y position
    * Enemies Package
        * Forward moving zombie
        * Jumping zombie
        * Diagonal zombie
* Update game/status information package: This package is used to update the time left and the points that the player has earned over the course of the game
    * Time
    * Points
    * sun
* PlayerGridComponents Implements GameComponent interface
* EnemyGridComponents interface implements Collidable and GameComponent - handles actions that happen when projectile collides with zombie (eg push vs damage)  //interesting design challenge
* GameComponent interface
* Collidable interface
* GridReader interface

Controller
* Main class: The main class will set up the timeline for the game and control the animation.  It will instantiate the splashscreen at the beginning of the game, and then set up the Game based on the input values from the 
* Splashscreen controller module
    * Menu for choosing which game version to play, settings, set language, dark/light mode, global high score
    * Resources:
        * HighScores.txt file
        * darkMode.css
        * lightMode.css
        * Splashscreen.properties
* Gameplay controller module
    * Classes for win/loss/stepping/timeline
    * Class to read initial configuration files
    * Example resources (from data/simplegame)
        * Boards: board1.csv
        * Levels: level1.properties
        * Plants: peashooter.properties, cherrybomb.properties
        * Waves: wave.csv
        * Zombies: BasicZombie.properties
* Position Class: Uses a List structure to store x and y position of game components

View
* GameView API:  This API defines methods for
    * Type of tower to create
    * Position of a new tower
    * Pause/play status of the game
    * Types of towers that are available and their cost
    * Setting the point value for an enemy in the game
    * Setting the value of the Sun remaining
    * Setting the time remaining
    * Setting the current point total
    * Displaying the game ending message
* SplashView API: Implemented by various splashscreens
* Grid Package
    * GridView: Implements the GameView API to define the above methods.  The GridView will also call upon the Game API in the backend to display the positions of all enemies, towers, empty, and blocked cells in the game.  The constructor for the GridView will take a Game as a parameter, and each time the controller updates the game, it will also update the view with the current status of the game.
    * PlayerGridDisplay: implements the plantable interface.  Allows a selected plant from the plant selector to be planted on a specific tile
    * PlayerComponentDisplay: Is able to display towers, empty, or blocked cells in the main playing grid
    * EnemyGridDisplay: Displays the positions of all enemies independently of the playerGridDisplay.  Utilizes Game API to get updated positions of enemies
    * EnemyDisplay: Displays each individual enemy, according to image and type
* Hud:  The hud will display the point total, the previous high score, the amount of sun remaining, and other tools such as using the shovel to dig up a plant etc.
    * ScoreDisplay
    * SunDisplay
    * HighScoreDisplay
    * DigTool: Implements an event listener to dig up a plant
* Splashscreen: Implements the SplashViewAPI. On the splashscreen, the user will be prompted to select a language, default theme or create a custom theme with specific images and colors.  They will then be able to set up the game.
    * LanguageDropdown
    * DefaultThemeDropdown
    * EnemyImageDropdown
    * MainColorDropdown
    * AccessoryColorDropdown
    * SetupButton
* Splashscreen2: Implements SplashView API The second dropdown screen will prompt the user to select a variation of the game, along with a win rule, loss rule, list of numbers of zombies in the waves, and a zombie type.
    * VariationDropdown
    * WinRuleDropdown
    * LossRuleDropdown
    * WavesInput
    * ZombieTypeDropdown
    * StartButton
* Building plant selector panel + bank:
    * Queue: The Queue version of the plant selector will have plants that come in on a conveyor belt in a random order.  The player can only choose plants that are available in the queue.
    * SelectorPanel:  The selector panel displays all types of available plants. As long as the user has enough sun to pay for a plant, they can plant it in the Grid.
    * PlantOptionDisplay: Displays a plant along with its cost in suns. Implements an event listener for when its selected.  When selected, creates a PlayerComponentDisplay and prompts user to plant it in PlayerGridDisplay.



###Example Games
The different plants and zombies available to the player are configured completely through the data files, so this allows for flexibility in creating unique gameplay and strategy with different tools and adversaries that the player has to strategize around. 

Additionally, there are no specific implementations of any game type within our code; rather the rules are options that can be put together like puzzle pieces to form unique games. In separating out the win/loss conditions, how the plants are chosen, and how the waves are generated, the functional differences between our game variations are easily supported. 


1. Normal: Fixed number of zombies at beginning of level with fixed starting configuration.
    * Win: All zombies are killed
    * Lose: Zombie reaches the left side of screen
2. Time/flag trial: Shoot enough zombies that have different randomized point values within a certain period of time, then you win?
   * Win: By the end of time, you have shot enough points of zombies as required by the level
        * Gradient color in view of how many points zombie is
   * Lose: Zombie reaches left side of screen at any point, or by end of time, have not shot enough zombies with flag
3. Survival: Place ALL plants before game starts (you have limited options). Then zombie wave comes where you aren’t allowed to add any more plants. This happens multiple times
    * Win: All the waves are over
    * Lose: There is no plant left by the end of one of the waves
4. Boss battle: 
Instead of a bank, available plants come in on a queue and players have to use these predetermined plants to fight off a boss zombie 
Pushing zombies off screen: Start with waves of zombie, if you hit one, it goes backwards a little bit. Goal is to push all the zombies off the screen? Zombies don’t die.
    * Win: No zombies on screen
    * Lose: Zombie reaches left side of screen



###Design Considerations
We discussed in detail how to separate the enemies walking from the towers,
and how to handle their collisions. We will have two grids in the model: EnemyGrid and ActorGrid. In
 the view, EnemyGrid will extend past the end of ActorGrid, and will be overlapped on top of 
 ActorGrid so that each column of ActorGrid corresponds to 10 (or some other larger constant value) 
columns in EnemyGrid. Having smaller columns in EnemyGrid will allow for a steady animated movement
of the Enemy, rather than choppy movement from one to the next cell in ActorGrid. This will allow us
to handle projectiles, movement, and collisions more easily (by taking mod 10 of the column the 
enemy is currently on to see if it is on the correct column in ActorGrid).

We will need to further consider what the splashscreen entails, and what settings should be able to 
be modified through the splashscreen. We will also need to consider the exceptions that the 
controller should catch from the model and from the properties files being read in.



