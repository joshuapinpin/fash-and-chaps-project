

package nz.ac.wgtn.swen225.lc.app;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * GamePanel is the main view for displaying the maze and game area.
 * @author SWEN225 Team
 */
public class GamePanel extends JPanel {
    private GameController controller;

    public GamePanel(GameController controller) {
        this.controller = controller;
        // TODO: Initialize renderer component
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: Delegate drawing to renderer
    }
}
