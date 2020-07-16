package Panels;

import javax.swing.*;
import java.awt.*;

public class CurrentScorePanel extends JPanel {
    Board board;
    int highScore;
    int currentScore;

    public CurrentScorePanel(Board board, MainFrame frame){
        this.board = board;

        setFont(frame.getFont());
        setPreferredSize(new Dimension(board.getWidth(), (int)(board.getCellSize() * 2.5)));
        setBackground(board.backgroundColour);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        // Setting font colour
        g.setColor(Color.white);
        FontMetrics fontMetrics = g.getFontMetrics();


        // Draws titles
        g.drawString("1UP", getWidth() / 6, fontMetrics.getHeight() - 10);
        g.drawString("HIGH SCORE", getWidth() / 2 - (fontMetrics.stringWidth("HIGH SCORE") / 2), fontMetrics.getHeight() / 2 + 3);


        // Draws current score and high score
        currentScore = board.getScore();
        highScore = board.getHighScore();
        g.drawString(Integer.toString(currentScore), getWidth() / 5, fontMetrics.getHeight() * 2 - 20);
        g.drawString(Integer.toString(highScore), getWidth() / 2, fontMetrics.getHeight() * 2 - 20);

    }

    // Getter and setter methods
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }
}
