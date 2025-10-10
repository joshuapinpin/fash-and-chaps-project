# App Manual Testing

## Test Records
| Commit ID  | Date          |Functionality                              | Outcome  | Issues Opened/Updated  | 
| ------     | ------        | ------------------------------------------|------    | ------                 |
| e7cd4c81   | 10-09-2025    | Testing inputs and info propagation       | Pass     | #2                     |
| bbe4f3c6   | 22-09-2025    | Button recognition                        | Pass     | #15                    |
| ca6dc518   | 20-09-2025    | Implementing game states                  | Pass     | #18                    |
| 5068c9f0   | 20-09-2025    | Integrating App and Renderer together     | Pass     | #31                    |
| 4f24ac55   | 21-09-2025    | Implement timer controller                | Pass     | #17                    |
| ac76300c   | 30-09-2025    | Refactored code for each module           | Pass     | #NOISSUE               |
| f473003f   | 04-10-2025    | Refactoring the GUI panel creation        | Pass     | #62                    |
| 3231ee1b   | 07-10-2025    | Ensure Game screens/transitions           | Pass    | #72                    |
| aa27adf6   | 4-10-2025     | Fixing Info Tile                          | Pass    | #63                 |


---

## Details
### Commit:
- **Tested:**
- **Result:**

### Commit: e7cd4c81
- **Tested:** User presses key, makes sure game recognises keys are being pressed and maps them to game actions.
  Testing the the red square moves when user presses move keys.
- **Result:** Red square moves and game can recognises and updates based on user inputs.

### Commit: bbe4f3c6
- **Tested:** Testing user presses on buttons, making sure game recognises presses. Also testing slider,
  making sure java swing focuses back to the main game panle.
- **Result:** Confirmation messages of pressed buttons are printed, user can still move after using slider.

### Commit: ca6dc518
- **Tested:** Tested game states including pause, play and victory states to see if triggered when event occurs
- **Result:** All the states triggered for each event, no false triggers.

### Commit: 5068c9f0
- **Tested:** Testing renderer and app integration. Seeing if renderer JPANEL displayed when app call its.
- **Result:** JPANELs were drawn correctly

### Commit: 4f24ac55
- **Tested:** Testing if the timer works and if events occured due to the timer e.g. dead state when timer runs out
- **Result:** Timer works and triggered correct states when those events occured.

### Commit: ac76300c
- **Tested:** Tested that logic and code still works once refactoring it into each module controller
- **Result:** Code and logic all worked, made the logic a lot more simpler and easy to find bugs.

### Commit: f473003f
- **Tested:** Test to see if the code still worked when layering panels. making code more reusable so it can be used for other panels
- **Result:** All GUI elements were shown correctly.

### Commit: 3231ee1b
- **Tested:** Tested to see if the game screens changes when event event triggers
- **Result:** Game screens changed correctly when those event occurs

### Commit: aa27adf6
- **Tested:** Fixed Info Tile logic, ideally when the info tile is stepped on the popup shows and when stepped off the popup hides away
- **Result:** Results worked for each event e.g. when player on the popup showed and when stepped off the popup disappeared



## Issues
- #2 Implement User Input Handling in InputController
- #15 Implemente funciontalities to GUI panels
