CompSci 307: Final Project API Changes
===================

##Model
Refactored methods in the original Game API in model into three separate APIS:
GameControlAPI (which communicates between model-controller), GameDisplayAPI (which
communicates between model-view), and GameStatusAPI (which provides information about the
status). These APIs combined contain mostly the functionality intended originally
to be provided by the Game API by itself in the plan.

##View
* GameViewAPI defines two methods: step() and setupScene().  Never implemented any other methods that
were outlined in the plan because we discovered quickly that they were unnecessary
* Added SetupScreenAPI and FirstSplashScreenAPI to return the strings selected by the user, the scene
for the splashscreen, and the start/setup buttons.

##Controller