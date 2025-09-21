package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
import nz.ac.wgtn.swen225.lc.app.controller.InputController;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.app.util.MyImage;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RightPanel extends JPanel implements ActionListener, ChangeListener, GamePanel{
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 6 ;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private GameController controller;
    private InputController inputController;
    private List<JComponent> allComps;
    private Map<JButton, Runnable> buttonRunnableMap;
    private BufferedImage bgImg;

    public RightPanel(GameController controller, InputController inputController){
        this.controller = controller;
        this.inputController = inputController;
        allComps = new ArrayList<>();
        setupUI();
        setupComponents();
        setupRecorderButtons();
        setupSlider();
        setupHelp();
    }

    private void setupUI(){
        setOpaque(false);
        setLayout(new GridLayout(9,1, 0, 20));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBorder(BorderFactory.createEmptyBorder(0, AppWindow.SQUARE_SIZE,
                0, AppWindow.SQUARE_SIZE));
        setOpaque(false);
        bgImg = new MyImage("water").getImage();
    }

    private void setupComponents(){
        JLabel label = new JLabel("Recorder");
        label.setFont(MyFont.PIXEL.getFont(FONT_SIZE));
        label.setForeground(Color.white);
        add(label);
        allComps.add(label);
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
        allComps.add(button);
    }

    private void setupSlider(){
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
        allComps.add(slider);
    }

    private void setupHelp(){
        JLabel label = new JLabel();
        allComps.add(label);
        setupSingleButton("HELP", controller::help);
    }

    @Override
    public void updatePanel() {
        allComps.forEach(JComponent::repaint);
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


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = AppWindow.SQUARE_SIZE;
        int x = 0, y = 0;
        while(x < PANEL_WIDTH){
            while(y < PANEL_HEIGHT){
                g.drawImage(bgImg, x, y, squareSize, squareSize, this);
                y += squareSize;
            }
            y = 0;
            x += squareSize;
        }
    }
}
