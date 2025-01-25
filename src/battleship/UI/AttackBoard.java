package battleship.UI;

import battleship.Battleship;
import battleship.Game;

import javax.swing.*;

public class AttackBoard extends Board
{

    public AttackBoard(Battleship battleship, Battleship opponent) {
        super(battleship, opponent);

        configureMainPanel();
        createGrid();
        initializeInfoText();
        infoText("");
        updateFrame();

        main_panel.setVisible(true);
    }

    protected void buttonAction(JButton button) {
        int[] position = new int[2];
        position[0] = Integer.parseInt(button.getName().split(" ")[0]);
        position[1] = Integer.parseInt(button.getName().split(" ")[1]);
        battleship.attack(opponent, position);
        renderBoard();
    }

    protected void renderBoard() {
        int[][] board = opponent.getDefenseBoard();
        int index = 0;

        for(int row = 0; row < 10; row++) {
            for(int column = 0; column < 10; column++) {
                switch(board[row][column]) {
                    case Game.hit -> buttons[index].setBackground(hit_color);
                    case Game.miss -> buttons[index].setBackground(miss_color);
                    case Game.sunk -> buttons[index].setBackground(sunk_color);
                }
                index++;
            }
        }
    }

}
