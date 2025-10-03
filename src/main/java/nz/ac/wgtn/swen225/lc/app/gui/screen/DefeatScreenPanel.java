package nz.ac.wgtn.swen225.lc.app.gui.screen;

import nz.ac.wgtn.swen225.lc.app.gui.AppWindow;
import nz.ac.wgtn.swen225.lc.app.gui.game.GamePanel;
import nz.ac.wgtn.swen225.lc.app.state.PlayState;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DefeatScreenPanel extends JPanel implements GamePanel {
    private AppWindow window;
    private BufferedImage bgImg;

    public DefeatScreenPanel(AppWindow window) {
        this.window = window;
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Defeat :(((", SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(50));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> window.showScreen(PlayState.name()));
        add(startButton, BorderLayout.SOUTH);

        bgImg = LoadingImg.StartScreen.loadImage();
    }
    @Override
    public void updatePanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
    }
}
