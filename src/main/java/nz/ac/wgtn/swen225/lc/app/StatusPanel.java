

package nz.ac.wgtn.swen225.lc.app;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * StatusPanel displays time left, current level, keys collected, and treasures remaining.
 * @author SWEN225 Team
 */
public class StatusPanel extends JPanel {
    public JLabel timeLabel;
    public JLabel levelLabel;
    public JLabel keysLabel;
    public JLabel treasuresLabel;
    private GameController controller;

    public StatusPanel(GameController controller) {
        this.controller = controller;
        setLayout(new GridLayout(1, 4));
        timeLabel = new JLabel("Time: 00:00");
        levelLabel = new JLabel("Level: 1");
        keysLabel = new JLabel("Keys: 0");
        treasuresLabel = new JLabel("Treasures: 0");
        add(timeLabel);
        add(levelLabel);
        add(keysLabel);
        add(treasuresLabel);
    }
}
