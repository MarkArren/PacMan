package Consumables;

import Panels.Board;
import Shapes.Circle;

import java.awt.*;

public class Bell extends Consumables{
    Circle apple;
    Color colour = Color.YELLOW;

    public Bell(double gridX, double gridY, int cellSize){
        super(3000);

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
        apple = new Circle(centerX,centerY,size, 0);
    }

    public void paintSprite(Graphics g){
        Color colourCurrent = g.getColor();
        g.setColor(colour);
        apple.paintComponent(g);
        g.setColor(colourCurrent);
    }

}
