package nz.ac.wgtn.swen225.lc.app.util;

import java.awt.*;
import java.io.InputStream;

public class MyFont {
    private Font font;
    private String filename;

    public MyFont(String name, float size) {
        this.filename = name;
        try {
            InputStream is = MyFont.class.getResourceAsStream("/fonts/" + filename + ".ttf");
            if(is==null) {throw new RuntimeException("Cannot find the font file: " + filename);}
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            System.out.println("Loading font: " + filename);
        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, (int) size);
            System.out.println("Error loading font: " + e.getMessage());
        }
    }

    public Font getFont() {return font;}
}
