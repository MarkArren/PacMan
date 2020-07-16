package Characters;

import DataTypes.GhostMovementSpeed;
import Panels.Board;
import DataTypes.Coordinate;
import Shapes.Triangle;
// import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.awt.*;
import java.util.*;

abstract public class Ghost extends Character {
    protected Coordinate currentTile;
    protected Coordinate nextTile;
    protected Coordinate targetTile;
    protected PacMan pacman;
    protected Color ghostColour;

    protected int dotCounter = 0;
    protected int dotLimit = 0;
    protected boolean isInMonsterPen = false;
    protected int state = 0; // State of ghost (0 = chase, 1 = scatter, 2 = frightened)

    protected ArrayList<GhostMovementSpeed> movementSpeeds = new ArrayList<>();


    protected boolean flashing;
    protected int flashingCount;

    /**
     * Constructs ghost
     * @param i x grid position of spawn
     * @param j y grid position of spawn
     * @param board the board to spawn on
     * @param pacMan the pac man in the board
     */
    public Ghost(double i, double j, Board board, PacMan pacMan) {
        super(i, j, board);
        this.pacman = pacMan;

        sprite = new Triangle((int)(i * board.getCellSize()), (int)(j * board.getCellSize()), board.getCellSize(), 0);

        // Setting up movement speed for each level
        movementSpeeds.add(new GhostMovementSpeed(1, .75, .50, .4));
        for (int k = 2; k < 5; k++) {
            movementSpeeds.add(new GhostMovementSpeed(k, .85, .55, .45));
        }
        for (int k = 5; k < 256; k++) {
            movementSpeeds.add(new GhostMovementSpeed(k, .95, .60, .5));
        }
        movementSpeed = maxMovementSpeed * movementSpeeds.get(board.getLevel()).getNormal();

    }

    public void update(){
        super.update();

        updateCurrentTile();
        updateNextTile();
        updateTargetTile();



        // Reverse direction if needs to
        if (!hasReversed){
            // Making sure ghosts has gone to the next tile before reversing direction
            if (!previousTile.equals(currentTile) && !previousSecondTile.equals(currentTile)){
                desiredDirectionX = directionX * -1;
                desiredDirectionY = directionY * -1;
                hasReversed = true;
            }
        }else {
            // If direction needs to be changed change direction
            int nextDirection = checkDirections();
            switch (nextDirection){
                case 0:
                    setDesiredDirectionY(-1);
                    setDesiredDirectionX(0);
                    break;
                case 1:
                    setDesiredDirectionY(0);
                    setDesiredDirectionX(-1);
                    break;
                case 2:
                    setDesiredDirectionY(1);
                    setDesiredDirectionX(0);
                    break;
                case 3:
                    setDesiredDirectionY(0);
                    setDesiredDirectionX(1);
                    break;
            }
        }




        // Slows down movement in tunnel
        // tunnelMovementSpeed();
        setMovementSpeed();


        // Can leave monster pen
    }

    public void canLeaveMonsterPen(){
        if (dotCounter >= dotLimit && isInMonsterPen){
            leaveMonsterPen();
        }
    }


    /**
     * Updates the current tile of the ghost
     */
    protected void updateCurrentTile(){
        // currentTile = board.getGridPosition(sprite.getPosX(), sprite.getPosY());
        currentTile = board.getGridPosition((int)realPosX, (int)realPosY);
    }

    /**
     * Updates the next tile the ghost is looking at
     * This is because the ghost looks ahead one tile
     */
    protected void updateNextTile(){
        nextTile = getNextPos(desiredDirectionX, desiredDirectionY);
        // Check if looking at next tile which isn't in the grid
        if (nextTile.getX() < 0){
            nextTile.setX(board.getWidth() - Board.getCellSize());
        }else if (nextTile.getX() > board.getWidth() - 1){
            nextTile.setX(0);
        }
        nextTile = board.getGridPosition(nextTile.getX(), nextTile.getY());
    }

    abstract protected void updateTargetTile();

