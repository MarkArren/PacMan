package Utilities;

import Panels.Board;

import java.util.Timer;
import java.util.TimerTask;

public class ScatterAndChaseTimer implements Runnable {
    private Board board;
    private int currentTimerIndex = 0;

    public ScatterAndChaseTimer(Board board){
        this.board = board;
    }

    @Override
    public void run() {
        if (currentTimerIndex != 7){
            int delay = board.getScatterChaseTimes().get(board.getLevel()).getState(currentTimerIndex);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentTimerIndex % 2 == 0){
                        board.changeGhostState(0);
                    }else {
                        board.changeGhostState(1);
                    }
                    ++currentTimerIndex;
                    // board.startScatterTimer();
                    // this.cancel();
                    // this.run();
                    // board.startChaseTimer();
                }
            }, delay);
            System.out.println("Timer: " + currentTimerIndex);
            System.out.println("delay: " + delay);


        }
    }






    public void pause(long milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }

    }
}
