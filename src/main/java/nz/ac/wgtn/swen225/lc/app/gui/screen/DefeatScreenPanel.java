package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.game.GamePanel;

import javax.swing.*;

public class DefeatScreenPanel extends JPanel implements GamePanel {
    private AppWindow window;
    public DefeatScreenPanel(AppWindow window) {
        this.window = window;
    }
    @Override
    public void updatePanel() {

    }
}
