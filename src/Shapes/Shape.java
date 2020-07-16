package Shapes;

import java.awt.*;

public abstract class Shape{
    private volatile int posX;
    private volatile int posY;
    private int size;
    private int rot;


    public Shape(int posX, int posY, int size, int rot){
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.rot = rot;
    }

    public int getRot() {
        return rot;
    }

    /**
     * Sets rotation of shape
     * @param rot - rotation
     */
    public void setRot(int rot) {
        this.rot = rot;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    /**
     * Sets x and y position of shape
     * @param posX - x position
     * @param posY - y position
     */
    public void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Paints shape on screen
     * @param g Graphics
     */
    abstract public void paintComponent(Graphics g);

}

