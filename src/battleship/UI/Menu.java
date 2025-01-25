package battleship.UI;

import battleship.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu
{
    private final JFrame frame;
    public Menu() {
        frame = new JFrame();
        configureFrame();
        addStartButton();
        frame.setVisible(true);
    }

    private void configureFrame() {
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setTitle("battleship - main menu");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addStartButton() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BorderLayout());

        JButton button = new JButton("start");
        button.setBackground(Color.darkGray);
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> {
            frame.dispose();
            Game.initializeGame();
        });
        panel.add(button);
        frame.add(panel);
    }
}
