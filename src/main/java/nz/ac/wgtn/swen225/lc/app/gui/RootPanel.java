package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class RootPanel extends JPanel {

    private GameController controller;

    public RootPanel(GameController controller) {
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.CENTER, 25,0));
//        setBackground(Color.BLACK);
//        setBorder(BorderFactory.createLineBorder(Color.RED, 10));
    }
}
