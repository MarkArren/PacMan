package Consumables;

import Panels.Board;
import Shapes.Circle;
import java.awt.*;

public class Orange extends Consumables{
    Circle orange;
    Color colour = Color.orange;

    public Orange(double gridX, double gridY, int cellSize){
        super(500);

        // Stores where in the grid the pac dot is
        this.gridX = gridX;
        this.gridY = gridY;

        // Adds circle sprite
        updateSprite();
    }

    @Override
    public void updateSprite() {
        int size = (int)Math.floor(Board.getCellSize() / 2);
        int centerX = (int)(gridX * Board.getCellSize());
        int centerY = (int)(gridY * Board.getCellSize());
        orange = new Circle(centerX,centerY,size, 0);
    }

    public void paintSprite(Graphics g){
        Color colourCurrent = g.getColor();
        g.setColor(colour);
        orange.paintComponent(g);
        g.setColor(colourCurrent);
    }

}
