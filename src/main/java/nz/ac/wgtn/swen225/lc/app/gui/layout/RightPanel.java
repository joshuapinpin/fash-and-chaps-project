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

    private AppController c;
    private RecorderController recorderController;
    private List<JComponent> allComps;
    private Map<JButton, Runnable> buttonRunnableMap;
    private BufferedImage bgImg;

    private int currentSpeed = 1;
    private JLabel speedLabel;

    /**
     * Constructor for RightPanel.
     * @param c AppController
     */
    public RightPanel(AppController c){
        this.c = c;
        this.recorderController = c.recorderController();
        allComps = new ArrayList<>();
        setupUI();
        setupComponents();
    }

    private void setupUI(){
        setOpaque(false);
        setLayout(new GridLayout(9,1, 0, 10));
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
        speedLabel = new JLabel("Set Speed: " + currentSpeed + "x", SwingConstants.CENTER);
        speedLabel.setFont(MyFont.PIXEL.getFont(AppWindow.FONT_SIZE_H3));
        speedLabel.setForeground(Color.white);

        add(speedLabel);
        allComps.add(speedLabel);

        JPanel panel = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int squareSize = AppWindow.SQUARE_SIZE;
                int x = 0, y = 0;
                while(x < getWidth()){
                    while(y < getHeight()){
                        g.drawImage(LoadingImg.Sand.loadImage(), x, y, squareSize, squareSize, this);
                        y += squareSize;
                    }
                    y = 0;
                    x += squareSize;
                }
            }
        };
        JSlider slider = getJSlider();
        panel.add(slider);
        add(panel);
        allComps.add(slider);
    }

    private JSlider getJSlider(){
        JSlider slider = new JSlider(1, 10, 1);
        slider.setMajorTickSpacing(1);
        slider.setBackground(Color.WHITE);
        slider.setForeground(Color.BLACK);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        slider.setOpaque(false); // transparent background if you want
        slider.addChangeListener(e -> {
            stateChanged(e);
            c.windowController().window().requestFocusInWindow();
        });
        return slider;
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
            currentSpeed = value;
            System.out.println("Slider value: " + currentSpeed);
            updateSpeedLabel();
            allComps.forEach(JComponent::repaint);

        }
    }

    public void updateSpeedLabel(){
        speedLabel.setText("Set Speed: " + currentSpeed + "x");
    }

    /**
     * Paints the background of the panel with a tiled image.
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
