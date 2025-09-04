

package nz.ac.wgtn.swen225.lc.app;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * ControlPanel contains buttons for pause, save, resume, and help.
 * @author SWEN225 Team
 */
public class ControlPanel extends JPanel {
    public JButton pauseButton;
    public JButton saveButton;
    public JButton resumeButton;
    public JButton helpButton;
    private GameController controller;

    public ControlPanel(GameController controller) {
        this.controller = controller;
        setLayout(new GridLayout(1, 4));
        pauseButton = new JButton("Pause");
        saveButton = new JButton("Save");
        resumeButton = new JButton("Resume");
        helpButton = new JButton("Help");
        add(pauseButton);
        add(saveButton);
        add(resumeButton);
        add(helpButton);
        // TODO: Add action listeners to buttons
    }
}
