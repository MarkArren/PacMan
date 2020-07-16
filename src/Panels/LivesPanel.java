package Panels;

import Consumables.Consumables;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import Consumables.*;


public class LivesPanel extends JPanel {
    private Board board;
    private List<Consumables> fruit = Arrays.asList(
            new Cherry(0, 0, Board.getCellSize()),
            new Strawberry(0, 0, Board.getCellSize()),
            new Orange(0, 0, Board.getCellSize()),
            new Orange(0, 0, Board.getCellSize()),
            new Apple(0, 0, Board.getCellSize()),
            new Apple(0, 0, Board.getCellSize()),
            new Melon(0, 0, Board.getCellSize()),
            new Melon(0, 0, Board.getCellSize()),
            new GalaxianBoss(0, 0, Board.getCellSize()),
            new GalaxianBoss(0, 0, Board.getCellSize()),
            new Bell(0, 0, Board.getCellSize()),
            new Bell(0, 0, Board.getCellSize()),
            new Key(0, 0, Board.getCellSize()),
            new Key(0, 0, Board.getCellSize()),
            new Key(0, 0, Board.getCellSize()),
            new Key(0, 0, Board.getCellSize()),
            new Key(0, 0, Board.getCellSize()),
            new Key(0, 0, Board.getCellSize()),
            new Key(0, 0, Board.getCellSize())
    );


    public LivesPanel(Board board){
        this.board = board;
        setPreferredSize(new Dimension(board.getWidth(), Board.getCellSize()));
        setBackground(Board.backgroundColour);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < board.getLives(); i++){
            g.setColor(Color.ORANGE);
            g.fillArc((int)(i * Board.getCellSize()) + (5 * i) + 40, 0, Board.getCellSize(), Board.getCellSize(), 210, 300);
        }

        if (board.getLevel() < 8){
            for (int i = 0; i < board.getLevel(); i++) {
                fruit.get(i).setGridX(((board.getGridWidth() - 3 - i)));
                fruit.get(i).updateSprite();
                fruit.get(i).paintSprite(g);
            }
        }else if (board.getLevel() < 20){
            int counter = 0;
            for (int i = board.getLevel() - 7; i < board.getLevel(); i++) {
                fruit.get(i).setGridX(((board.getGridWidth() - 3 - counter)));
                fruit.get(i).updateSprite();
                fruit.get(i).paintSprite(g);
                ++counter;
            }
        }else if (19 < board.getLevel()){
            int counter = 0;
            for (int i = 12; i < 19; i++) {
                fruit.get(i).setGridX(((board.getGridWidth() - 3 - counter)));
                fruit.get(i).updateSprite();
                fruit.get(i).paintSprite(g);
                ++counter;
            }
        }
    }
}
