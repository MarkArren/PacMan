package Shapes;

import java.awt.*;

public class Triangle extends Shape{

    public Triangle(int posX, int posY, int length, int rot){
        super(posX,posY,length,rot);
    }

    @Override
    public void paintComponent(Graphics g) {
        int xValues[] = new int[]{getPosX(),getPosX() + (getSize()/2),getPosX() + getSize()};
        int yValues[] = new int[]{getPosY() + getSize(),getPosY(),getPosY() + getSize()};

        Graphics2D g2d = (Graphics2D) g;

        // Rotates g then draws triangle then rotates back
        g2d.rotate(Math.toRadians(getRot()), getPosX() + getSize()/2, getPosY() + getSize()/2);
        g.fillPolygon(xValues, yValues, 3);
        g2d.rotate(Math.toRadians(-getRot()), getPosX() + getSize()/2, getPosY() + getSize()/2);
    }
}
