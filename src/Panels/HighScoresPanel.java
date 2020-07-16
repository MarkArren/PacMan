package Panels;

import DataTypes.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HighScoresPanel extends JPanel {
    private ArrayList<Score> scores;
    private int titleHeight = 100;
    private int columnNameHeight = 140;
    private int scoreHeight = 180;
    private String[] ranks = {"1ST" , "2ND" , "3RD" , "4 TH" , "5 TH" , "6 TH" , "7 TH" , "8 TH" , "9 TH" , "10 TH"};

    private Color[] colours = {Color.orange, Color.red, Color.pink, Color.cyan};

    public HighScoresPanel(int width, int height, ArrayList<Score> scores){
        super();
        this.scores = scores;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Sets up font with character spacing of 0.1
        g.setFont(new Font("Calibri" , Font.BOLD, 22));
        Map<TextAttribute, Object> attribute = new HashMap<>();
        attribute.put(TextAttribute.TRACKING, 0.1);
        g.setFont(g.getFont().deriveFont(attribute));
        FontMetrics fontMetrics = g.getFontMetrics();

        // Draws title and column names
        g.setColor(Color.WHITE);
        String text = "THE TOP 10 HIGH SCORES";
        int textWidth = fontMetrics.stringWidth(text);
        g.drawString(text, (getWidth() / 2) - (textWidth / 2) , titleHeight);

        g.setColor(colours[0]);
        text = "RANK";
        textWidth = fontMetrics.stringWidth(text);
        g.drawString(text, (getWidth() / 6) - (textWidth / 2) , columnNameHeight);

        text = "SCORE";
        textWidth = fontMetrics.stringWidth(text);
        g.drawString(text, ((getWidth() / 6) * 3) - (textWidth / 2) , columnNameHeight);

        text = "INITIAL";
        textWidth = fontMetrics.stringWidth(text);
        g.drawString(text, ((getWidth() / 6) * 5) - (textWidth / 2), columnNameHeight);


        // Draws each name on score on screen
        for (int i = 0; i < scores.size(); i++) {

            g.setColor(colours[(i+1) % 4]);

            // Draw rank
            text = ranks[i];
            textWidth = fontMetrics.stringWidth(text);
            g.drawString(text, (getWidth() / 6) - (textWidth / 2), i * 40 + scoreHeight);


            // Draw Score
            Integer scoreInt = scores.get(i).getScore();
            g.drawString(Integer.toString(scoreInt), getWidth() / 2, i * 40 + scoreHeight);

            // Name
            text = scores.get(i).getName().toUpperCase();
            textWidth = fontMetrics.stringWidth(text);
            g.drawString(text, ((getWidth() / 6) * 5) - (textWidth / 2), i * 40 + scoreHeight);

            }
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }
}
