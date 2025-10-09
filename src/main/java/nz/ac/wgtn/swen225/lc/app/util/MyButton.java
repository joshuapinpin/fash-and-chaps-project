package nz.ac.wgtn.swen225.lc.app.util;

import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MyButton {
    JButton button;

    /** Factory method to create a button with specified properties.
     * @param name The text to display on the button.
     * @param w The width of the button.
     * @param h The height of the button.
     * @param fontSize The font size of the button text.
     * @param img The background image for the button (can be null).
     * @return A JButton instance with the specified properties.
     */
    public static JButton of(String name, int w, int h, int fontSize, BufferedImage img){
        return new MyButton(name, w, h, fontSize, img).button();
    }

    private JButton button(){return button;}

    private MyButton(String name, int w, int h, int fontSize, BufferedImage img){
        button = new JButton(name){
            private boolean hovered = false;
            private boolean pressed = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hovered = true;
                        repaint();
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        hovered = false;
                        pressed = false;
                        repaint();
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        pressed = true;
                        repaint();
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        pressed = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                if(img != null) drawImg(g);
                if (hovered) {
                    // Draw a semi-transparent black overlay for a darker tint
                    g.setColor(new Color(0, 0, 0, 80)); // alpha 80 for subtle darkness
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                if (pressed) {
                    g.setColor(new Color(0, 0, 0, 140)); // even darker for pressed
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }

            private void drawImg(Graphics g){
                int x = 0, y = 0;
                while(x < w){
                    while(y < h){
                        g.drawImage(img, x, y,
                                AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
                        y += AppWindow.SQUARE_SIZE;
                    }
                    y = 0;
                    x += AppWindow.SQUARE_SIZE;
                }
            }
        };

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFont(MyFont.PIXEL.getFont(fontSize));
        button.setForeground(Color.white);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
    }
}
