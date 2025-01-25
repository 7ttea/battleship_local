package battleship;

import java.util.Arrays;

public class Ship
{
    private final int[][] position;
    private int size;
    
    public Ship(int[][] ship_position, int ship_size) {
        this.position = ship_position;
        this.size = ship_size;
    }

    protected int[][] getPosition() {return position;}

    protected boolean isAtPosition(int[] check_position) {
        for (int[] pos : position) {
            if(Arrays.equals(pos, check_position)) {return true;}
        }
        return false;
    }
    
    protected void gotHit() {
        size--;
    }
    
    protected boolean sunk() {
        return size == 0;
    }
}
