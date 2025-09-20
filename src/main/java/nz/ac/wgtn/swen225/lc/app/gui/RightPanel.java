package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RightPanel extends JPanel implements GamePanel{

//    public static final int PANEL_WIDTH = (AppWindow.WINDOW_WIDTH / 4) ;
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 4 ;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private GameController controller;
    private List<JPanel> allPanels;

    public RightPanel(GameController controller){
        this.controller = controller;
//        setBackground(Color.green);
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel label = new JLabel("Recorder");
        label.setFont(MyFont.PIXEL.getFont(FONT_SIZE));
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

    @Override
    public void update() {
        allPanels.forEach(JPanel::repaint);
    }
}
