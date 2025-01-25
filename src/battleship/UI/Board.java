package battleship.UI;

import battleship.Battleship;
import battleship.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.swing.*;

public abstract class Board extends JFrame
{
    protected Battleship battleship, opponent;
    protected JPanel sideBar;
    protected JPanel infoTextPanel = new JPanel();;
    protected JTextField infoText = new JTextField();
    protected JButton[] buttons = new JButton[100];
    protected JPanel main_panel;
    protected JPanel grid_panel;

    protected int size = 10;
    protected int ship_size = 0;
    protected int orientation = Game.horizontal;

    protected final Color water_color = new Color(23, 55, 211);
    protected final Color ship_color = new Color(135, 135, 142);
    protected final Color hit_color = new Color(163, 63, 63);
    protected final Color sunk_color = new Color(104, 15, 15);
    protected final Color miss_color = new Color(0, 0, 0);

    public Board(Battleship battleship, Battleship opponent) {
        this.battleship = battleship;
        this.opponent = opponent;
    }

    public JPanel getPanel() {return main_panel;}

    protected void updateFrame() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable render = this::renderBoard;
        executorService.scheduleAtFixedRate(render, 0, 200, TimeUnit.MILLISECONDS);
    }

    protected void createGrid() {
        int row = 0, column = 0, index = 0;
        grid_panel = new JPanel();
        grid_panel.setLayout(new GridLayout(size, size));
        grid_panel.setBackground(Color.GRAY);
        grid_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        for (int i = 0; i < size*size; i++) {
            JButton button = new JButton();
            button.setBackground(water_color);
            button.setForeground(Color.GRAY);
            button.setText("" + (char) (0x0041 + row) + (column+1));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            button.setLayout(new BorderLayout());
            button.setName(row + " " + column);

            ActionListener action_listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {buttonAction(button);}
                };
            button.addActionListener(action_listener);

            if (column == size-1) {column = 0; row++;}
            else {column++;}
            buttons[index] = button;
            index++;

            grid_panel.add(button);
            main_panel.add(grid_panel);
        }
    }

    protected void configureMainPanel() {
        main_panel = new JPanel();
        main_panel.setSize(400, 400);
        main_panel.setBackground(Color.darkGray);
        main_panel.setLayout(new BorderLayout());
    }

    protected abstract void buttonAction(JButton button);

    protected abstract void renderBoard();

    protected void initializeInfoText() {
        infoTextPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        infoTextPanel.setPreferredSize(new Dimension(500, 40));
        infoTextPanel.setMaximumSize(new Dimension(500, 100));
        infoTextPanel.setBackground(Color.darkGray);
        infoText.setBackground(Color.darkGray);
        infoText.setForeground(Color.white);
        infoText.setHorizontalAlignment(JTextField.CENTER);
        infoText.setEditable(false);
        infoTextPanel.add(infoText);
        main_panel.add(infoText, BorderLayout.NORTH);
    }

    public void infoText(String info) {
        infoText.setText(info);
    }

    public void infoText(String info, Color text_color) {
        infoText.setText(info);
        infoText.setForeground(text_color);

    }

    protected void addSideBarButton(String name, Function<JButton, ActionListener> actionFactory) {
        JButton button = new JButton(name);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionFactory.apply(button));

        sideBar.add(button);
    }
}
