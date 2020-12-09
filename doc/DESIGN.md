CompSci 307: Final Project Design
===================

#Names
Katherine Barbano (kab134)

Alex Chao (ac590)

Heather Grune (hlg20)

Priya Rathinavelu (plr11)

Megan Richards (mer91)

#Primary Roles
- Katherine - Model : GamePlay (managing the backend grids and collisions, interaction between GameComponents)
- Alex - Model: TowerGridComponents (Different plants), View: (Queue version of displaying plant selections), Controller: (Win/Loss conditions)
- Heather - View (Frontend - Displaying backend objects, splash screens, custom themes)
- Megan - Controller (General progression/workflow of the game, creating levels, reading in all data files, validation, saving)
- Priya - Model: EnemyGridComponents (Different Enemies, different Projectiles)

#Design Goals and Easy-to-Add Features

Our goal was to make the following features easy to add:
- New level variations with different win/lose conditions, plant selections, visual themes,
available towers, enemy wave configurations, initial board states, grid vs. path variations, all
in any different combination within a single level
- Add any new level into a single game
- New types of enemies (MOD with different health/speed/firing rate, and ones with different functionally)
- New types of towers (MOD with different health/firing rate, and ones with different functionality)
- New types of projectiles (specifically the range over which the projectile travels)
- New visual themes
- New implementations of the board (i.e. a grid vs a path)

#High Level Design of Core Classes

##Model

### GameComponents package
Within the game components package, there are two main subpackages, the EnemyGridComponents and the PlayerGridComponents.

- EnemyGridComponents:
These objects are stored in the separate grid from player components because they have movement, so we 
created a separate grid with smaller column and row sizes. 

    - Enemies package - this includes the Enemy abstract class which is used for the general enemy object that is 
able to keep track of its own health as well as get its next position for movement (this includes normal movement
as well as movement along a grid path). There are different 
subclasses as well that implement this Enemy class, such as a SingleUseEnemy, ForwardEnemy, and ShootEnemy. These 
enemies have different "powers" that result in different actions that occur as part of the enemy's unique 
features. 

    - Projectile package - this package includes the abstract Projectile class which is extended by different subclasses
that are functionally unique. For example, the ShortRangeProjectile, which extends the abstract class, is only limited
to travelling only a little bit in front of it. In contrast, the SingleProjectile, which also
 extends the Projectile abstract class, is able to travel the full length of the grid. The projectile is 
 considered a Damager, so it is able to cause damage to other components it interacts with. 

- PlayerGridComponents:

    * Player grid components consist of the towers that the users can create when running the game 
        
        * The tower abstract class defines the common functionality of the other classses in this package 
        
            * Suntowers generate sun for the user 
            
            * Shoot towers fire projectiles 
            
            * SingleUseTowers enact some action when planted and then disappear


- Fire State - this is separate from the other packages but is used for the "fire" state created by the 
single use towers and enemies that "explode" and destroy their nearby states.

- Interfaces 
    - GameComponent 
        - A general interface used to help combine the different types of game components so that they could be categorized and easily referenced
    - Actionable 
        - Used for any objects that had some sort of "action" that needed to be triggered at a certain time
    - Shooter 
        - Used for objects, such as ShootTower and ShootEnemy, that are capable of shooting projectiles
    - Movement 
        - Used for objects that needed their positions to be updated as the game progresses to display movement
    - Removable
        - Used for objects that will eventually need to be removed from the grid as the game updates 
    - Health 
        - Used for any components that store their health and update it as the game goes on, such as after collisions
    - Damager 
        - Used for any components that are capable of inflicting damage on other game components and can store this damage value


###Gameplay package

This package is essentially the higher level model, which puts together all interactions between GameComponents,
detects and creates the results of Collisions, stores all GameComponent data, and
makes it possible for the View and Controller to interact with model through APIs.

The gameplay package was organized into three primary sub-packages. The first was
playableArea, which included a PlayableArea interface and PlayableAreaData concrete
subclass that stored the GameComponent data in a List of Lists. We chose to include the
PlayableArea interface so that the higher level code in Model only needed to reference the
interface's methods, and so that other packages would not have access to the actual
data structure implementation of PlayableAreaData. This made the code more extensible because
it is now possible to have different data structure implementations of PlayableArea 
without changing any other code besides creating a new subclass of the interface (because the
interface contains helper methods and uses the iterator design pattern to access
the necessary data without exposing the data structure).

