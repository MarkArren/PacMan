package Characters;

import DataTypes.Coordinate;
import Panels.Board;
import Shapes.Triangle;

import java.awt.*;
import java.util.Random;

public class Pinky extends Ghost{
    public Pinky(double i, double j, Board board, PacMan pacMan) {
        super(i, j, board, pacMan);
        // sprite = new Triangle((int)(i * board.getCellSize()), (int)(j * board.getCellSize()), board.getCellSize(), 0);
        ghostColour = Color.pink;
    }

    /**
     * Setting target tile depending on if the ghost is chasing pac man
     */
    protected void updateTargetTile(){
        if (state == 0) {

            // Pinky chooses tile four tiles ahead of pacman
            Coordinate targetTileCoord = pacman.getBoardPosition();

            // Target tile should be able to be out of grid bounds
            if (pacman.directionX == 1){
                targetTileCoord.setX((targetTileCoord.getX() + 4));
            }   else if (pacman.directionX == -1){
                targetTileCoord.setX((targetTileCoord.getX() - 4));
            }else if (pacman.directionY == 1){
                targetTileCoord.setY((targetTileCoord.getY() + 4));
            }else if (pacman.directionY == - 1){
                targetTileCoord.setY((targetTileCoord.getY() - 4));
                targetTileCoord.setX((targetTileCoord.getX() - 4));
            }
            setTargetTile(targetTileCoord);
        }else if (state == 1) {
            // When in scatter mode go to home position
            setTargetTile(new Coordinate(2, -3));
        }else  if (state == 2){
            // When in frightened mode go to random tile
            Random r = new Random();
            setTargetTile(new Coordinate(r.nextInt(board.getGridWidth() - 1), board.getGridHeight() - 1));
        }
    }
}
