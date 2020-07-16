package Panels;


import Characters.*;

import Consumables.*;
import DataTypes.Coordinate;
import DataTypes.ScatterChaseTime;
import Utilities.ScatterAndChaseTimer;
import Utilities.TimerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;


public class Board extends JPanel implements KeyListener {
    // Grid showing where to spawn walls,pac dots and power pellets
    // 0 = empty, 1 = wall, 2 = pac dot, 3 = power pellet
    // [column][row] = [x][y] = lengths [28][32] = index's[27][31]
    private static final int grid[][] = {
            // Left - Right
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,2,3,2,2,2,2,2,1,1,1,1,1,0,1,1,1,1,1,2,2,2,3,1,1,2,2,2,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,2,2,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,1,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1},
            {1,2,1,1,1,2,1,1,1,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1},
            {1,2,1,1,1,2,2,2,2,1,1,0,0,0,0,0,0,0,0,0,2,1,1,2,2,2,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,0,1,1,1,1,1,0,1,1,2,1,1,2,1,1,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,0,1,0,0,0,1,0,1,1,2,1,1,2,1,1,2,1,1,2,1},
            {1,2,2,2,2,2,1,1,2,0,0,0,1,0,0,0,1,0,1,1,2,2,2,2,1,1,2,2,2,2,1},
            {1,1,1,1,1,2,1,1,1,1,1,0,1,0,0,0,1,0,1,1,1,1,1,0,1,1,1,1,1,2,1},
            {1,1,1,1,1,2,1,1,1,1,1,0,1,0,0,0,1,0,1,1,1,1,1,0,1,1,1,1,1,2,1},
            {1,2,2,2,2,2,1,1,2,0,0,0,1,0,0,0,1,0,1,1,2,2,2,2,1,1,2,2,2,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,0,1,0,0,0,1,0,1,1,2,1,1,2,1,1,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,0,1,1,1,1,1,0,1,1,2,1,1,2,1,1,2,1,1,2,1},
            {1,2,1,1,1,2,2,2,2,1,1,0,0,0,0,0,0,0,0,0,2,1,1,2,2,2,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,1,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1},
            {1,2,1,1,1,2,1,1,1,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1},
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,2,2,2,1,1,2,1},
            {1,2,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,2,1,1,2,1},
            {1,2,2,3,2,2,2,2,2,1,1,1,1,1,0,1,1,1,1,1,2,2,2,3,1,1,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
    private static final int gridWidth = grid.length;
    private static final int gridHeight = grid[0].length;
    private static final int CELL_SIZE = 20;

    // Game states
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean gamePaused = false;

    // Game values
    private int highScore;
    private int score;
    private int level = 1;
    private int lives = 3;

    // Characters
    private PacMan pacMan;
    private Vector<Ghost> ghosts = new Vector<>(4); // Stores all the ghosts
    private Boolean canGhostsMove = true;
    private int ghostsEatenCounter; // How many ghosts have been eaten in frightened stage

    // Consumables
    private ConcurrentHashMap<Integer, Consumables> consumables = new ConcurrentHashMap(); // Stores every consumable item in the map
    private Consumables fruit;

    // Consumable counters
    private boolean usingGlobalCounter; // If using global dot counter or local dot counter for ghosts
    private int globalDotCounter;
    private Timer dotEatenTimer = new Timer();
    private Boolean dotEatenTimerRunning = false;

    // Look of game
    public static final Color backgroundColour = Color.black;
    public static final Color wallColour = new Color(44,53,216);

    // Timer for ghosts being frightened
    private Timer frightenedTimer = new Timer();
    private Boolean frightenedTimerRunning = false;
    private final int[] frightenedBlueTimes = {4000, 3000, 2000, 1000, 0, 3000, 0, 0, 0, 3000, 0, 0, 0, 1000, 0, 0, 0, 0};
    private final int[] frightenedFlashingTimes = {4000, 4000, 4000, 4000, 4000, 4000, 4000, 4000, 2000, 4000, 4000, 2000, 2000, 4000, 2000, 2000, 0, 2000};

    // Creating new thread containing the timers
    private ScatterAndChaseTimer scatterAndChaseTimer = new ScatterAndChaseTimer(this);
    private final Thread timerThread = new Thread(scatterAndChaseTimer);

    // Coordinates of tunnels
    public static final List<Coordinate> tunnelCoordinates = Arrays.asList(
            new Coordinate(0, 14),
            new Coordinate(1, 14),
            new Coordinate(2, 14),
            new Coordinate(3, 14),
            new Coordinate(4, 14),
            new Coordinate(5, 14),
            new Coordinate(22, 14),
            new Coordinate(23, 14),
            new Coordinate(24, 14),
            new Coordinate(25, 14),
            new Coordinate(26, 14),
            new Coordinate(27, 14)
    );
    // Coordinates where ghosts can't turn upwards
    public static final List<Coordinate> noUpTurnCoordinates = Arrays.asList(
            new Coordinate(10, 23),
            new Coordinate(11, 23),
            new Coordinate(12, 23),
            new Coordinate(13, 23),
            new Coordinate(14, 23),
            new Coordinate(15, 23),
            new Coordinate(16, 23),
            new Coordinate(17, 23),
            new Coordinate(10, 11),
            new Coordinate(11, 11),
            new Coordinate(12, 11),
            new Coordinate(13, 11),
            new Coordinate(14, 11),
            new Coordinate(15, 11),
            new Coordinate(16, 11),
            new Coordinate(17, 11)
    );

    // Stores the scatter and chase time in milliseconds for each round
    protected ArrayList<ScatterChaseTime> scatterChaseTimes = new ArrayList<>(256);



    /**
     * Initialises board
     */
    public Board(){
        setPreferredSize(new Dimension(gridWidth * CELL_SIZE, gridHeight * CELL_SIZE));

        initialiseObjects();

        // Setting up scatter and chase times speed for each level
        scatterChaseTimes.add(new ScatterChaseTime(7000, 20000, 7000, 20000, 5000, 20000, 5000 , Integer.MAX_VALUE));
        for (int k = 2; k < 5; k++) {
            scatterChaseTimes.add(new ScatterChaseTime(7000, 20000, 7000, 20000, 5000, 1033000, 17 , Integer.MAX_VALUE));
        }
        for (int k = 5; k < 256; k++) {
            scatterChaseTimes.add(new ScatterChaseTime(5000, 20000, 5000, 20000, 5000, 1037000, 17 , Integer.MAX_VALUE));
        }



        addKeyListener(this);
        setFocusable(true);
    }

    /**
     * Tick event for the board
     */
    public void update(){
        if (!gamePaused){
            // Update all characters
            pacMan.update();

            if (canGhostsMove){
                for (Ghost ghost:ghosts) {
                    ghost.update();
                }
            }

            // Check if need to spawn fruit
            shouldInitialiseFruit();

            // Check for overlapping ghost
            checkGhostOverlappingPacMan();

            // Check for finish
            checkFinish();
        }
        repaint();
    }


    /**
     * Spawns pac man, pac dots & power pellets
     */
    private void initialiseObjects(){
        // Looping through grid to see where to initialise pac dots and power pellets
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // See which one to spawn
                switch (grid[i][j]){
                    case 2:
                        // Initialise pac dots
                        PacDot pacDot = new PacDot(i, j, CELL_SIZE);
                        consumables.put(grid[0].length * i + j, pacDot);
                        break;
                    case 3:
                        // Initialise power pellet
                        PowerPellet powerPellet = new PowerPellet(i, j, CELL_SIZE);
                        consumables.put(grid[0].length * i + j, powerPellet);
                        break;
                }
            }
        }

        // Initialise pac man and ghosts
        pacMan = new PacMan(13.5, 23, this);

        Blinky blinky = new Blinky(13.5,11,this, pacMan);
        ghosts.add(blinky);
        ghosts.add(new Pinky(11.5,13,this, pacMan));
        ghosts.add(new Inky(13.5, 13, this, pacMan, (Blinky) ghosts.get(0)));
        ghosts.add(new Clyde(15.5, 13, this, pacMan));

        // Adding each ghost to monster pen
        ghosts.get(1).enterMonsterPen();
        ghosts.get(2).enterMonsterPen();
        ghosts.get(3).enterMonsterPen();
    }

    /**
     * Check if should spawn fruit
     */
    private void shouldInitialiseFruit(){
        if (consumables.size() == 174 || consumables.size() == 74){
            initialiseFruit();
        }
    }

    /**
     * Spawn fruit depending on what level it is
     */
    private void initialiseFruit(){
        double x = 13.5;
        double y = 17;
        switch (level){
            case 1:
                fruit = new Cherry(x, y, CELL_SIZE);
        }

        // After spawning fruit delete fruit after time limit
        Random r = new Random();
        int delay = r.nextInt(10000 - 9300) + 9300;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fruit = null;
            }
        }, delay);
    }



    /**
     * Checks if pac man is overlapping any of the ghosts
     */
    private void checkGhostOverlappingPacMan(){
        // Loop through ghosts checking if any ghosts are in the same position as pac man
        for (Ghost ghost:ghosts) {
            if (ghost.getCurrentTile().equals(pacMan.getBoardPosition()) && canGhostsMove){
                // If they're frightened pacman will eat them
                if (ghost.getState() == 2){
                    ghost.eaten();
                    ghost.update();
                    ++ghostsEatenCounter;
                    score += 200 * Math.pow(2, ghostsEatenCounter - 1);
                    // If they're not pacman will die
                }else{
                    if (lives == 0){
                        endGame();
                        break;
                    }else{
                        pacMan.die();
                        resetGhosts();
                        lives -= 1;
                        usingGlobalCounter = true;
                        globalDotCounter = 0;
                    }

                }
            }
        }
    }

    /**
     * Resets ghosts if pac man dies
     */
    private void resetGhosts(){
        for (Ghost ghost:ghosts) {
            if (ghost instanceof Blinky){
                ghost.leaveMonsterPen();
            }else {
                ghost.enterMonsterPen();
            }
            // ghost.update();
        }
        canGhostsMove = false;

        // Start timer so ghosts can't move until pacman finishes dying
        int delay = 2000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                canGhostsMove = true;
                pacMan.respawn();
            }
        }, delay);
    }

    /**
     * Check if level is finished
     */
    private void checkFinish(){
        // If everything collected reset board to next level
        if (consumables.size() == 0){
            // resetBoard();
            ghosts.clear();
            initialiseObjects();
            ++level;


            // Pauses game to get ready for next round
            gamePaused = true;
            int delay = 3000;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gamePaused = false;
                }
            }, delay);

        }
    }

    /**
     * Ends game, clearing all ghosts
     */
    private void endGame(){
        // Delete all ghosts
        ghosts.clear();
        gameEnded = true;
    }

    private int currentTimerIndex = 0;
    /**
     * Makes ghost switch between scatter and chase mode after x seconds
     */
    private void startScatterTimer(){
        if (currentTimerIndex != 7){
            int delay = scatterChaseTimes.get(level).getState(currentTimerIndex);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentTimerIndex % 2 == 0){
                        changeGhostState(0);
                    }else {
                        changeGhostState(1);
                    }
                    ++currentTimerIndex;
                    startScatterTimer();
                }
            }, delay);

        }

    }
    private void pauseScatterTimer(){

    }

    TimerThread timerThread2 = new TimerThread(this);
    public void startChaseTimer(){
        // timerThread = new Thread(scatterAndChaseTimer);
        //timerThread.start();
        //scatterAndChaseTimer.run();
        //timerThread2.start();
    }

    private void pauseChaseTimer(){
        // TODO pause timer after going into frightened then resume it after ending frightened
        // scatterAndChaseTimer.pause(10000);
        // try{
        //     synchronized (timerThread){
        //         timerThread.wait();
        //     }
        //     System.out.println(timerThread);
//
        //     System.out.println("pausing timer thread");
        // }catch (Exception e){
        //     System.out.println(e.getMessage());
        // }
        // scatterAndChaseTimer.pause(1000);
        // timerThread.interrupt();
        //timerThread2.pause();

    }

    private void resumeChaseTimer(){
        // timerThread.
        //notifyAll();
        //timerThread2.resume2();
    }




    /**
     * Draws each cell, consumables and characters
     * @param g Graphics component
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        // Draw each cell in grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // Draws up and down then left to right
                if (grid[i][j] == 1){ // wall
                    g.setColor(wallColour);
                }else{
                    g.setColor(backgroundColour); // empty
                }
                g.fill3DRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE, true);
            }
        }

        // Draws each consumable in the map
        for (Consumables consumable: consumables.values()){
            consumable.paintSprite(g);
        }


        // Draws each character
        g.setColor(Color.ORANGE);
        pacMan.paintSprite(g);
        for (Ghost ghost:ghosts) {
            ghost.paintSprite(g);
        }

        // Draws fruit if in level
        if (fruit != null){
            fruit.paintSprite(g);
        }

        // Draws starting text
        if (!gameStarted){
            g.setFont(new Font("Calibri", Font.PLAIN, 22));
            FontMetrics fontMetrics = g.getFontMetrics();

            g.setColor(Color.yellow);
            int width = fontMetrics.stringWidth("READY!");
            g.drawString("READY!", getWidth() / 2 - (width / 2), (int)(17.8 * CELL_SIZE));

            g.setColor(Color.cyan);
            width = fontMetrics.stringWidth("PLAYER ONE");
            g.drawString("PLAYER ONE", getWidth() / 2 - (width / 2), (int)(11.8 * CELL_SIZE));
        }

        // Draws the current level
        if (gamePaused){
            g.setFont(new Font("Calibri", Font.PLAIN, 22));
            FontMetrics fontMetrics = g.getFontMetrics();

            g.setColor(Color.yellow);
            int width = fontMetrics.stringWidth("LEVEL: " + level);
            g.drawString("LEVEL: " + level, getWidth() / 2 - (width / 2), (int)(17.8 * CELL_SIZE));
        }
    }





    public int checkGrid(int i, int j){
        return grid[i][j];
    }

    /**
     * Returns x coordinate of far right square
     * @return int x
     */
    public int getEdgeOfBoard(){
        return grid.length * CELL_SIZE;
    }

    /**
     * Deletes consumable at grid positions
     * @param i column
     * @param j row
     */
    public void destroyConsumable(int i, int j){
        Consumables deletedConsumable = consumables.remove(grid[0].length * i + j);

        // Adding points to score if eaten consumable
        if (deletedConsumable != null){
            score += deletedConsumable.getPoints();

            // If pac man avoids eating dots release a ghost
            // if (dotEatenTimerRunning){
            //     dotEatenTimer.cancel();
            //     dotEatenTimer = new Timer();
            // }
            // int delay = 4000;
            // if (level >= 5){
            //     delay = 3000;
            // }
            // dotEatenTimer.schedule(new TimerTask() {
            //     @Override
            //     public void run() {
            //         releaseGhost();
            //     }
            // }, delay);

            // If power pellet change state of all ghosts
            if (deletedConsumable instanceof PowerPellet){
                changeGhostState(2);
            }



            // Increase dot counter depending if using global or local dot counter
            if (!usingGlobalCounter){
                // Increase dot counter for the preferred ghost
                for (Ghost ghost:ghosts) {
                    if (ghost.isInMonsterPen()){
                        if (ghost.getDotCounter() >= ghost.getDotLimit()){
                            ghost.leaveMonsterPen();
                        }else {
                            ghost.setDotCounter(ghost.getDotCounter() + 1);
                        }
                        break;
                    }
                }
            }else {
                // Ghosts exit monster pen after certain amount of dots
                if (globalDotCounter == 7){
                    ghosts.get(1).leaveMonsterPen();
                }else if (globalDotCounter == 17){
                    ghosts.get(2).leaveMonsterPen();
                }else if (globalDotCounter == 32 && ghosts.get(3).isInMonsterPen()){
                    ghosts.get(3).leaveMonsterPen();
                    usingGlobalCounter = false;
                    globalDotCounter = 0;
                }
                    ++globalDotCounter;
            }
        }

        // Checking if eating fruit
        if (i == 13 && j == 17 && fruit != null){
            score += fruit.getPoints();
            fruit = null;
        }

        // Check if high score has been beaten
        if (score > highScore){
            highScore = score;
        }
    }



    /**
     * Changes ghost state
     * @param state State of ghost that you want to change to
     */
    public void changeGhostState(int state){
        for (Ghost ghost:ghosts) {
            ghost.changeState(state);
        }
        // If ghosts change to frightened state change back to regular state after x seconds
        if (state == 2){
            // Change back to chase state after x seconds

            // Stacks power pellets by canceling the timer and creating a new one
            if (frightenedTimerRunning){
                frightenedTimer.cancel();
                frightenedTimer = new Timer();
                for (Ghost ghost:ghosts) {
                    ghost.setFlashing(false);
                }
            }

            // TODO move to ghost class
            // Ghosts enter frightened mode
            ghostsEatenCounter = 0;
            // Duration of frightened mode depends on level
            int blueTime = 0;
            int flashingTime = 0;
            if (level < frightenedBlueTimes.length){
                blueTime = frightenedBlueTimes[level];
                flashingTime = frightenedFlashingTimes[level];
            }
            // Makes ghost flash after x seconds
            frightenedTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Ghost ghost:ghosts) {
                        ghost.setFlashing(true);
                    }

                }
            }, blueTime);
            // Changes state after ghosts finish flashing
            frightenedTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Ghost ghost:ghosts) {
                        ghost.changeState(0);
                        ghost.setFlashing(false);
                        frightenedTimerRunning = false;
                    }
                }
            }, blueTime + flashingTime);
            frightenedTimerRunning = true;

            pauseChaseTimer();

        }

    }

    /**
     * Releases proffered ghost from monster pen
     */
    private void releaseGhost(){
        for (Ghost ghost:ghosts) {
            if (ghost.isInMonsterPen()){
                ghost.leaveMonsterPen();
                break;
            }
        }
    }

    /**
     * Gets the grid index position using the x and y coordinates
     * @param x coordinate
     * @param y coordinate
     * @return position in grid
     */
    public Coordinate getGridPosition(int x, int y){
        int gridX = (int)Math.floor((double)x / (double) CELL_SIZE);
        int gridY = (int)Math.floor((double)y / CELL_SIZE);

        // Use mod so if greater than grid it goes back to start index
        gridX = gridX % (gridWidth);
        gridY = gridY % (gridHeight);

        return (new Coordinate(gridX, gridY));
    }

    // Key inputs
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT: // Left pressed
                pacMan.setDesiredDirectionX(-1);
                pacMan.setDesiredDirectionY(0);
                break;
            case KeyEvent.VK_UP: // Up pressed
                pacMan.setDesiredDirectionX(0);
                pacMan.setDesiredDirectionY(-1);

                break;
            case KeyEvent.VK_RIGHT: // Right pressed
                pacMan.setDesiredDirectionX(1);
                pacMan.setDesiredDirectionY(0);
                break;
            case KeyEvent.VK_DOWN: // Down pressed
                pacMan.setDesiredDirectionX(0);
                pacMan.setDesiredDirectionY(1);
                break;

        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // Getters and setter methods
    public static int getCellSize() {
        return CELL_SIZE;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public ConcurrentHashMap<Integer, Consumables> getConsumables() {
        return consumables;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
        if (gameStarted){
            startChaseTimer();
        }
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public boolean getGameEnded(){
        return gameEnded;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getLevel() {
        return level;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public ArrayList<ScatterChaseTime> getScatterChaseTimes() {
        return scatterChaseTimes;
    }
}
