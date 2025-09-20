package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.controller.GameController;
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
    private List<JPanel> allPanels;
    private Map<JButton, Runnable> buttonRunnableMap;

    public RightPanel(GameController controller){
        this.controller = controller;
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
        JButton button = new JButton(name){
            private boolean hovered = false;
            private boolean pressed = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hovered = true;
                        repaint();
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hovered = false;
                        pressed = false;
                        repaint();
                    }
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        pressed = true;
                        repaint();
                    }
                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                        pressed = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                int x = 0, y = 0;
                while(x < PANEL_WIDTH){
                    while(y < PANEL_HEIGHT){
                        g.drawImage(LoadingImg.Sand.loadImage(), x, y,
                                AppWindow.SQUARE_SIZE, AppWindow.SQUARE_SIZE, this);
                        y += AppWindow.SQUARE_SIZE;
                    }
                    y = 0;
                    x += AppWindow.SQUARE_SIZE;
                }
                if (hovered) {
                    // Draw a semi-transparent black overlay for a darker tint
                    g.setColor(new Color(0, 0, 0, 80)); // alpha 80 for subtle darkness
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                if (pressed) {
                    g.setColor(new Color(0, 0, 0, 140)); // even darker for pressed
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
            }
        };
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setFont(MyFont.PIXEL.getFont(20));
        button.setForeground(Color.white);
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
        slider.addChangeListener(this); // so stateChanged() will be called
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
