package Characters;

import Panels.Board;
import DataTypes.Coordinate;
import Shapes.Shape;

import java.awt.*;

abstract public class Character{
    // Desired direction
    protected int desiredDirectionX;
    protected int desiredDirectionY;
    // Current direction
    protected int directionX;
    protected int directionY;
    // Character sprite
    Shape sprite;
    // Real position on board
    protected double realPosX;
    protected double realPosY;

    // Movement of character
    protected double maxMovementSpeed = 1;
    protected double movementSpeed = 1; // Movement speed
    protected boolean isMoving;

    // Tick counter used to time animations to tick
    protected int tickCounter;
    protected int maxTickCounter = 30;

    Board board;

    public Character(double i, double j, Board board) {
        this.board = board;

        realPosX = i * board.getCellSize();
        realPosY = j * board.getCellSize();

        desiredDirectionX = directionX = -1;
        desiredDirectionY = directionY = 0;
    }



    /**
     * Moves character
     */
    public void update(){
        // Can only change direction when pac man is in very center of cell
        if ((desiredDirectionX != directionX && desiredDirectionX != 0 && Math.floor(realPosY) % board.getCellSize() == 0) || (desiredDirectionY != directionY && desiredDirectionY != 0 && Math.floor(realPosX) % board.getCellSize() == 0)){

            // Change current direction and rotation
            if (!isNextCellWall(desiredDirectionX, desiredDirectionY)) {
                directionX = desiredDirectionX;
                directionY = desiredDirectionY;
                updateRotation();
            }

        }

        // Check if next cell is wall and move
        if (!isNextCellWall(directionX, directionY)) {
            updatePosition(directionX, directionY);
            isMoving = true;
        }else {
            isMoving = false;
        }
        ++tickCounter;
        if (tickCounter > maxTickCounter){
            tickCounter = 0;
        }

    }

    /**
     * Sets position of sprite and real position
     */
    private void updatePosition(double dx, double dy){
        realPosX += dx * movementSpeed;
        realPosY += dy * movementSpeed;
        updateSprite();
    }

    /**
     * Updates sprite to match position
     */
    protected void updateSprite(){
        sprite.setPos((int)realPosX, (int)realPosY);
    }


    /**
     * Updates rotation according to movement direction
     */
    protected void updateRotation(){
        if (directionX == 1){
            sprite.setRot(180);
        }else if (directionX == -1){
            sprite.setRot(0);
        }else if (directionY == 1){
            sprite.setRot(270);
        }else if (directionY == -1){
            sprite.setRot(90);
        }
    }

    // TODO move teleport into a different method
    /**
     * Checks the next cell a direction see if its a wall or not
     * @param directionX -1,0,1 x direction
     * @param directionY -1,0,1 y direction
     */
    private boolean isNextCellWall(int directionX, int directionY){
        Coordinate nextCoord = getNextPos(directionX, directionY);

        // If at edge of board teleport to other side
        if (nextCoord.getX() < 0){
            realPosX = board.getEdgeOfBoard();
            return false;
        }else  if (nextCoord.getX() >= board.getEdgeOfBoard()){
            realPosX = 0;
            return false;
        }

        // Turn into grid position
        Coordinate nextGridPos = board.getGridPosition(nextCoord.getX(), nextCoord.getY());

        if (board.checkGrid(nextGridPos.getX(), nextGridPos.getY()) == 1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Gets next coordinate position on board
     * @param directionX
     * @param directionY
     * @return
     */
    protected Coordinate getNextPos(int directionX, int directionY){
        int nextXPos = (int)Math.floor(realPosX) + directionX;
        int nextYPos = (int)Math.floor(realPosY) + directionY;

        // If going right then need to add cell size to next x pos
        if (directionX > 0){
            nextXPos += Board.getCellSize() - directionX;
        }
        // Similar to above
        if (directionY > 0){
            nextYPos += Board.getCellSize() - directionY;
        }


        // Next coordinate position on the board
        Coordinate coord = new Coordinate(nextXPos, nextYPos);
        return coord;
    }

    /**
     * Gets current grid position of character on board
     * @return Coordinate
     */
    public Coordinate getBoardPosition(){
        return board.getGridPosition((int)Math.floor(realPosX), (int)Math.floor(realPosY));
    }

    public void paintSprite(Graphics g){
        sprite.paintComponent(g);
    }



    // Setters and getters methods
    public void setDesiredDirectionX(int desiredDirectionX) {
        this.desiredDirectionX = desiredDirectionX;
    }

    public int getDesiredDirectionX() {
        return desiredDirectionX;
    }

    public void setDesiredDirectionY(int desiredDirectionY) {
        this.desiredDirectionY = desiredDirectionY;
    }

    public int getDesiredDirectionY() {
        return desiredDirectionY;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public double getMaxMovementSpeed() {
        return maxMovementSpeed;
    }


}
