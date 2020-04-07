# Dotify
## Eric Ng

### Description:
**Dotify** is the next big music app that will break through into the music industry from an up and incoming start up. What the final product is a proof of concept screen on an Android app. At the moment, it does not play any music. 

### Extra Credit:
*All extra credits were attempted and are finished*
1. **A user is not allowed to apply a new username if the edit text field is empty** :heavy_check_mark:
   1. An error is shown when the username field is empty (spaces are not considered characters)
1. **Long pressing on the cover image changes the color of all the text views to a different color** :heavy_check_mark:
   1. Long pressing the cover image changes the color of all text on the screen to a random generated color
1. **If using ConstraintLayout, utilize a Barrier or Guideline somewhere with a view constrained to it** :heavy_check_mark:
   1. I put a guideline horizontally centered on the screen and had the skip previous buttons and skip next button constrainted to it. This will make sure that the buttons are always beside the play button, even when the screen size changes. The buttons would move further apart if constrainted on both sides of the screen.
1. **All hardcoded dimensions & colors are extracted into res/values/dimens.xml & res/values/colors.xml respectively** :heavy_check_mark:
   1. All hardcoded dimensions are in the dimens.xml. I did not use any hardcoded color values, but a randomly generated color value.
1. **Instead of requirement #5, the album cover image must be a perfect 1:1 square size whose width matches the screen width and height matches the height as well.** :heavy_check_mark:
1. **Create another xml file that uses the a different ViewGroup type than your original.** :heavy_check_mark:
   1. activity_main.xml is made in ConstraintLayout View and has all the functionality in it. activity_main2.xml is a replication with RelativeLayout View (does not have the functionality that activity_main.xml has).

### Sreenshots/Gif:

##### Pixel 2 Emulator
<img src="https://github.com/ericngg/Dotify/blob/master/extras/Pixel%202%20Emulator.png" alt="emulator" /> ![Pixel 2 Emulator](https://github.com/ericngg/Dotify/blob/master/extras/Pixel%202%20Emulator.gif)
