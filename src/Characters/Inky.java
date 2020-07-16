package Characters;

import DataTypes.Coordinate;
import Panels.Board;

import java.awt.*;
import java.util.Random;

public class Inky extends Ghost {
    Blinky blinky;
    public Inky(double i, double j, Board board, PacMan pacMan, Blinky blinky) {
        super(i, j, board, pacMan);
        this.blinky = blinky;
        ghostColour = Color.cyan;

        // Changing required amounts of dots to leave monster pen depending on level
        if (board.getLevel() == 1){
            dotLimit = 30;
        }else {
            dotLimit = 0;
        }
    }

    @Override
    protected void updateTargetTile() {
        if (state == 0) {
            // Inky uses pacman and blinkys direction for a target


            // Gets two positions in front of pacman
            // Target tile should be able to be out of grid bounds
            Coordinate pacmanOffset = pacman.getBoardPosition();
            if (pacman.directionX == 1){
                pacmanOffset.setX((pacmanOffset.getX() + 2));// % (board.getGridWidth()));
            }   else if (pacman.directionX == -1){
                pacmanOffset.setX((pacmanOffset.getX() - 2));// % (board.getGridWidth()));
            }else if (pacman.directionY == 1){
                pacmanOffset.setY((pacmanOffset.getY() + 2));// % (board.getGridHeight()));
            }else if (pacman.directionY == - 1){
                pacmanOffset.setY((pacmanOffset.getY() - 2));// % (board.getGridHeight()));
                pacmanOffset.setX((pacmanOffset.getX() - 2));// % (board.getGridWidth()));
            }

            Coordinate blinkyCoord = blinky.getCurrentTile();

            // Find distance between blinky and pacman offset then double it setting new target tile
            int x = pacmanOffset.getX() - blinkyCoord.getX();
            x += pacmanOffset.getX();
            int y = pacmanOffset.getY() - blinkyCoord.getY();
            y += pacmanOffset.getY();

            Coordinate targetTile = new Coordinate(x, y);
            setTargetTile(targetTile);


        }else if (state == 1) {
            // When in scatter mode go to home position
            setTargetTile(new Coordinate(board.getGridWidth() - 1, board.getGridHeight() + 1));
        }else  if (state == 2){
            // When in frightened mode go to random tile
            Random r = new Random();
            setTargetTile(new Coordinate(r.nextInt(board.getGridWidth() - 1), board.getGridHeight() - 1));
        }
    }
}
