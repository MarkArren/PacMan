package Utilities;

import DataTypes.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScoreFile {
    static final String SCORES_TEXT_FILE = "./scores.txt";

    /**
     * Adds score to an array then saves it to file
     * @param scoreToAdd
     */
    public static void addScore(Score scoreToAdd){
        ArrayList<Score> scores = getScores();

        // Checking to see if score to add is higher than any score in list and add it
        for (int i = 0; i < scores.size(); i++) {
            if (scoreToAdd.getScore() >= scores.get(i).getScore()){
                scores.add(i, scoreToAdd);

                // Deleting any scores below top 10
                if (scores.size() > 10){
                    scores.subList(10, scores.size()).clear();
                }

                saveScoreFile(scores);
                break;
            }
        }
        // If nothing in file add score
        if (scores.size() == 0){
            scores.add(scoreToAdd);
            saveScoreFile(scores);
        }
    }

    /**
     * Overwrites score file with score
     * @param scores array of scores
     */
    private static void saveScoreFile(ArrayList<Score> scores){

        try (PrintWriter out = new PrintWriter(new FileWriter(SCORES_TEXT_FILE))){
            for (Score score:scores) {
                out.print(score.getName() + "," + score.getScore() + "\n");
            }
            System.out.println("Written scores to: " + SCORES_TEXT_FILE);
        }catch (IOException e){
            System.out.println("ERROR: Could not write to file");
        }
    }

    /**
     * Gets the scores from a text file and returns them in an array list
     * @return Array[player][score] - highest to lowest
     */
    public static ArrayList<Score> getScores(){
        ArrayList<Score> scores = new ArrayList<>(10);

        try (Scanner scanner = new Scanner(new File(SCORES_TEXT_FILE))){
            // Using a delimiter to separate by comma and new line
            scanner.useDelimiter(",|\n");
            while (scanner.hasNext()){
                Score score = new Score(scanner.next(), 0);
                if (scanner.hasNextInt()){
                    score.setScore(scanner.nextInt());
                    scores.add(score);
                }else{
                    System.out.println("ERROR: No score associated with name");
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Could not find file: " + SCORES_TEXT_FILE);
        }
        return scores;
    }

    /**
     * Gets highest score from the text file
     * @return
     */
    public static int getHighestScore(){
        ArrayList<Score> scores = getScores();
        int highest = Integer.MIN_VALUE;

        // Checking for highest score
        if (scores.size() > 0){
            for (Score score:scores) {
                if (score.getScore() > highest){
                    highest = score.getScore();
                }
            }
            return highest;
        }else{
            // If no high score
            return 0;
        }

    }

    /**
     * Returns the lowest high score
     * @return lowest score
     */
    public static int getLowestScore(){
        ArrayList<Score> scores = getScores();
        int lowest = 0;

        // Gets lowest score from last index
        if (scores.size() > 0){
            lowest = scores.get(scores.size() - 1).getScore();

        }

        return lowest;
    }

    /**
     * Checks if the high score file is full
     * @return true if file is full
     */
    public static boolean isFileFull(){
        ArrayList<Score> scores = getScores();

        if (scores.size() >= 10){
            return true;
        }

        return false;

    }
}
