package Shapes;

import java.awt.*;

public class Rectangle extends Shape {
    private int height;

    public Rectangle(int posX, int posY, int width, int height,int rot){
        super(posX, posY, width, rot);
        this.height = height;

    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        // Rotates g then draws rectangle then rotates back
        g2d.rotate(Math.toRadians(getRot()), getPosX() + getSize()/2, getPosY() + height/2);
        g.fillRect(getPosX(),getPosY(),getSize(), height);
        g2d.rotate(Math.toRadians(-getRot()), getPosX() + getSize()/2, getPosY() + height/2);
    }
}
