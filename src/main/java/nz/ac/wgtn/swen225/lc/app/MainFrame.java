package nz.ac.wgtn.swen225.lc.app;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private GamePanel gamePanel;
    private StatusPanel statusPanel;
    private ControlPanel controlPanel;
    private GameController controller;

    public MainFrame() {
        super("SWEN225's Adventures");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 700);

        controller = new GameController(this);
        gamePanel = new GamePanel(controller);
        statusPanel = new StatusPanel(controller);
        controlPanel = new ControlPanel(controller);

        add(gamePanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);

        setJMenuBar(new AppMenuBar(controller));
        controller.initialize();
    }

//    public void showPauseDialog() {
//        PauseDialog.showDialog(this, controller);
//    }
//
//    public void showHelpDialog() {
//        HelpDialog.showDialog(this);
//    }

    public GamePanel gamePanel(){return gamePanel;    }
    public StatusPanel statusPanel(){return statusPanel;}
    public ControlPanel controlPanel() {return controlPanel;}
}