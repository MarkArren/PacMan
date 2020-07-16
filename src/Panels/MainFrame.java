package Panels;

import DataTypes.Score;
import Utilities.HighScoreFile;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {
    private JLayeredPane centerPanel = new JLayeredPane();
    private HighScoresPanel highScoresPanel;
    private EnterNamePanel enterNamePanel;
    private Board board;
    private CurrentScorePanel currentScorePanel;
    private LivesPanel livesPanel;
    private int lowestHighScore;
    private boolean isHighScoreListFull;

    private Color fontColour;

    public static void main(String[] args) {
        // Starts program
        new MainFrame("1703572");
    }

    public MainFrame(String title) {
        super(title);

        // Sets up font with character spacing of 0.1
        setFont(new Font("Calibri" , Font.BOLD, 22));
        Map<TextAttribute, Object> attribute = new HashMap<>();
        attribute.put(TextAttribute.TRACKING, 0.1);
        setFont(getFont().deriveFont(attribute));
        fontColour = Color.white;



        // Creates board and adds it to layered pane
        // Add layered pane to frame
        board = new Board();
        board.requestFocus();

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(board, BorderLayout.CENTER,-1);
        getContentPane().add(BorderLayout.CENTER, centerPanel);

        // Adds current score panel to the top of the screen
        currentScorePanel = new CurrentScorePanel(board, this);
        getContentPane().add(BorderLayout.NORTH, currentScorePanel);

        // Adds current lives panel to the bottom of the screen
        livesPanel = new LivesPanel(board);
        getContentPane().add(BorderLayout.SOUTH, livesPanel);



        // Frame attributes etc
        setResizable(false);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        repaint();
        // setPreferredSize(new Dimension(board.getHeight() - 20, board.getHeight()));

        // Gets highest and lowest high score
        lowestHighScore = HighScoreFile.getLowestScore();
        board.setHighScore(HighScoreFile.getHighestScore());
        isHighScoreListFull = HighScoreFile.isFileFull();


        startGame();
    }

    /**
     * Starts game after delay of 3 seconds
     */
    public void startGame(){
        int delay = 3000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startTimer();
                board.setGameStarted(true);
            }
        }, delay);
    }

    /**
     * Tick event which occurs every x milli seconds
     */
    public void startTimer(){
        long time_interval = 7; // Time in milliseconds
        java.util.Timer timer = new Timer();

        TimerTask task = new TimerTask(){
            // Every update
            public void run(){
                if (!board.getGameEnded()){
                    board.update();
                }else {
                    timer.cancel();
                    endGame();
                }

                currentScorePanel.repaint();
                livesPanel.repaint();
                repaint();
            }
        };
        timer.scheduleAtFixedRate(task, 0, time_interval);

    }

    /**
     * Ends game asking for user name and storing scores in file
     */
    private void endGame(){
        // Checks if user set new high score to prompt player for name
        if (board.getScore() > lowestHighScore || !isHighScoreListFull){
            // Adds name input panel on top of the board
            enterNamePanel = new EnterNamePanel(board.getWidth(), board.getHeight(),this);
            centerPanel.add(enterNamePanel, BorderLayout.CENTER, 1);
            enterNamePanel.requestFocus();
            centerPanel.moveToFront(enterNamePanel);
            centerPanel.revalidate();

        }else {
            displayHighScorePanel();
        }

        System.out.println("Game Over");
    }


    /**
     * Adds high score to the file and then displays high scores panel
     * @param playerName
     */
    public void addHighScore(String playerName){
        HighScoreFile.addScore(new Score(playerName, board.getScore()));
        displayHighScorePanel();
    }

    /**
     * Adds the high score panel to the top of the center panel
     */
    public void displayHighScorePanel(){
        // Adds high score panel on top of the board
        highScoresPanel = new HighScoresPanel(board.getWidth(), board.getHeight(), HighScoreFile.getScores());
        centerPanel.add(highScoresPanel, BorderLayout.CENTER, 2);
        highScoresPanel.requestFocus();
        centerPanel.moveToFront(highScoresPanel);
        centerPanel.revalidate();
    }

    // Setter and getter methods
    public Color getFontColour() {
        return fontColour;
    }

    public void setFontColour(Color fontColour) {
        this.fontColour = fontColour;
    }
}

