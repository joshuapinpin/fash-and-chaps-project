package nz.ac.wgtn.swen225.lc.app.util;

import java.awt.Font;
import java.io.InputStream;

/**
 * Enum representing different fonts used in the application.
 * Each enum constant corresponds to a specific font file.
 * Provides methods to load and retrieve fonts with specified sizes.
 *
 * @author Joshua Pinpin (Student ID: 300662880)
 */
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

    /**
     * Retrieves the font with the specified size.
     *
     * @param size The desired font size.
     * @return The Font object with the specified size.
     */
    public Font getFont(float size) {
        return font.deriveFont(size);
    }
}
