package Consumables;

import java.awt.*;

public abstract class Consumables {
    protected int points;
    protected double gridX;
    protected double gridY;

    Consumables(int points){
        this.points = points;
    }

    public abstract void paintSprite(Graphics g);

    abstract public void updateSprite();

    // Getter and setter methods
    public int getPoints() {
        return points;
    }

    public double getGridX() {
        return gridX;
    }

    public void setGridX(double gridX) {
        this.gridX = gridX;
    }

    public double getGridY() {
        return gridY;
    }

    public void setGridY(double gridY) {
        this.gridY = gridY;
    }
}
