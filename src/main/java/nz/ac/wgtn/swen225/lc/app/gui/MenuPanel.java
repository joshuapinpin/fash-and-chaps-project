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
        setupButtons();
    }

    private void setupButtons(){
        buttonRunnableMap = new HashMap<>();
        setupSingleButton("Save", this::saveButtonAction);
        setupSingleButton("Pause", this::pauseButtonAction);
        setupSingleButton("Resume", this::resumeButtonAction);
        setupSingleButton("Exit", this::exitButtonAction);
    }

    private void setupSingleButton(String label, Runnable action){
        JButton button = new JButton(label);
        button.addActionListener(this);
        button.setFocusable(false);
        buttonRunnableMap.put(button, action);
        add(button);
    }

    private void pauseButtonAction(){
        System.out.println("Pause button pressed");
    }

    private void saveButtonAction(){
        System.out.println("Save button pressed");
    }

    private void exitButtonAction(){
        System.out.println("Exit button pressed");
        System.exit(0);
    }

    private void resumeButtonAction(){
        System.out.println("Resume button pressed");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(buttonRunnableMap.containsKey(button)) buttonRunnableMap.get(button).run();
        else System.out.println("No action assigned to this button.");
    }
}
