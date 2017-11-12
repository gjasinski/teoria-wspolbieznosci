package tw04.task2.naive;

import java.util.Random;

public class Consumer implements Runnable {
    private Random generator = new Random();
    private final Buffer buffer;
    private final int maxLengthOfProducedString;

    public Consumer(Buffer buffer, int maxLengthOfProducedString) {
        this.buffer = buffer;
        this.maxLengthOfProducedString = maxLengthOfProducedString;
    }

    @Override
    public void run() {
        System.out.println("start consumer");
        try {
            while (true) {
                int productSize = generator.nextInt(maxLengthOfProducedString);
                System.out.println("chce wziasc + " + productSize);
                String dataFromBuffer = buffer.get(productSize);
                System.out.println("Consumer get size = : " + productSize + " data: " + dataFromBuffer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
