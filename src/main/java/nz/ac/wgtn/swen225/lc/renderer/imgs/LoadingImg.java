package nz.ac.wgtn.swen225.lc.renderer.imgs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public enum LoadingImg {
//    PlayerForward("playerForward.png"),
//    PlayerBackward("playerBackward.png"),
//    PlayerLeft("playerLeft.png"),
//    PlayerRight("playerRight.png"),
//    Treasure("treasure.png"),
//    Key("key.png"),
//    Door("door.png"),
//    ExitLock("exitLock.png"),
//    Info("info.png"),
//    Wall("wall.png"),
      FreeTile("sand.png");

    private final String filename;
    private BufferedImage image;

    LoadingImg(String filename) {
        this.filename = filename;
        try {
            this.image = ImageIO.read(new File("src/main/java/nz/ac/wgtn/swen225/lc/renderer/imgs/"+filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage loadImage() {
        return image;
    }




}
