package nz.ac.wgtn.swen225.lc.app.gui;

import nz.ac.wgtn.swen225.lc.app.state.PlayState;
import nz.ac.wgtn.swen225.lc.app.util.MyFont;
import nz.ac.wgtn.swen225.lc.renderer.imgs.LoadingImg;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartScreenPanel extends JPanel implements GamePanel{
    private AppWindow window;
    private BufferedImage bgImg;

    public StartScreenPanel(AppWindow window) {
        this.window = window;
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Fash and Chaps", SwingConstants.CENTER);
        title.setFont(MyFont.PIXEL.getFont(50));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> window.showScreen(PlayState.name()));
        add(startButton, BorderLayout.SOUTH);

    }

    @Override
    public void updatePanel() {

    }


}
