package Utilities;

import Panels.Board;

public class TimerThread extends Thread {
    private ScatterAndChaseTimer scatterAndChaseTimer;

    public TimerThread(Board board) {
        scatterAndChaseTimer = new ScatterAndChaseTimer(board);
    }

    @Override
    public synchronized void start() {
        super.start();
        scatterAndChaseTimer.run();
    }

    public void pause(){
        try{

            // synchronized(this){
            //     wait();
            // }
            scatterAndChaseTimer.wait();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void resume2(){
        notifyAll();
    }
}
