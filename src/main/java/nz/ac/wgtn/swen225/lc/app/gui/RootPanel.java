package nz.ac.wgtn.swen225.lc.app.gui;

import javax.swing.*;
import java.awt.*;

public class RootPanel extends JPanel {

    public RootPanel() {
        setLayout(null);
        setBounds(0,0,GameWindow.WINDOW_WIDTH,GameWindow.WINDOW_HEIGHT);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.RED, 10));
    }
}
