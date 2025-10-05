package nz.ac.wgtn.swen225.lc.renderer.imgs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Handles images from file path to load into the game
 * Saves images as enum objects to be used throughout game
 */
public enum LoadingImg {
    PlayerUp("imgs/playerUp.png"),
    PlayerDown("imgs/playerDown.png"),
    PlayerLeft("imgs/playerLeft.png"),
    PlayerRight("imgs/playerRight.png"),

    enemyCrab("imgs/enemyCrab.png"),

    Treasure("imgs/treasure.png"),
    PurpleKey("imgs/purpleKey.png"),
    PinkKey("imgs/pinkKey.png"),
    OrangeKey("imgs/orangeKey.png"),
    GreenKey("imgs/greenKey.png"),

    PurpleDoor("imgs/purpleDoor.png"),
    PinkDoor("imgs/pinkDoor.png"),
    OrangeDoor("imgs/orangeDoor.png"),
    GreenDoor("imgs/greenDoor.png"),

    Exit("imgs/exit.png"),
    ExitLock("imgs/exitLock.png"),
    Info("imgs/info.png"),

    Rock("imgs/wall.png"),
    Sand("imgs/sand.png"),
    Water("imgs/water.png"),

    StartScreen("imgs/startScreen.png");


    private final String filename;//name of file
    private BufferedImage image; //tile image

    /**
     * Constructor for the enum
     * Loads and saves the image to an enum
     * @param filename: name of the image file
     */
    LoadingImg(String filename){
        this.filename = filename;
        try{
            InputStream stream = LoadingImg.class.getResourceAsStream("/" + filename); //gets the image path as stream
            if(stream == null) {throw new RuntimeException("Cannot find the image file: " + filename);}
            this.image = ImageIO.read(stream);
            stream.close();
        } catch(IOException e){ throw new RuntimeException("Error loading the image: " + filename, e);}
    }

    /**
     * Gets the image of enum
     * @return - buffered image
     */
    public BufferedImage loadImage() {
        return image;
    }

}
