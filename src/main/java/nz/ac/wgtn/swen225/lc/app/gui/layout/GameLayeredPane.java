package nz.ac.wgtn.swen225.lc.app.gui.layout;

import javax.swing.*;
import java.awt.*;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

/**
 * A layered pane for the game components.
 * @author Hugh McDonald
 */
public class GameLayeredPane extends JLayeredPane {
    /**
     * Constructor for the GameLayeredPane.
     */
    public GameLayeredPane(){
        setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
    }
}
