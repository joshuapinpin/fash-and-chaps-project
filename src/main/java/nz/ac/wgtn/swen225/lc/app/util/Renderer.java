package nz.ac.wgtn.swen225.lc.app.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer {
    public static Renderer of(){
        return new Renderer();
    }
    public static BufferedImage image;
    Renderer(){
        loadImage();
    }
    private static void loadImage(){
        try {
            image = ImageIO.read(new File(
                    "src/main/java/nz/ac/wgtn/swen225/lc/renderer/imgs/sand.png"
            ));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