The second sub-package was collision, which included a CollisionDetector class, 
which is perpetually creating new Collisions every time components are moved
using a CollisionFactory. Refactoring the collision detection out into its own class
from the rest of the code made my classes follow the Single Responsiblity Principle more
closely, and refactoring out instantiation of Collisions into a CollisionFactory made
the code more extensible, because it is possible to add new Collision subclasses without
needing to change anything in CollisionDetector. There is a Collision hierarchy that includes a Collision interface, and subclasses
for each different type of collision (for example, HealthFireCollision, ProjectileProjectileCollision, etc.)
We chose to design subclasses for collisions between different GameComponent interfaces
because the result of these interfaces colliding will always be inherently different depending
on what the interfaces are that collide.

The third sub-package was MVCInteraction, which had a sub-sub-package for 3 API interfaces
that were used throughout the controller and View: GameControlAPI communicated with Controller,
and had many helper methods for controller.
The Boolean helpers about the state of enemies in the 
 grid in GameControlAPI were referenced many times by the win/lose conditions
  in controller. This was well designed because it allowed the controller to better follow Dependency 
  Inversion Principle, by not having the controller depend on lower level 
  methods in model.
  There is also a method to return a GameDisplayAPI for the given GameControlAPI. We designed
  the GameDisplayAPI methods to be separate from GameControlAPI because it would allow controller
  to be able to pass an API into View that did not have methods only meant for the controller. This
  allowed to View to better follow the Interface Segregation Principle, and not depend on methods it doesn't use.
  There was also a GameStatusAPI that can be retrieved from the GameDisplayAPI, since similarly, a few
  View classes only need to use status-related methods, and do not need access to the entire GameDisplayAPI.
  The concreteModel sub-sub-package implements each of these interfaces in a class. There is also
  an abstraction for GameControl, so that subclasses are able to implement the starting states of the grid
  differently in GridGameControl and PathGameControl (which is good design because it makes the game board
  more extensible).
  
  The GameControlAPI has a method called "moveComponents". This is
  one of the most important methods in Model because it is called by controller
   every step to update the PlayableArea. This is what it does:
  
  1. Update the ranges of all shooter GameComponents (which allows
  enemy shooters to only shoot when there is a tower in range, and vice versa).
  
  2. Place new GameComponents produced from all Actionables, including Projectiles that towers or enemies
  are now ready to place, or Fire components for SingleUse components that are ready to explode.
  
  3. All components that are out of range (e.g. off the boundaries of the grid) are removed.
  
  4. A method is called for all collisions that enacts the result of each collision in the grid.
  
  5. All movable objects in the PlayableArea determine their next position based on the empty
  positions in the grid, and move there if that position is valid.
  
  6. The set of active collisions is updated by the collision detector for the next step. 
  
  7. All game status values are updated by
  calling gameStatus.update.
  
  This entire update process is well designed because it relies on all other parts of the
  model at a high level, and calls helpers to perform these actions to keep the moveComponents method
  at a high level itself.

##Controller

## GamePlay Hierarchy and Responsibilities 
The app is run by running main, which initializes an object that implements AppControl. 
This AppControl object initializes the application with controller classes for the game and set up 
 portions of the workflow (gameControl and SetUpSelections classes). The gameControl class handles the 
 gameplay operations, such as switching between levels and determining win/loss of the game. The setUpSelection
 class controls the screens producing the initial set up portion of the app (choosing the languages, 
 making custom games, selecting games to play). Once the isSelectionComplete boolean in the set up class
 returns true, the app control will create a gameControl, which initializes a game based off of the selections. 
 To run the game, game control creates level objects (which is where the view and model are created), 
 uses a conditionsChecks object to handle the condition checking, which is used by the game control 
 to switch levels and start/end the game. The level object creates a view for the game (gameView) and 
 a backend for the game, exporting the updating of the backend objects to a levelControl class.  

## Conditions package
The conditions package is broken up into the win and loss packages which each define the subclasses
 for the different conditions that could be implemented in the game this is used by the conditions 
 check class to check the conditions of the game and determine whether to move on to the next level or produce the appropriate screen. 

