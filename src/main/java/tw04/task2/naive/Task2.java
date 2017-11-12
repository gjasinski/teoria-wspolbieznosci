package tw04.task2.naive;

import tw04.task2.TimeMeasurementRepository;

public class Task2 {
    private static int producers;
    private static int consumers = producers = 1000;
    private static int halfBufferSize = 100000;

    public static void main(String[] args) {
        Buffer buffer = new Buffer(2 * halfBufferSize);
        TimeMeasurementRepository tmr = new TimeMeasurementRepository(halfBufferSize);
        Thread[] threads = new Thread[producers + consumers];
        for (int i = 0; i < producers; i++) {
            Producer p = new Producer(buffer, halfBufferSize, tmr);
            threads[i] = new Thread(p);
            threads[i].start();

        }
        for (int i = 0; i < consumers; i++) {
            Consumer c = new Consumer(buffer, halfBufferSize, tmr);
            threads[i + producers] = new Thread(c);
            threads[i + producers].start();
        }
    }
}
