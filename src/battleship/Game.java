package battleship;

import battleship.UI.GameWindow;
import battleship.UI.Menu;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Game
{
    private static Battleship battleship1, battleship2;

    private static GameWindow p1_window, p2_window;

    protected static AtomicBoolean game_over = new AtomicBoolean(false);
    protected static AtomicInteger turn = new AtomicInteger(1);;
    public final static int water = 0, ship = 1, hit = 2, miss = 3, sunk = 4;
    public final static int horizontal = 0, vertical = 1;
    private final static String wait_for_opponent_ships = "waiting for opponent to place their ships...",
                                place_your_ships = "place your ships: ",
                                your_turn = "it's your turn",
                                won = "you won",
                                lost = "you lost";

    public static void initializeGame() {
        battleship1 = new Battleship();
        battleship2 = new Battleship();

        p1_window = new GameWindow(battleship1, battleship2, "p1");
        p2_window = new GameWindow(battleship2, battleship1, "p2");

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable waitForShipPlacement = () -> {
            if (battleship1.allShipsPlaced() && battleship2.allShipsPlaced()) {
                startAttackingPhase();
                p1_window.getDefenseBoard().infoText("");
                p2_window.getDefenseBoard().infoText("");
                executorService.close();
            }
            if (battleship1.allShipsPlaced()) {p1_window.getDefenseBoard().infoText(wait_for_opponent_ships);}
            else {p1_window.getDefenseBoard().infoText(place_your_ships +
                    Math.abs(battleship1.ships.size() - battleship1.getShipsToBePlaced()) + " ships remaining");}

            if (battleship2.allShipsPlaced()) {p2_window.getDefenseBoard().infoText(wait_for_opponent_ships);}
            else {p2_window.getDefenseBoard().infoText(place_your_ships +
                    Math.abs(battleship2.ships.size() - battleship2.getShipsToBePlaced()) + " ships remaining");}
        };
        executorService.scheduleAtFixedRate(waitForShipPlacement, 0, 100, TimeUnit.MILLISECONDS);

    }

    private static void startAttackingPhase() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Runnable attackSwitching = () -> {
            if (game_over.get()) {
                if (battleship1.won()) {
                    p1_window.getAttackBoard().infoText(won, Color.green);
                    p2_window.getAttackBoard().infoText(lost, Color.red);
                }
                else {
                    p1_window.getAttackBoard().infoText(lost, Color.red);
                    p2_window.getAttackBoard().infoText(won, Color.green);
                }
                executorService.close();
            }
            switch (turn.get()){
                case 1 -> {
                    battleship1.canAttack();
                    p1_window.getAttackBoard().infoText(your_turn);
                    p2_window.getAttackBoard().infoText("");
                }
                case 2 -> {
                    battleship2.canAttack();
                    p2_window.getAttackBoard().infoText(your_turn);
                    p1_window.getAttackBoard().infoText("");
                }
                case 3 -> turn.set(1);
            }
        };
        executorService.scheduleAtFixedRate(attackSwitching, 0, 100, TimeUnit.MILLISECONDS);
    }


    public static void main(String[] args) {
        new Menu();
    }
}