## Data Access and Config 
For data access, primarily the app uses three resource interfaces: 
AppResourceAccess:defines the set up and game control classes (we've only implemented one, but interfaces could be easily extended),
SetupResourceAccess: defines the methods to get the screen properties and selection options
TowerDefenseResourceAccess: defines data access methods to run the towerDefense game we have. 

We also have Reader and writer classes. 

In the configObjects package, we make config objects for the enemies and towers (using an EnemyConfigFactory), 
which are package objects for the properties read in the from the data files, and is sent to the backend
to create the actual full game objects. We use the ConfigAccess interface in externalAccess package to give the view access to the 
methods to create the config objects needed as the user clicks on the grid, for example. 

## Validation and Customization
In the validation package, we have classes for validating the custom inputs and the set up inputs. 
For customization, we have handler classes which define methods to take in either games or level objects 
(level objects include sub-components of levels, such as a grid). These take in the input, perform 
the validation, and call the writer functions as needed. 

##View

###GameView Package
The GameView Package implements the GameViewAPI, and creates and displays the main game and 
all of the components necessary to run the game.  The main components in the GameView are:
1. GameControlPanel
    * Constructs the panel along the bottom of the screen.  
    * It contains all of the clickable menu options, including the digging tool, the home button, and the pause button.
2. GridPanel
    * Constructs the main grid.  In order to do so, constructs the playerGridView and EnemyGridView,
    and calculates sizing of the cells in both.  Then, adds the EnemyGridView to the group directly 
    on top of the PlayerGridView. The basic design structure/hierarchy is:
        * GridViewStructure: superclass for PlayerGridView and EnemyGridView
        * PlayerGridView
            * composed of PlayerGridComponentDisplay objects
        * EnemyGridView
            * composed of EnemyGridComponentDisplay objects
        * GameComponentDisplay super class for PlayerGridComponentDisplay and EnemyGridComponentDisplay objects
    
3. PlantSelection:
    * PlantSelection The plant selection abstract class defines the functionality of selecting a 
    tower to place and having the player grid listen for this type of tower.
        * Panel the towers' ability to be created is bound by the amount of sun the player has 
        * Queue each seed can only create one tower, but new seeds are constantly being refreshed 
    * PlantHandler: the plant handler sets the event handlers for creating and removing the towers 
    its event handler is set by the shovel and the plant selection class 
    * TowerBox: a tower box represents the object that the player interacts with to determine which 
    tower to create 
4. StatusDisplay: 
    * StatusBar:extends HBox to display the non-clickable status display components along the top
       of the screen
        * StatusDisplayComponent: superclass for all components in status display bar
        * EnemyProgress: extends progress bar to display progress of enemy waves
        * SunDisplay: displays total sun 
        * ScoreDisplay: displays total score
        * Time Display: displays elapsed time
      


###Splashscreen package
* FirstSplashscreen: Implements firstSplashscreenAPI.  Contains a language selection dropdown and
setup button
* SetupScreen: Implements SetupScreenAPI.  Contains game selection and dynamic customization 
    * GameSelection: choose game to play (contains custom option which dynamically opens customizaton menu)
    * Customization:
        * CustomGameSelection: Menu is added to main splashscreen when custom game is selected.
        User must choose number of levels. For each level, chose a predefined level or
                       customize a level, CustomLevelSelectionPopup opens when the user selects "Custom"
        * CustomSelection: abstract class to define what happens when a user selects "Custom" from a list
        of options
            * CustomLevelComponentSelection: abstract class for a custom dropdown for Level components
                * ThemeSelection
                * GridSelection
                * EnemyWaveSelection
            * LevelSelection
                    
        * CustomPopup - Abstract class to define the basic structure of a popup window with a button to apply choices
            * CustomPopupWithTextField - extends custom popup with option to add a text input 
                * CustomLevelSelectionPopup
                * CustomThemeSelectionPopup
            * SingleFileUploadPopup - extends custom popup to create a popup screen with a button to select a single file
                * CustomGridSelectionPopup
                * CustomEnemyWaveSelectionPopup
        
        * CustomLevelSelectionPopup: if the user chooses to customize a level, they are given dropdowns 
        and options


###WinLoseScreens package
* LevelScreen abstract class: API for all winning and losing screens. All messages and buttons
are populated with text based on className keys in the properties files.
    * LevelLostScreen
    * LevelWonScreen
    * GameWonScreen


#Assumptions/Decisions to Simplify Design

##Model
- Fire and projectiles only damage/kill towers if they are shot from enemies, and vice versa. You can’t hurt a game component that’s on your same side.
- Projectiles only get shot from enemies if there is a tower in the range of an enemy. For example, if the enemy shoots upwards, if there is a tower above the enemy, it will shoot. Otherwise it won’t shoot. This applies vice versa for towers shooting at enemies.


##Controller
- Custom win/loss only can have one selection
- if your game has an unsupported level you have to restart (Get sent back to the home screen)
- user must create game with path games in the properties files
- Assume a fair number of key names from properties files, especially setupScreenPropertiesMap, setupScreenOptions

##View
- Theme is unique to each level.  User must specify theme in custom game creation
- Custom games are only compatible with GameWithGrid not game with path (no dropdown selection for this variation)
- Grid cells are squares, are sized so that the grid fits inside the allotted space in both dimensions (would rather not take up the
- entire space than go outside the window)
- The enemyGrid cell dimensions are calculated using the dimensions of the playerGridCells

#Differences From Initial Plan

##Sprint 1

Plan: Create fully functioning basic version of the game with all interactions between a basic enemy and tower being handled in the backend and displayed in the frontend

Reality: Finished creating abstract game components (tower, enemy), created initial grids for storing components, created initial frontend for displaying grid

##Sprint 2

Plan: Implement another variation of the game, specifically a survival based game, implement other types of enemies and towers, such as ones that can explode, implement a queue feature of storing towers

Reality: Created more interfaces for developing different types of enemies and towers, focused on handling an interactions between components within the grid, updated front end to display new collisions from the backend, implemented initial splash screens, implemented more themes

##Complete

Plan: Implement a "final" round boss battle variation of the game, work on additional features for improving user experience such as saving/loading a game, language settings, and high scores.

Reality: Created all necessary win/loss conditions, increased user customization with splash screens

#How to Add New Features
We will discuss many different ways you can make functionally different levels, the
first of which is by making different combinations of existing functionalities in a 
properties file:

##To add new Level variation (the core of our functionally different playing experiences):

Add a new properties file within resources/levels corresponding to your new level. This
properties file can specify any combination of win and lose conditions (or even have multiple
conditions for winning and losing), specify any combination of plant selection (e.g. having the bank
in the Panel version, or having the Queue version of plant selection), specify the
visual Theme images, specify the type of board for model to use (e.g. a Grid or a Path), 
specify which towers will be available for the player to use in the level, specify the enemy
waves. It is possible to have any combination of these different level properties, all of which make
for very functionally different experiences.

This process can also occur through a custom level creator in the setup screen of the
View without manually making a new properties file.

##To add a new Game with multiple Levels:

Add a new properties file within resources/games. The keys should be 0, 1, 2, ... level numbers,
and the values should be the name of the Level properties file.

##To add a new type of enemy:

1. Add a new properties file within resources/enemies with the enemy's name, health, damage,
type (which corresponds to its subclass like Forward, SingleUse, etc.), speed, score.
Depending on its type you might need to add other properties, like projectile speed for shooters.

2. In resources/enemyMaps, add a new character key for the enemy, and as the value list the enemy name.

3. In resources/visual/themes, within whichever properties file for the
visual theme you want the enemy to appear in, add a key with the enemy name, and a value with the image filepath.

4. In data/waves, edit the csv of the incoming wave to include the character key corresponding
to your new enemy. Add this as the WavesCSV property in the level property file you want
the enemy to appear in.

Note: to create a functionally different enemy type, simply extend the Enemy abstract class in
model.

##To add a new type of tower:

1. Add a new properties file in resources/towers. Specify the tower name, damage, type (i.e. the
model subclass), cost, score, health, and rate. Depending on its type you might need to add other properties, like projectile speed for shooters.

2. In resources/visual/themes, within whichever properties file for the
visual theme you want the tower to appear in, add a key with the tower name, and a value with the image filepath.
Within here, also add a property for the filepath to the tower "seed" (the image that appears
on the left side of the screen for tower selection).

3. Within the level in which you want that tower to appear, add
the tower to the level properties file in the list next to "Plants".

Note: to create a functionally different tower type, simply extend the Tower abstract class in
model.

##To add a new type of projectile:

1. Within the properties file for the shooter type that you want to shoot that projectile (either enemy or tower),
change the property of "ProjectileType" to be the subclass of Projectile that you want.
To create a functionally different projectile type simply extend the Projectile abstract
class in model.

##To add a new visual theme:

1. Within resources/visual/themes, create a new properties file that has all game components
that will show on the screen, and map them to an image filepath.

##To add a new winning or losing condition:

1. If the winning/losing condition is functionally different, extend the 
WinLossCondition abstract class in the Controller into a concrete subclass.

2. In the level properties file that you want to have that new winning or losing condition,
add that new condition to the list of win conditions or the list of loss conditions.

##To add a new way to select towers:

1. If the tower selection is functionally different, extend the 
PlantSelection abstract class in the View into a concrete subclass.

2. In the level properties file that you want to have that new tower selection mechanism,
change the PlantSelection property to the name of your new subclass.

##To add a new Game board in model:

I.e. this is how to have a grid vs. a path

1. If the game board is functionally different, extend the 
GameControl abstract class in the Model into a concrete subclass.

2. If the game board has different starting states, create a new csv in
data/grids with the starting state layout of single states in the grid.

3. In the level properties file that you want to have that new game board mechanism,
change the GridType property to the name of your new subclass. Change the GridCSV
property to the name of your new csv.
