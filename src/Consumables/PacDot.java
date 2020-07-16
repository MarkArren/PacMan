package Consumables;

import Panels.Board;
import Shapes.Square;
import java.awt.*;

public class PacDot extends Consumables{
    Square square;
    Color colour = new Color(255,206,197);

    public PacDot(int gridX, int gridY, int cellSize){
        super(10);

        // Stores where in the grid the pac dot is
        this.gridX = gridX;
        this.gridY = gridY;

        // Adds square sprite centering it into middle of cell
        updateSprite();
    }

    @Override
    public void updateSprite() {
        int size = (int)Math.floor(Board.getCellSize() / 3.0);
        int centerX = (int)(gridX * Board.getCellSize()) + (Board.getCellSize() / 2) - (size / 2);
        int centerY = (int)(gridY * Board.getCellSize()) + (Board.getCellSize() / 2) - (size / 2);
        square = new Square(centerX,centerY,size,0);
    }

    public void paintSprite(Graphics g){
        Color colourCurrent = g.getColor();
        g.setColor(colour);
        square.paintComponent(g);
        g.setColor(colourCurrent);
    }

}
