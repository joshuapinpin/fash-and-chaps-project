package nz.ac.wgtn.swen225.lc.app.gui.layout;

import javax.swing.*;
import java.awt.*;

import static nz.ac.wgtn.swen225.lc.app.gui.AppWindow.MAZE_SIZE;

public class GameLayeredPane extends JLayeredPane {
    public GameLayeredPane(){
        setPreferredSize(new Dimension(MAZE_SIZE, MAZE_SIZE));
        setLayout(null);
    }
}
