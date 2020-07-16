package Shapes;

import java.awt.*;

public class CircleSector extends Shape {
    private int startAngle;
    private int arcAngle;

    public CircleSector(int posX, int posY, int radius, int startAnagle, int arcAngle,int rot){
        super(posX,posY,radius,rot);
        this.startAngle = startAnagle;
        this.arcAngle = arcAngle;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(int arcAngle) {
        this.arcAngle = arcAngle;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Rotates g then draws circle sector then rotates back
        g2d.rotate(Math.toRadians(getRot()), getPosX() + getSize(), getPosY() + getSize());
        g.fillArc(getPosX(),getPosY(),getSize()*2,getSize()*2,startAngle,arcAngle);
        g2d.rotate(Math.toRadians(-getRot()), getPosX() + getSize(), getPosY() + getSize());
    }
}
