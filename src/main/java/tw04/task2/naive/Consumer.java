package tw04.task2.naive;

import tw04.task2.IBuffer;
import tw04.task2.TimeMeasurementRepository;


import java.math.BigDecimal;
import java.util.Random;

public class Consumer implements Runnable {
    private Random generator = new Random();
    private final IBuffer buffer;
    private final int maxLengthOfProducedString;
    private final TimeMeasurementRepository timeMeasurementRepository;

    public Consumer(IBuffer buffer, int maxLengthOfProducedString, TimeMeasurementRepository timeMeasurementRepository) {
        this.buffer = buffer;
        this.maxLengthOfProducedString = maxLengthOfProducedString;
        this.timeMeasurementRepository = timeMeasurementRepository;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int productSize = generator.nextInt(maxLengthOfProducedString - 1) + 1;
                long timestamp1 = System.nanoTime();
                String dataFromBuffer = buffer.get(productSize);
                long timestamp2 = System.nanoTime();
                this.timeMeasurementRepository.addMeasurementGet(productSize, BigDecimal.valueOf(timestamp2 - timestamp1));
                System.out.println("Consumer get size = : " + productSize + " data: " + dataFromBuffer+ " time " + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
