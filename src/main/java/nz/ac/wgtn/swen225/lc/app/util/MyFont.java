package nz.ac.wgtn.swen225.lc.app.util;

import java.awt.Font;
import java.io.InputStream;


public enum MyFont {
    PIXEL("pixel-font"),
    ARIAL("Arial");

    private final String filename;
    private Font font;

    MyFont(String filename) {
        this.filename = filename;
        createFont();
    }

    private void createFont() {
        if ("Arial".equals(filename)) {
            font = new Font("Arial", Font.PLAIN, 12); // Default size, can be changed with deriveFont
            return;
        }
        try (InputStream is = MyFont.class.getResourceAsStream("/fonts/" + filename + ".ttf")) {
            if (is == null) throw new RuntimeException("Cannot find the font file: " + filename);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, 12);
            System.out.println("Error loading font: " + e.getMessage());
        }
    }

    public Font getFont(float size) {
        return font.deriveFont(size);
    }
}
