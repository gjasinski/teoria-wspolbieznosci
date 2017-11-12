package tw04.task2.fair;

import tw04.task2.TimeMeasurementRepository;

import java.math.BigDecimal;
import java.util.Random;

public class Producer implements Runnable {
    private Random generator = new Random();
    private final Buffer buffer;
    private final int maxLengthOfProducedString;
    private final TimeMeasurementRepository timeMeasurementRepository;
    private final int REPEATE_PRODUCING = 100;
    private static int id;

    public Producer(Buffer buffer, int maxLengthOfProducedString, TimeMeasurementRepository timeMeasurementRepository, int id) {
        this.buffer = buffer;
        this.maxLengthOfProducedString = maxLengthOfProducedString;
        this.timeMeasurementRepository = timeMeasurementRepository;
        this.id = id;
    }


    @Override
    public void run() {
        try {
            for(int i = 0; i < REPEATE_PRODUCING; i++){
                int productSize = generator.nextInt(maxLengthOfProducedString - 1) + 1;
                String product = generateProduct(productSize);
                long timestamp1 = System.nanoTime();
                buffer.put(product, id);
                long timestamp2 = System.nanoTime();
                this.timeMeasurementRepository.addMeasurementPut(productSize, BigDecimal.valueOf(timestamp2 - timestamp1));
               // System.out.println("Producer: size = " + productSize + " produced = " + product + " time " + System.currentTimeMillis());
            }
            this.timeMeasurementRepository.printResults();
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
