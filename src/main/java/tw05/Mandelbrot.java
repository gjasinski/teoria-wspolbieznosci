package tw05;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
    private final static StatisticsRepository statistcsRepository = new StatisticsRepository();
    private static final boolean VISIBILITY = false;
    private BufferedImage I;
    private final int MAX_ITER = 1000;
    private final double ZOOM = 150;
    public Mandelbrot(ExecutorService pool, Statistics statistics) {
        super("Mandelbrot Set");
        try {
            prepareJFrame();
            long timestamp1 = System.nanoTime();
            List<Future<int[]>> futures = new ArrayList<>();
            for (int y = 0; y < getHeight(); y++) {
                Callable<int[]> rowCalculator = new RowCalculator(y, getWidth(), ZOOM, MAX_ITER);
                Future<int[]> future = pool.submit(rowCalculator);
                futures.add(future);
            }
            for (Future f : futures) {
                while (!f.isDone()) ;

            }
            long timestamp2 = System.nanoTime();
            statistics.addMeasurement(timestamp2 -timestamp1);
            printMandelbrot(futures);
        } catch (Exception ex) {
        }
    }

    private void printMandelbrot(List<Future<int[]>> futures) throws InterruptedException, java.util.concurrent.ExecutionException {
        for (int y = 0; y < getHeight(); y++) {
            I.setRGB(0, y, getWidth(), 1, futures.get(y).get(), 0, 1);
        }
    }

    private void prepareJFrame() {
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        for(int i = 1; i < 256; i *= 2) {
            runMandelbrotNewFixedThreadPool(i);
            runCachedThreadPool(i);
            runWorkStealingPool(i);
        }
        runSingleThreadExecutor();
        statistcsRepository.printStatistcs();
    }

    private static void runMandelbrotNewFixedThreadPool(int threads) {
        Statistics statistics = new Statistics("newFixedThreadPool" + threads);
        statistcsRepository.addStatistics(statistics);
        for (int i = 0; i < 10; i++) {
            new Mandelbrot(Executors.newFixedThreadPool(threads), statistics).setVisible(VISIBILITY);
        }
    }

    private static void runCachedThreadPool(int threads){
        Statistics statistics = new Statistics("newCachedThreadPool" + threads);
        statistcsRepository.addStatistics(statistics);
        for (int i = 0; i < 10; i++) {
            new Mandelbrot(Executors.newCachedThreadPool(), statistics).setVisible(VISIBILITY);
        }
    }

    private static void runSingleThreadExecutor(){
        Statistics statistics = new Statistics("newSingleThreadExecutor");
        statistcsRepository.addStatistics(statistics);
        for (int i = 0; i < 10; i++) {
            new Mandelbrot(Executors.newSingleThreadExecutor(), statistics).setVisible(VISIBILITY);
        }
    }

    private static void runWorkStealingPool(int threads){
        Statistics statistics = new Statistics("newWorkStealingPool" + threads);
        statistcsRepository.addStatistics(statistics);
        for (int i = 0; i < 10; i++) {
            new Mandelbrot(Executors.newWorkStealingPool(threads), statistics).setVisible(VISIBILITY);
        }
    }
}