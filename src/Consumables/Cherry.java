package Consumables;

import Panels.Board;
import Shapes.CircleSector;
import java.awt.*;

public class Cherry extends Consumables{
    CircleSector cherry;
    Color colour = Color.red;

    public Cherry(double gridX, double gridY, int cellSize){
        super(100);

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
        int centerY = (int)(gridY * Board.getCellSize()) + Board.getCellSize() / 4;
        cherry = new CircleSector(centerX,centerY,size,45, 90, 0);
    }


    public void paintSprite(Graphics g){
        Color colourCurrent = g.getColor();
        g.setColor(colour);
        cherry.paintComponent(g);
        g.setColor(colourCurrent);
    }




}
