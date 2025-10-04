package nz.ac.wgtn.swen225.lc.app.gui.game;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;

import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {
    public HelpPanel(AppController controller) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(AppWindow.WINDOW_WIDTH/2, AppWindow.WINDOW_HEIGHT/2));
        setBackground(new Color(4, 44, 143));

        JLabel label = new JLabel("Help", SwingConstants.CENTER);
        label.setFont(MyFont.PIXEL.getFont(50));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton resumeButton = new JButton("Play");
        resumeButton.setFont(MyFont.PIXEL.getFont(30));
        resumeButton.addActionListener(e -> {
            controller.continueGame();
            // Close the dialog (handled in AppWindow)
            SwingUtilities.getWindowAncestor(this).dispose();

        });
        buttonPanel.add(resumeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
