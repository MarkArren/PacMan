package Characters;

import DataTypes.PacManMovementSpeed;
import Panels.Board;
import DataTypes.Coordinate;
import Shapes.CircleSector;

import java.awt.*;
import java.util.ArrayList;

public class PacMan extends Character {
    protected int startAngle = 210;
    protected int arcAngle = 300;

    protected ArrayList<PacManMovementSpeed> movementSpeeds = new ArrayList<>();

    protected boolean isDead = false;





    public PacMan(double i, double j, Board board) {
        super(i, j, board);
        sprite = new CircleSector((int)(i * board.getCellSize()), (int)(j * board.getCellSize()), board.getCellSize() / 2, 210, 300, 0);
        movementSpeed = .85;
        maxTickCounter = 180;


        // Setting up movement speed for each level
        movementSpeeds.add(new PacManMovementSpeed(1, .8, .71, .9, .79));
        for (int k = 2; k < 5; k++) {
            movementSpeeds.add(new PacManMovementSpeed(k, .9, .79, .95, .83));
        }
        for (int k = 5; k < 21; k++) {
            movementSpeeds.add(new PacManMovementSpeed(k, 1, .87, 1, .87));
        }
        for (int k = 21; k < 256; k++) {
            movementSpeeds.add(new PacManMovementSpeed(k, .9, .79, 1, .87));
        }


        movementSpeed = maxMovementSpeed * movementSpeeds.get(board.getLevel()).getNormal();
    }

    @Override
    public void update() {
        // If dead then don't update position
        if (!isDead){
            super.update();
        }else {
            ++tickCounter;
        }

        // Eat any pac dots or power pellets, or ghosts
        eatConsumable();
        playAnimation();
    }

    /**
     * Eats consumable
     */
    private void eatConsumable(){
        Coordinate currentCord = getBoardPosition();

        board.destroyConsumable(currentCord.getX(), currentCord.getY());
    }



    /**
     * Plays death animation and moves pac man to starting position
     */
    public void die(){
        isDead = true;
        isMoving = false;
    }

    public void respawn(){
        realPosX = 13.5 * board.getCellSize();
        realPosY = 23 * board.getCellSize();
        desiredDirectionX = directionX = -1;
        desiredDirectionY = directionY = 0;

        isDead = false;
        isMoving = true;

        updateRotation();
    }


    private void playAnimation(){
        // Play different animation depending on state of pac man
        if (isMoving){
            mouthAnimation();
        }else if (isDead){
            playDeathAnimation();
        }
    }

    /**
     * Animates pac mans mouth
     */
    private void mouthAnimation(){
        int animationLength = 15;

        if (tickCounter % animationLength == 0){
            startAngle = 210;
            arcAngle = 300;
        }if (tickCounter % animationLength == 5){
            startAngle = 225;
            arcAngle = 270;
        }if (tickCounter % animationLength  == 10){
            startAngle = 360;
            arcAngle = 360;
        }else if (tickCounter == 15){
            tickCounter = -1;
        }

        updateSprite();
    }



    /**
     * Plays the death animation
     */
    private void playDeathAnimation(){
        int animationLength = 180;


        if (tickCounter < animationLength){
            deathAnimation();
            // isDead = false;
            // updateRotation();
        }

    }

    /**
     * Death animation increase size of mouth
     */
    private void deathAnimation(){
        startAngle = startAngle + 1;
        arcAngle = arcAngle - 2;
        updateSprite();
    }

    /**
     * Updates sprite arc angle and start angle
     */
    @Override
    protected void updateSprite(){
        super.updateSprite();
        if (sprite instanceof CircleSector){
            CircleSector spriteRef = (CircleSector) sprite;
            spriteRef.setStartAngle(startAngle);
            spriteRef.setArcAngle(arcAngle);
        }else {
            System.out.println("ERROR: Pacmans sprite is not a 'CircleSector'");
        }

    }

    @Override
    public void paintSprite(Graphics g) {
        g.setColor(Color.orange);
        super.paintSprite(g);
    }


}
