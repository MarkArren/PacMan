package Consumables;

import Panels.Board;
import Shapes.Circle;
import java.awt.*;

public class PowerPellet extends Consumables{
    Circle circle;
    Color colour = new Color(255,206,197);

    public PowerPellet(int gridX, int gridY, int cellSize){
        super(50);

        // Stores where in the grid the pac dot is
        this.gridX = gridX;
        this.gridY = gridY;

        // Adds circle sprite
        updateSprite();
    }

    @Override
    public void updateSprite() {
        int size = (int)Math.floor(Board.getCellSize() / 2.0);
        int centerX = (int)(gridX * Board.getCellSize());
        int centerY = (int)(gridY * Board.getCellSize());
        circle = new Circle(centerX,centerY,size,0);
    }

    public void paintSprite(Graphics g){
        Color colourCurrent = g.getColor();
        g.setColor(colour);
        circle.paintComponent(g);
        g.setColor(colourCurrent);
    }

}
