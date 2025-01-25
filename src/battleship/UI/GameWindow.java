package battleship.UI;

import battleship.Battleship;

import javax.swing.*;
public class GameWindow {

    private final JFrame frame = new JFrame();
    private final Board attack_board, defense_board;

    public GameWindow(Battleship battleship, Battleship opponent, String player) {
        configureFrame(player);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        attack_board = new AttackBoard(battleship, opponent);
        defense_board = new DefenseBoard(battleship, opponent);
        container.add(attack_board.getPanel());
        container.add(defense_board.getPanel());

        frame.add(container);
        frame.setVisible(true);
    }

    public Board getDefenseBoard() {return defense_board;}

    public Board getAttackBoard() {return attack_board;}

    private void configureFrame(String player) {
        frame.setTitle("battleship - " + player);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 900);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

}
