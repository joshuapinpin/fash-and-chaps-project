package nz.ac.wgtn.swen225.lc.app.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MyImage {
    BufferedImage image;
    String filename;

    public MyImage(String filename){
        this.filename = filename;
        try{
            InputStream stream = MyImage.class.getResourceAsStream("/" + filename + ".png");
            if(stream == null) {throw new RuntimeException("Cannot find the image file: " + filename);}
            this.image = ImageIO.read(stream);
            stream.close();
        } catch(IOException e){ throw new RuntimeException("Error loading: " + filename, e);}
    }
    public BufferedImage getImage() {
        return image;
    }

}
