package nz.ac.wgtn.swen225.lc.renderer.imgs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Handles images from file path to load into the game
 * Saves images as enum objects to be used throughout game
 */
public enum LoadingImg {
    PlayerForward("playerForward.png"),
    PlayerBackward("playerBackward.png"),
    PlayerLeft("playerLeft.png"),
    PlayerRight("playerRight.png"),
    Treasure("treasure.png"),
    RedKey("redkey.png"),
    Door("door.png"),
    ExitLock("exitLock.png" ),
    Info("info.png"),
    Rock("wall.png"),
    Sand("sand.png"),
    Water("water.png");


    private final String filename;
    private BufferedImage image;

    /*
    *   Constructor for the enum
    *   Loads and saves the image to an enum
    *   @param filename: name of the image file
     */
    LoadingImg(String filename){
        this.filename = filename;
        try{
            InputStream stream = LoadingImg.class.getResourceAsStream("/" + filename);
            if(stream == null) {throw new RuntimeException("Cannot find the image file: " + filename);}
            this.image = ImageIO.read(stream);
            stream.close();
        } catch(IOException e){ throw new RuntimeException("Error loading: " + filename, e);}
    }

    /*
     * Gets the buffered image
     */
    public BufferedImage loadImage() {
        return image;
    }

}
