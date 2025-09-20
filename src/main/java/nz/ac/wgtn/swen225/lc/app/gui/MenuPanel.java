package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;

public class MenuPanel extends JPanel implements ActionListener {
    // Size fields
    public static final int PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
    public static final int PANEL_HEIGHT = GameWindow.WINDOW_HEIGHT / 8;
    public static final int BUTTON_GAP = 10;

    // Buttons: Pause, Save, Exit, Resume
    private Map<JButton, Runnable> buttonRunnableMap;

    private GameController controller;

    MenuPanel(GameController controller){
        this.controller = controller;
        setLayout(new GridLayout(1, 4, BUTTON_GAP, BUTTON_GAP));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBorder(BorderFactory.createEmptyBorder(BUTTON_GAP, BUTTON_GAP, BUTTON_GAP, BUTTON_GAP));
        setBackground(Color.red);
        setupButtons();
    }

    private void setupButtons(){
        buttonRunnableMap = new HashMap<>();
        setupSingleButton("Save", () -> controller.saveGame());
        setupSingleButton("Pause", () -> controller.pauseGame());
        setupSingleButton("Resume", () -> controller.resumeGame());
        setupSingleButton("Exit", () -> controller.exitGame());
    }

    private void setupSingleButton(String label, Runnable action){
        JButton button = new JButton(label);
        button.addActionListener(this);
        button.setFocusable(false);
        buttonRunnableMap.put(button, action);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(buttonRunnableMap.containsKey(button)) buttonRunnableMap.get(button).run();
        else System.out.println("No action assigned to this button.");
    }
}
