package Shapes;

import java.awt.*;

public class Square extends Shape{

    public Square(int posX, int posY, int size, int rot){
        super(posX,posY,size,rot);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        // Rotates g then draws square then rotates back
        g2d.rotate(Math.toRadians(getRot()), getPosX() + getSize()/2, getPosY() + getSize()/2);
        g.fillRect(getPosX(),getPosY(),getSize(),getSize());
        g2d.rotate(Math.toRadians(-getRot()), getPosX() + getSize()/2, getPosY() + getSize()/2);
    }


}
