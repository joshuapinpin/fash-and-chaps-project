package nz.ac.wgtn.swen225.lc.app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AppMenuBar provides the main menu bar for the application window.
 * @author SWEN225 Team
 */
public class AppMenuBar extends JMenuBar {
    public AppMenuBar(GameController controller) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.pauseGame(); // Or call exit logic
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        JMenu gameMenu = new JMenu("Game");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> controller.saveGame());
        JMenuItem resumeItem = new JMenuItem("Resume");
        resumeItem.addActionListener(e -> controller.resumeGame());
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Game rules and help info here."));
        gameMenu.add(saveItem);
        gameMenu.add(resumeItem);
        gameMenu.add(helpItem);

        add(fileMenu);
        add(gameMenu);
    }
}
