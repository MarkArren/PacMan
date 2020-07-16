package Characters;

import Panels.Board;
import DataTypes.Coordinate;

import java.awt.*;
import java.util.Random;

public class Blinky extends Ghost {

    public Blinky(double i, double j, Board board, PacMan pacMan) {
        super(i, j, board, pacMan);
        ghostColour = Color.red;
    }

    public void update(){



        // Check remaining dots to see if movement speed needs to increase
        int remainingConsumables = board.getConsumables().size();
        if (remainingConsumables <= 10){
            movementSpeed = pacman.getMovementSpeed() * 1.1;
        }else if (remainingConsumables <= 20){
            movementSpeed = pacman.getMovementSpeed();
        }else {
            setMovementSpeed();
        }
        super.update();
    }


    /**
     * Setting target tile depending on if the ghost is chasing pac man
     */
    protected void updateTargetTile(){

        if (state == 0) {
            // Blinky targets pacman directly
            setTargetTile(pacman.getBoardPosition());
        }else if (state == 1) {
            // When in scatter mode go to home position
            setTargetTile(new Coordinate(board.getGridWidth() - 3, -3));
        }else  if (state == 2){
            // When in frightened mode go to random tile
            Random r = new Random();
            setTargetTile(new Coordinate(r.nextInt(board.getGridWidth() - 1), board.getGridHeight() - 1));
        }

    }


}
