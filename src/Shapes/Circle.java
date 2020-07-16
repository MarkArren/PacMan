package Shapes;

import java.awt.*;

public class Circle extends Shape {

    public Circle(int posX, int posY, int radius, int rot){
        super(posX,posY,radius,rot);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.fillOval(getPosX(),getPosY(), getSize()*2, getSize()*2);
    }
}
