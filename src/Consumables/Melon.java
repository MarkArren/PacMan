package Consumables;

import Panels.Board;
import Shapes.Circle;

import java.awt.*;

public class Melon extends Consumables{
    Circle melon;
    Color colour = Color.GREEN;

    public Melon(double gridX, double gridY, int cellSize){
        super(1000);

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
        melon = new Circle(centerX,centerY,size, 0);
    }

    public void paintSprite(Graphics g){
        Color colourCurrent = g.getColor();
        g.setColor(colour);
        melon.paintComponent(g);
        g.setColor(colourCurrent);
    }

}
