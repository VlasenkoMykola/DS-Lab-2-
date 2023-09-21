package a;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class main {
    private int[][] forest;
    private final AtomicBoolean BEAR_FOUND;
    private final AtomicInteger CURRENT_ROW;
    private final Integer SIZE_OF_FOREST;
    public final Integer COUNT_OF_THREADS;
    private Thread[] threads;
    private class Bees extends Thread {
        public Bees(){

        }
        public void run() {
            while(!BEAR_FOUND.get() && CURRENT_ROW.get() < SIZE_OF_FOREST) {
                checkRow(CURRENT_ROW.get());
                CURRENT_ROW.set(CURRENT_ROW.get() + 1);
            }
        }
    }
    public main(Integer SIZE_OF_FOREST) {
        this.SIZE_OF_FOREST = SIZE_OF_FOREST;
        this.COUNT_OF_THREADS = (int)Math.sqrt(SIZE_OF_FOREST);
        this.threads = new Thread[COUNT_OF_THREADS];
        forest = new int[SIZE_OF_FOREST][SIZE_OF_FOREST];
        for(int i = 0; i < SIZE_OF_FOREST; i++){
            for(int j = 0; j < SIZE_OF_FOREST; j++) {
                forest[i][j] = 0;
            }
        }

        int column = (int)(Math.random()*SIZE_OF_FOREST);
        int row = (int)(Math.random()*SIZE_OF_FOREST);
        System.out.println("Winnie the Pooh is in row: " + row + " column: " + column);
        forest[row][row] = 1;

        BEAR_FOUND = new AtomicBoolean(false);
        CURRENT_ROW = new AtomicInteger(0);
    }
    private void check_entire_forest(){
        for(int i = 0; i < COUNT_OF_THREADS; i++){
            threads[i] = new Bees();
            threads[i].start();
        }
        for(int i = 0; i < COUNT_OF_THREADS; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void checkRow(int row) {
        if(BEAR_FOUND.get()) { return; }
        System.out.println(Thread.currentThread().getName() + " number of bees in row: " + row);
        for(int i = 0; i < SIZE_OF_FOREST; i++){
            if(forest[row][i] == 1){
                System.out.println(Thread.currentThread().getName() + " Winnie the Pooh was found in row: " + row);
                BEAR_FOUND.set(true);
                break;
            }
        }
    }
    public static void main(String[] args) {
        main BeesFindingWinniePooh = new main(100);
        BeesFindingWinniePooh.check_entire_forest();
    }
}
