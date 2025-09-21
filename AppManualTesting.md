# App Manual Testing

## Test Records
| Commit ID | Date          |Functionality                              | Outcome  | Issues Opened/Updated  | 
| ------    | ------        | ------------------------------------------|------    | ------                 |
|e7cd4c81   | 10-09-2025    | Testing inputs and info propagation       | Pass     | #2                     |
|bbe4f3c6   | 22-09-2025    | Button recognition                        | Pass     | #15                    |

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

## Issues
- #2 Implement User Input Handling in InputController
- #15 Implemente funciontalities to GUI panels