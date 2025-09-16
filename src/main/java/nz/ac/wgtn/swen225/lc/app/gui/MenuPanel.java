package nz.ac.wgtn.swen225.lc.app.gui;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;

public class MenuPanel extends JPanel implements ActionListener {
    // Size fields
    public static final int PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
    public static final int PANEL_HEIGHT = GameWindow.WINDOW_HEIGHT / 8;

    // Buttons: Pause, Save, Exit, Resume
    private Map<JButton, Runnable> buttonRunnableMap;

    private GameWindow window;
    MenuPanel(GameWindow window){
        this.window = window;
//        setBackground(Color.green);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setupButtons();
    }

    private void setupButtons(){
        JButton pauseButton = new JButton("Pause");
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");
        JButton resumeButton = new JButton("Resume");

        pauseButton.addActionListener(this);
        saveButton.addActionListener(this);
        exitButton.addActionListener(this);
        resumeButton.addActionListener(this);

        buttonRunnableMap = new HashMap<>();
        buttonRunnableMap.put(pauseButton, this::pauseButtonAction);
        buttonRunnableMap.put(saveButton, this::saveButtonAction);
        buttonRunnableMap.put(exitButton, this::exitButtonAction);
        buttonRunnableMap.put(resumeButton, this::resumeButtonAction);

        add(saveButton);
        add(pauseButton);
        add(resumeButton);
        add(exitButton);
    }

//    private void setupSingleButton(String label, Runnable action){
//
//        button.addActionListener(this);
//        buttonRunnableMap.put(button, action);
//        add(button);
//    }

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
