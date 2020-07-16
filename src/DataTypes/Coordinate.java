package DataTypes;

import Panels.MainFrame;

import static java.lang.Math.pow;

/**
 * Object which a coordinate consisting of two integers
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Constructs coordinate object
     * @param x
     * @param y
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    // Checks if two coordinates are equal
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (!(o instanceof Coordinate)){
            return false;
        }
        Coordinate coord = (Coordinate) o;
        if (x == coord.getX() && y == coord.getY()){
            return true;
        }
        return false;
    }

    /**
     * Figures distance from this to the other coordinate
     * @param coord2 other coordinate
     * @return distance rounded
     */
    public double distance(Coordinate coord2){
        double xDiff = Math.pow(coord2.getX() - this.x,2.0);
        double yDiff = Math.pow(coord2.getY() - this.y,2);

        return (Math.sqrt(xDiff + yDiff));
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    // Set and get methods for both variables
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
