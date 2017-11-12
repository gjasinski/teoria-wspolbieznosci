package tw04.task2.naive;

public class Task2 {
    private static int producers = 2;
    private static int consumers = 2;
    private static int timeToLive = 10;
    private static int halfBufferSize = 100;

    public static void main(String[] args) {
        Buffer buffer = new Buffer(2 * halfBufferSize);
        Thread[] threads = new Thread[producers + consumers];
        for (int i = 0; i < producers; i++) {
            Producer p = new Producer(buffer, halfBufferSize, timeToLive);
            threads[i] = new Thread(p);
        }
        for (int i = 0; i < consumers; i++) {
            Consumer c = new Consumer(buffer, halfBufferSize);
            threads[i + producers] = new Thread(c);
        }

        for(int i = 0; i < producers + consumers; i++){
            threads[i].run();
        }
    }
}