    /**
     * Checks what directions a ghost can go from the next tile
     * Returns directions ghosts can go:
     * @return 0 = up, 1 = left, 2 = down, 3 = right, 4 = don't change direction
     */
    protected int checkDirections(){
        double distances[] = {0,0,0,0};
        double shortestValue = Integer.MAX_VALUE;
        int shortestDirection = 4;


        // If next tile is wall or is same as current tile don't calculate
        if (board.checkGrid(nextTile.getX(), nextTile.getY()) == 1){
            return 4;
        }
        if (currentTile.equals(nextTile)){
            return 4;
        }


        // Checking tile to the right of the next tile
        // Then checking the distance from this tile to the target tile
        Coordinate gridPos = new Coordinate(nextTile.getX() + 1, nextTile.getY());
        // If edge of board use other side
        if (nextTile.getX() > board.getGridWidth() - 2){
            gridPos = new Coordinate(0, nextTile.getY());
        }
        // Make sure ghost isn't reversing and grid pos isn't wall
        Boolean isEmpty = (board.checkGrid(gridPos.getX(), gridPos.getY()) != 1);
        Boolean isReversing = (gridPos.equals(currentTile));
        if (!isReversing && isEmpty){
            distances[3] = gridPos.distance(targetTile);
            if (distances[3] <= shortestValue){
                shortestValue = distances[3];
                shortestDirection = 3;
            }
        }else{
            distances[3] = Integer.MAX_VALUE;
        }


        // Checking tile below the next tile
        // Then checking the distance from this tile to the target tile
        gridPos = new Coordinate(nextTile.getX(), nextTile.getY() + 1);
        // Make sure ghost isn't reversing and grid pos isn't wall
        isEmpty = (board.checkGrid(gridPos.getX(), gridPos.getY()) != 1);
        isReversing = (gridPos.equals(currentTile));
        if (!isReversing && isEmpty){
            distances[2] = gridPos.distance(targetTile);
            if (distances[2] <= shortestValue){
                shortestValue = distances[2];
                shortestDirection = 2;
            }
        }else{
            distances[2] = Integer.MAX_VALUE;
        }


        // Checking tile to the left of the next tile
        // Then checking the distance from this tile to the target tile
        gridPos = new Coordinate(nextTile.getX() - 1, nextTile.getY());
        // If edge of board use other side
        if (nextTile.getX() <= 0){
            gridPos = new Coordinate(board.getGridWidth() - 1, nextTile.getY());
        }
        // Make sure ghost isn't reversing and grid pos isn't wall
        isEmpty = (board.checkGrid(gridPos.getX(), gridPos.getY()) != 1);
        isReversing = (gridPos.equals(currentTile));
        if (!isReversing && isEmpty){
            distances[1] = gridPos.distance(targetTile);
            if (distances[1] <= shortestValue){
                shortestValue = distances[1];
                shortestDirection = 1;
            }
        }else{
            distances[1] = Integer.MAX_VALUE;
        }


        // Checking tile above the next tile
        // Then checking the distance from this tile to the target tile
        gridPos = new Coordinate(nextTile.getX(), nextTile.getY() - 1);
        // Make sure ghost isn't reversing and grid pos isn't wall
        isEmpty = (board.checkGrid(gridPos.getX(), gridPos.getY()) != 1);
        isReversing = (gridPos.equals(currentTile));
        if (!isReversing && isEmpty){
            // Making sure ghost can turn upwards or is frightened
            if (canTurnUpwards() || state == 2){
                distances[0] = gridPos.distance(targetTile);
                if (distances[0] <= shortestValue){
                    shortestValue = distances[0];
                    shortestDirection = 0;
                }
            }
        }else{
            distances[0] = Integer.MAX_VALUE;
        }


        return shortestDirection;
    }

    /**
     * Changes state if it's different
     * @param state next state
     */
    public void changeState(int state){
        if (this.state != state){
            reverseDirection();
            this.state = state;
            // Changing movement speed depending on state
            switch (state){
                case 0:
                    setMovementSpeed();
                    break;
                case 1:
                    setMovementSpeed();
                    break;
                case 2:
                    setMovementSpeed();
                    break;
            }
        }
    }

