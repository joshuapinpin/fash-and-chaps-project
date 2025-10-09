# Renderer Manual Testing

# Test Records
| Commit ID   | Date         | Functionality                        | Outcome   | Issues Opened/Updated |
| --------    | --------     | ---------------------------          |-----------|-----------------------|
| 2528533d    | 14/19/25     | Loading images                       | Pass      | #2                    |
| 5f2be012    | 20/09/25     | Displaying image                     | Pass      | #2                    |
| 4c12fb26    | 20/09/25     | Camera Displaying                    | Pass      | #34                   |
| 7b457475    | 20/09/25     | Fetching Images from correct files   | Pass      | #2                    |
| 4623e312    | 04/10/25     | Loading Sound Effects                | Pass      | #36                   |
| 7d8145ae    | 04/10/25     | Observer Pattern                     | Pass      | #                     |
| 32809dc2    | 04/10/25     | Background Music playing             | Pass      | #36                   |
| 9f58221d    | 04/10/25     | BG music looping                     | Pass      | #36                   |
| 52174764    | 05/10/25     | Visitor Pattern                      | Pass      | #67                   |
| f6f0abe0    | 07/10/25     | Background music restarting          | Pass      | #76                   |




## Details
### Commit: 2528533d
- **Tested:** Tested if an image could be loaded and displayed onto a JPANEL then on a JFRAME. Tested to see if the input stream could fetch the correct image. This was tested through trying different file paths to get the image.
- **Result:** Image was loaded accordingly and images could be displayed correctly. Meaning that using a inputdatastream worked to get the file path of the image.

### Commit: 5f2be012
- **Tested:** Testing cases to see if more than one image could be loaded at a time. Tested that the correct images would be displayed 
- **Result:** Images of all the different tiles could be loaded onto the JPANEL and could be seen. 

### Commit: 4c12fb26
- **Tested:** Tested "camera" functionality of the game, showing 9x9 grid of the game from center of player. Ensured displayed correct images, and smooth transitions when player moves (only displays the changes). Tested to see if tiles and entities were parsed correctly to get correct image.
- **Result:** Camera worked well displaying correct tiles from center of player. Not laggy or glitchy but smooth transitions when tiles change. Not every tile was loaded so heaps of white space but logic works.

### Commit: 7b457475
- **Tested:** Images were fetched from correct file, following Java's structure in resources. Tested the file path in which the images were fetched from.
- **Result:** Images can load perfectly, without having to "hardcode" the file path. Can directly get from resources. 
- 
### Commit: 4623e312
- **Tested:** Sound effects loaded correctly. Checked if the file path is correct and when loading the clip it gets the correct file/actual audio data. Tested if the whole clip would play through. Tested the volume of the sound ensuring its not too loud.
- **Result:** Sounds loaded and played fully till the end. File loaded correct and audio was fetched and sounds were played at correct volume.

### Commit: 7d8145ae
- **Tested:** Implemented Observer Pattern to play sounds when event occurs. Ensured each sound effect played fully and correctly on time when events occured e.g. door unlocks
- **Result:** When any event occurs, dying, collection or unlocking, the correct sound played and played for the right amount of time.

### Commit: 32809dc2
- **Tested:** Testing to see if background music plays, ensuring that the background music loops after ending
- **Result:** Background music played correctly once but did not loop, stopped. 

### Commit: 9f58221d
- **Tested:** Tested to see if background music loops after ending once. Changed where the looping occurs
- **Result:** Background music now plays and loops correcting after changing where in the code the music loops
- 
### Commit: 52174764
- **Tested:** Implemented Visitor Pattern to show tiles correctly rather than checking its instance every time
- **Result:** It worked properly, displayed images

### Commit: f6f0abe0
- **Tested:** Ensured that the bg music would continue after pausing and that stopping the bg music 
- **Result:** Music played from where it left off correctly


### Commit:
- **Tested:**
- **Result:**



## Issues
- #2 (closed): Implement loading images and displaying them
- #34 (closed): Implement Camera, 9x9 tile display
- #36 (closed): Implement loading music and playing them
- #67 (closed): Implement Visitor pattern to load
- #76 (closed): Fixing Background Music replay

