package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    public static final int PANEL_WIDTH = (GameWindow.WINDOW_WIDTH / 4) ;
    public static final int PANEL_HEIGHT = (GameWindow.WINDOW_HEIGHT / 4) * 3;

    private GameController controller;

    public RightPanel(GameController controller){
        this.controller = controller;
//        setBackground(Color.green);
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel label = new JLabel("Inventory");
        label.setFont(new MyFont("pixel-font", 40).getFont());
        label.setForeground(Color.white);
        add(label);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    private void drawKeys(Graphics g){
        int keys = controller.getDomain().getPlayer().getKeys().size();

    }
}