    /**
     * Sets movement speed depending on state
     */
    protected void setMovementSpeed(){
        // Changes speed depending on state
        switch (state){
            case 0:
                movementSpeed = maxMovementSpeed * movementSpeeds.get(board.getLevel()).getNormal();
                break;
            case 1:
                movementSpeed = maxMovementSpeed * movementSpeeds.get(board.getLevel()).getNormal();
                break;
            case 2:
                movementSpeed = maxMovementSpeed * movementSpeeds.get(board.getLevel()).getFrightened();
                break;
        }

        // If in tunnel reduce movement speed
        if (Board.tunnelCoordinates.contains(getCurrentTile())){
            movementSpeed = maxMovementSpeed * movementSpeeds.get(board.getLevel()).getTunnel();
        }
    }

    /**
     * Reverse direction ghost is traveling in
     */
    Boolean hasReversed = true;
    Coordinate previousTile;
    Coordinate previousSecondTile;
    public void reverseDirection(){
        // Makes ghost reverse direction after passes next tile
        hasReversed = false;
        previousTile = nextTile;
        previousSecondTile = currentTile;
    }

    /**
     * Changes movement speed if ghost is in tunnel
     */
    private void tunnelMovementSpeed(){
        if (Board.tunnelCoordinates.contains(getCurrentTile())){
            movementSpeed = maxMovementSpeed * movementSpeeds.get(board.getLevel()).getTunnel();
        }else{
            setMovementSpeed();
        }

    }

    public void enterMonsterPen(){
        realPosX = 13.5 * Board.getCellSize();
        realPosY = 13 * Board.getCellSize();
        updateCurrentTile();
        updateSprite();

        isInMonsterPen = true;
        // update();
    }

    public void leaveMonsterPen(){
        realPosX = 13.5 * Board.getCellSize();
        realPosY = 11 * Board.getCellSize();
        updateSprite();

        directionX = -1;
        directionY = 0;
        updateRotation();
        // updateNextTile();
        // updateTargetTile();
        // updateCurrentTile();
        isInMonsterPen = false;


        // After leaving monster pen ghost shouldn't be frightened and should wait 2 seconds before leaving
        if (state == 2){
            int delay = 000;
            Timer waitingTime = new Timer();
            waitingTime.schedule(new TimerTask() {
                @Override
                public void run() {
                    changeState(0);

                }
            }, delay);

        }

    }

    /**
     * Ghosts is eaten teleport into monsterpen and release after 3 seconds
     */
    public void eaten(){
        realPosX = 13.5 * Board.getCellSize();
        realPosY = 13 * Board.getCellSize();
        updateSprite();
        int delay = 3000;
        Timer waitingTime = new Timer();
        waitingTime.schedule(new TimerTask() {
            @Override
            public void run() {
                leaveMonsterPen();

            }
        }, delay);
    }

    private boolean canTurnUpwards(){
        if (Board.noUpTurnCoordinates.contains(currentTile)){
            return false;
        }else {
            return true;
        }
    }


    @Override
    public void paintSprite(Graphics g) {
        g.setColor(ghostColour);
        if (state == 2){
            g.setColor(new Color(100,34,234));
            // Flashes ghost white
            if (flashing){
                if (flashingCount % 60 < 30){
                    g.setColor(Color.white);
                }
                ++flashingCount;
            }
        }

        super.paintSprite(g);

        // DEBUG TESTING
        // if (targetTile != null){
        //     g.fillRect(targetTile.getX() * Board.getCellSize(), targetTile.getY() * Board.getCellSize(), 10,10);
//
        // }
    }

    // Getters and setters methods
    public void setTargetTile(Coordinate targetTile) {
        this.targetTile = targetTile;
    }

    public Coordinate getTargetTile() {
        return targetTile;
    }

    public Coordinate getCurrentTile() {
        return currentTile;
    }

    public int getState() {
        return state;
    }

    public int getDotCounter() {
        return dotCounter;
    }

    public void setDotCounter(int dotCounter) {
        this.dotCounter = dotCounter;
    }

    public int getDotLimit() {
        return dotLimit;
    }

    public boolean isInMonsterPen() {
        return isInMonsterPen;
    }

    public void setInMonsterPen(boolean inMonsterPen) {
        isInMonsterPen = inMonsterPen;
    }

    public void setFlashing(boolean flashing) {
        this.flashing = flashing;
    }
}
