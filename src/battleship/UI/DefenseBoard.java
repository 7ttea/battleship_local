package battleship.UI;

import battleship.Battleship;
import battleship.Game;

import java.awt.*;
import javax.swing.*;

public class DefenseBoard extends Board
{
    public DefenseBoard(Battleship battleship, Battleship opponent) {
        super(battleship, opponent);

        configureMainPanel();
        createGrid();
        configureSideBar();
        initializeInfoText();
        infoText("");
        updateFrame();

        main_panel.setVisible(true);
    }

    protected void configureSideBar() {
        sideBar = new JPanel();
        sideBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        sideBar.setBackground(Color.DARK_GRAY);
        sideBar.setLayout(new GridLayout(1, 7));
        addSideBarButton("1", button -> e -> {ship_size = 1; button.setEnabled(false);});
        addSideBarButton("2", button -> e -> {ship_size = 2; button.setEnabled(false);});
        addSideBarButton("3", button -> e -> {ship_size = 3; button.setEnabled(false);});
        addSideBarButton("4", button -> e -> {ship_size = 4; button.setEnabled(false);});
        addSideBarButton("5", button -> e -> {ship_size = 5; button.setEnabled(false);});
        addSideBarButton("--", button -> e -> orientation = Game.horizontal);
        addSideBarButton("|", button -> e -> orientation = Game.vertical);

        main_panel.add(sideBar, BorderLayout.SOUTH);
    }

    protected void buttonAction(JButton button) {
        int[] start_position = new int[2];
        start_position[0] = Integer.parseInt(button.getName().split(" ")[0]);
        start_position[1] = Integer.parseInt(button.getName().split(" ")[1]);
        battleship.placeShip(start_position, ship_size, orientation);
        renderBoard();
    }

    protected void renderBoard() {
        int[][] board = battleship.getDefenseBoard();
        int index = 0;

        for(int row = 0; row < 10; row++) {
            for(int column = 0; column < 10; column++) {
                switch(board[row][column]) {
                    case Game.water -> buttons[index].setBackground(water_color);
                    case Game.ship -> buttons[index].setBackground(ship_color);
                    case Game.miss -> buttons[index].setBackground(miss_color);
                    case Game.hit -> buttons[index].setBackground(hit_color);
                    case Game.sunk -> buttons[index].setBackground(sunk_color);
                }
                index++;
            }
        }
    }

}
