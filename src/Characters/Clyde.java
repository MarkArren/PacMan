package Characters;

import DataTypes.Coordinate;
import Panels.Board;
import Shapes.Triangle;

import java.awt.*;
import java.util.Random;

public class Clyde extends Ghost {
    public Clyde(double i, double j, Board board, PacMan pacMan) {
        super(i, j, board, pacMan);
        ghostColour = Color.orange;

        // Changing required amounts of dots to leave monster pen depending on level
        if (board.getLevel() == 1){
            dotLimit = 60;
        }else if (board.getLevel() == 2){
            dotLimit = 50;
        }else {
            dotLimit = 0;
        }

    }

    @Override
    protected void updateTargetTile() {
        if (state == 0) {
            // Clyde targets pacman directly if 8 tiles away from pacman
            if (pacman.getBoardPosition().distance(currentTile) > 8){
                setTargetTile(pacman.getBoardPosition());
            }else{
                // Goes to home position
                setTargetTile(new Coordinate(0, board.getGridHeight() - 1));
            }

        }else if (state == 1) {
            // When in scatter mode go to home position
            setTargetTile(new Coordinate(0, board.getGridHeight() + 1));
        }else  if (state == 2){
            // When in frightened mode go to random tile
            Random r = new Random();
            setTargetTile(new Coordinate(r.nextInt(board.getGridWidth() - 1), board.getGridHeight() - 1));
        }
    }
}
