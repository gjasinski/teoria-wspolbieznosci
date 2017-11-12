package tw04.task2.naive;

import tw04.task2.IBuffer;
import tw04.task2.TimeMeasurementRepository;

import java.math.BigDecimal;
import java.util.Random;

public class Producer implements Runnable {
    private Random generator = new Random();
    private final IBuffer buffer;
    private final int maxLengthOfProducedString;
    private final TimeMeasurementRepository timeMeasurementRepository;
    private final int REPEATE_PRODUCING = 100;

    public Producer(IBuffer buffer, int maxLengthOfProducedString, TimeMeasurementRepository timeMeasurementRepository) {
        this.buffer = buffer;
        this.maxLengthOfProducedString = maxLengthOfProducedString;
        this.timeMeasurementRepository = timeMeasurementRepository;
    }


    @Override
    public void run() {
        try {
            for(int i = 0; i < REPEATE_PRODUCING; i++){
                int productSize = generator.nextInt(maxLengthOfProducedString - 1) + 1;
                String product = generateProduct(productSize);
                long timestamp1 = System.nanoTime();
                buffer.put(product);
                long timestamp2 = System.nanoTime();
                this.timeMeasurementRepository.addMeasurementPut(productSize, BigDecimal.valueOf(timestamp2 - timestamp1));
                System.out.println("Producer: size = " + productSize + " produced = " + product + " time " + System.currentTimeMillis());
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
