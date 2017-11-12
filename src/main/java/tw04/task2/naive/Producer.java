package tw04.task2.naive;

import java.util.Random;

public class Producer implements Runnable {
    private Random generator = new Random();
    private final Buffer buffer;
    private final int maxLengthOfProducedString;
    private int timeToLive;

    public Producer(Buffer buffer, int maxLengthOfProducedString, int timeToLive) {
        this.buffer = buffer;
        this.maxLengthOfProducedString = maxLengthOfProducedString;
        this.timeToLive = timeToLive;
    }


    @Override
    public void run() {
        System.out.println("start procuder");
        try {
            for (int i = 0; i < timeToLive; i++) {
                int productSize = generator.nextInt(maxLengthOfProducedString - 1) + 1;
                String product = generateProduct(productSize);
                buffer.put(product);
                System.out.println("Producer: size = " + productSize + " produced = " + product);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String generateProduct(int productSize) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < productSize; i++) {
            stringBuilder.append(getRandomChar());
        }
        return stringBuilder.toString();
    }

    private char getRandomChar() {
        return (char)(generator.nextInt(90) + 32);
    }
}
