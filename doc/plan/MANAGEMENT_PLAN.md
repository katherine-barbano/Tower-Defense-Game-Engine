## Responsibilities
Katherine: Model Gameplay package (Grid stuff) and Game class

Alex: Half View (Bank + building plants panel), Half Model (PlayerGridComponents package and PlayerGridComponent interface)

Heather: View (GridView and SplashScreenView)

Priya: Model EnemyGridComponents package and EnemyGridComponent interface

Megan: Controller

## Features Due
### Sprint 1
* Model
    * Implement Normal variation of the game
    * Forward zombie, peashooter plant, projectile, sunflowers (generate sun to build plants)
* Controller
    * Read in initial config files of level, tower, wave, properties, etc. files. Assume all properties files are correct
    * Win and loss conditions for normal
* View
    * Placing a plant, using the store implementation
    * Display using the GridReader

### Sprint 2
* Model
    * Implement Time/flag variation, survival
    * Game statuses package (i.e. points and time package)
    * Enemy types
        * Jumping enemies (jump over peashooter plants)
        * Cherry bomb enemy
    * Tower types
        * Cherry bomb (explodes after certain period of time or after killed?)
        * type killer (when killed, kills all zombies of the same type as the zombie that died) 
* Controller
    * win/loss for 2 new variations
    * Error checking, assume properties files might be wrong
* View
    * Display the hud and bank
    * splashscreen
    * Placing a plant, using the queuing implementation with conveyor belt

### Complete
* Model
    * Boss battle variation
    * Movement Policy (chess pieces)
    * Pass high scores to other places
    * Dig up plant (remove)
* Controller
    * Saving/loading functionality for initial configuration
    * Randomized initial configuration of zombies
* View
    * Saving/loading button
    * High scores
    * Shovel option
    * Languages, settings, variations, etc
