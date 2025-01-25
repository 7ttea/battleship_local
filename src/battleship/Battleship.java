package battleship;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Battleship
{
    private final int[][] defense_board;
    private List<Integer> used_ship_sizes;
    private boolean can_attack = false, all_ships_placed = false, victory = false;
    private final int ships_to_be_placed = 2;
    final int water = Game.water, ship = Game.ship, hit = Game.hit, miss = Game.miss, sunk = Game.sunk;
    final int horizontal = Game.horizontal, vertical = Game.vertical;
    protected List<Ship> ships = new ArrayList<>();
    
    public Battleship() {
        defense_board = new int[10][10];
        used_ship_sizes = new ArrayList<>();
    }

    public void canAttack() {can_attack = true;}

    public boolean won() {return victory;}

    public boolean allShipsPlaced() {return all_ships_placed;}

    public int getShipsToBePlaced() {return ships_to_be_placed;}

    public void placeShip(int[] start_position, int ship_size, int ship_rotation) {
        if (all_ships_placed || used_ship_sizes.contains(ship_size)) {return;}
        int[][] ship_position = getShipPlacementPositions(start_position, ship_size, ship_rotation);
        if (positionCollidesWithShip(ship_position) || ship_size <= 0) {
            return;
        }

        ships.add(new Ship(ship_position, ship_size));
        used_ship_sizes.add(ship_size);
        if (ships.size() == ships_to_be_placed) {all_ships_placed = true;}

        for (int[] position : ship_position) {
            defense_board[position[0]][position[1]] = ship;
        }

    }
    
    public void attack(Battleship opponent, int[] position) {
        if (!can_attack){return;}
        int[][] opponent_defense_board = opponent.getDefenseBoard();
        int row = position[0], column = position[1];
        if (opponent_defense_board[row][column] == hit || opponent_defense_board[row][column] == miss) {return;}
        
        switch(opponent_defense_board[row][column]) {
            case water -> opponent.setDefenseBoardAtPosition(position, miss);
            case ship -> {
                opponent.setDefenseBoardAtPosition(position, hit);
                handleShipHit(position,opponent);
            }
        }
        Game.turn.incrementAndGet();
        can_attack = false;
    }
    
    public int[][] getDefenseBoard() {
        return defense_board;
    }
    
    private void setDefenseBoardAtPosition(int[] position, int value) {
        defense_board[position[0]][position[1]] = value;
    }
    
    private void handleShipHit(int[] position, Battleship opponent) {
        for (Ship ship : opponent.ships) {
            if (ship.isAtPosition(position)) {
                ship.gotHit();

                if (ship.sunk()) {
                    for (int[] ship_position : ship.getPosition()){
                        opponent.defense_board[ship_position[0]][ship_position[1]] = sunk;
                    }
                    opponent.ships.remove(ship);
                }
                break;
            }
        }

        if(opponent.ships.isEmpty()) {
            Game.game_over.set(true);
            victory = true;
        }
    }
    
    private boolean positionCollidesWithShip(int[][] ship_positions) {
        for (int[] ship_position : ship_positions) {
            if (defense_board[ship_position[0]][ship_position[1]] == ship) {
                return true;
            }
        }
        return false;
    }
    
    private int[][] getShipPlacementPositions(int[] start_position, int ship_size, int ship_rotation) {
        int[][] positions = new int[ship_size][2];
        
        switch(ship_rotation) {
            case horizontal:
                if((start_position[1] + ship_size - 1) >= 10) {
                    throw new InputMismatchException();
                } //check if inside the grid
                calculatePositionsHorizontal(positions, start_position, ship_size);
    
                break;
    
            case vertical:
                if((start_position[0] - ship_size - 1) >= 10) {
                    throw new InputMismatchException();
                } //check if inside the grid
                calculatePositionsVertical(positions, start_position, ship_size);
        }
        //check for overlapping
        for (int[] pos : positions) {
            if(defense_board[pos[0]][pos[1]] == ship){
                throw new InputMismatchException();
            }
        }
        return positions;
    }
    
    private void calculatePositionsVertical(int[][] positions, int[] start_position, int ship_size) {
        for (int index = 0; index < ship_size; index++) {
            positions[index][0] = start_position[0] - index;
            positions[index][1] = start_position[1];
        }
    }
    
    private void calculatePositionsHorizontal(int[][] positions, int[] start_position, int ship_size) {
        for (int index = 0; index < ship_size; index++) {
            positions[index][0] = start_position[0];
            positions[index][1] = start_position[1] + index;
        }
    }

}
