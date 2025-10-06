package nz.ac.wgtn.swen225.lc.app.gui.layout;

import nz.ac.wgtn.swen225.lc.app.controller.AppController;
import nz.ac.wgtn.swen225.lc.app.controller.module.RecorderController;
import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.util.MyButton;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
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

public class RightPanel extends JPanel implements ActionListener, ChangeListener {
    public static final int PANEL_WIDTH = AppWindow.SQUARE_SIZE * 6 ;
    public static final int PANEL_HEIGHT = AppWindow.MAZE_SIZE;
    public static final int FONT_SIZE = 40;

    private AppController controller;
    private RecorderController recorderController;
    private List<JComponent> allComps;
    private Map<JButton, Runnable> buttonRunnableMap;
    private BufferedImage bgImg;

    /**
     * Constructor for RightPanel.
     * @param controller AppController
     */
    public RightPanel(AppController controller){
        this.controller = controller;
        this.recorderController = controller.recorderController();
        allComps = new ArrayList<>();
        setupUI();
        setupComponents();
    }

    private void setupUI(){
        setOpaque(false);
        setLayout(new GridLayout(9,1, 0, 20));
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBorder(BorderFactory.createEmptyBorder(
                0, AppWindow.SQUARE_SIZE,
                0, AppWindow.SQUARE_SIZE));
        setOpaque(false);
        bgImg = LoadingImg.Water.loadImage();
    }

    private void setupComponents(){
        JLabel label = new JLabel("Recorder");
        label.setFont(MyFont.PIXEL.getFont(FONT_SIZE));
        label.setForeground(Color.white);
        add(label);
        allComps.add(label);

        setupRecorderButtons();
        setupSlider();

        JLabel helpLabel = new JLabel();
        allComps.add(helpLabel);
        setupSingleButton("HELP", controller::help);
    }

    private void setupRecorderButtons(){
        buttonRunnableMap = new HashMap<>();
        setupSingleButton("Start", () -> recorderController.startRecording());
        setupSingleButton("Stop", () -> recorderController.stopRecording());
        setupSingleButton("Auto-Play", () -> recorderController.autoPlay());
        setupSingleButton("Step-By-Step", () -> recorderController.stepByStep());

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
            controller.windowController().window().requestFocusInWindow();
        });
        add(slider);
        allComps.add(slider);
    }

    /**
     * Invoked when button is pressed
     * Runs the methods assigned to the button
     * @param e the event to be processed (button)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(buttonRunnableMap.containsKey(button)) buttonRunnableMap.get(button).run();
        else System.out.println("No action assigned to this button.");
    }

    /**
     * Invoked when the slider is changed.
     * Changes and sets speed for recorder
     * @param e  a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider slider) {
            int value = slider.getValue();
            recorderController.setSpeed(value);
            System.out.println("Slider value: " + value);
        }
    }

    /**
     * Paints the background of the panel with a tiled image.
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        int squareSize = AppWindow.SQUARE_SIZE;
//        int x = 0, y = 0;
//        while(x < PANEL_WIDTH){
//            while(y < PANEL_HEIGHT){
//                g.drawImage(bgImg, x, y, squareSize, squareSize, this);
//                y += squareSize;
//            }
//            y = 0;
//            x += squareSize;
//        }
    }
}
