package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.InputController;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RightPanel extends JPanel implements ActionListener, ChangeListener, GamePanel{
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 4 ;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private GameController controller;
    private InputController inputController;
    private List<JPanel> allPanels;
    private Map<JButton, Runnable> buttonRunnableMap;

    public RightPanel(GameController controller, InputController inputController){
        this.controller = controller;
        this.inputController = inputController;
//        setBackground(Color.green);
        setOpaque(false);
        setLayout(new GridLayout(9,1, 0, 10));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setupComponents();
        setupRecorderButtons();
        setupSlider();
        add(new JLabel());
        setupSingleButton("HELP", controller::help);

    }

    private void setupComponents(){
        JLabel label = new JLabel("Recorder");
        label.setFont(MyFont.PIXEL.getFont(FONT_SIZE));
        label.setForeground(Color.white);
        add(label);
    }
    private void setupRecorderButtons(){
        buttonRunnableMap = new HashMap<>();
        setupSingleButton("Start Recorder", () -> controller.startRecording());
        setupSingleButton("Stop Recorder", () -> controller.stopRecording());
        setupSingleButton("Auto-Play", () -> controller.autoPlay());
        setupSingleButton("Step-By-Step", () -> controller.stepByStep());

    }
    private void setupSingleButton(String name, Runnable action){
        JButton button = MyButton.of(name, PANEL_WIDTH, PANEL_HEIGHT, 20,
                LoadingImg.Sand.loadImage());

        button.setFocusable(false);
        button.addActionListener(this);
        buttonRunnableMap.put(button, action);
        add(button);
    }

    public void setupSlider(){
        JSlider slider = new JSlider(0, 100, 50); // min=0, max=100, initial=50
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(false);
        slider.setOpaque(false); // transparent background if you want
        slider.addChangeListener(e -> {
            stateChanged(e);
            controller.getGameWindow().requestFocusInWindow();
        });
        add(slider);
    }

    @Override
    public void update() {
        allPanels.forEach(JPanel::repaint);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(buttonRunnableMap.containsKey(button)) buttonRunnableMap.get(button).run();
        else System.out.println("No action assigned to this button.");
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider slider) {
            int value = slider.getValue();
            // Do something with the value, e.g.:
            System.out.println("Slider value: " + value);
            // You can call a controller method here if needed
        }
    }
}
