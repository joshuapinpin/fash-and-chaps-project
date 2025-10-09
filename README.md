# fash and chaps


## Instructions for set up:
- Start the game by running **nz.ac.wgtn.swen225.lc.app.Main** from IntelliJ
- **Test classes** are found in **test.nz.ac.wgtn.swen225.lc** where coverage was measured in Jacoco tool (disabled assertions):
    - Domain (**DomainTest**), persistency (**LevelMakerTest** and **LevelsTest**) and recorder (**RecorderTest**) unit tests.
    - Fuzz testing (**FuzzTest**)

## Game Logic:
- You are the **seagull**, and the aim is to collect all the **coins** to open the **treasure chest** to get back home to your **sand castle**.
- Coins can be in **locked doors** which need to be unlocked with the corresponding **key** matching the door colour
- Be careful and avoid the **crabs and puddles**, which are obstacles for the seagull in level 2.
- Go to the **seahorse** which provides info about playing the level in a pop up

### Home page (when you run Main.java):
- **LEVEL 1:** start game at level 1
- **LEVEL 2:** start game at level 2
- **HELP:** instructions of our game for the user
- **EXIT:** exit the program

### Keystrokes (as per handout):
- **CTRL-X:** exit the game
- **CTRL-S:** exit and save game
- **CTRL-R:** resume a saved game
- **CTRL-1:** start game at level 1
- **CTRL-2:** start game at level 2
- **SPACE:** pause game, with dialog
- **ESC:** close the "game paused" dialog and resume
- **UP,DOWN,LEFT,RIGHT:** move seagull in maze

### Recording a game (under RECORDER in GUI on right pane):
- **START:** to start recording
- **STOP:** to stop recording and store in a json format file
- **AUTO-PLAY:** after recording, plays full recorded game, adjusting replay speed on slider
- **STEP-BY-STEP:** player recorded game from file, click on the button each time for each step

### Saving and Loading (rock buttons on bottom pane):
- **SAVE:** save the current game state
- **LOAD:** load a game from file
- **PLAY:** start the game (resume when paused)
- **PAUSE:** pause the game (also via SPACE)
- **HOME:** takes you back to the home screen of game

### Other displays on GUI (left pane):
- **LEVEL:** current level user is player
- **TIMER:** time left to complete the game
- **KEYS:** keys collected by user so far in level
- **TREASURE:** coins collected by user so far in level

