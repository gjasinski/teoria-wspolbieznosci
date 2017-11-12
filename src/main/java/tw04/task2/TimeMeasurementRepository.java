package tw04.task2;

import java.io.PrintWriter;
import java.math.BigDecimal;

public class TimeMeasurementRepository {
    private final String name;
    private final BigDecimal[] resultsPut;
    private final BigDecimal[] resultsGet;
    private final int[] numberOfResultsPut;
    private final int[] numberOfResultsGet;

    public TimeMeasurementRepository(int capacity, String name) {
        this.resultsPut = new BigDecimal[capacity];
        this.resultsGet = new BigDecimal[capacity];
        this.numberOfResultsPut = new int[capacity];
        this.numberOfResultsGet = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            this.resultsPut[i] = new BigDecimal(0);
            this.resultsGet[i] = new BigDecimal(0);
        }
        this.name = name;
    }

    public synchronized void addMeasurementPut(int size, BigDecimal measurement) {
        this.resultsPut[size] = this.resultsPut[size].add(measurement);
        this.numberOfResultsPut[size]++;
        if (this.resultsPut[size].compareTo(measurement) < 0) throw new IllegalStateException("Overflow");
    }

    public synchronized void addMeasurementGet(int size, BigDecimal measurement) {
        this.resultsGet[size] = this.resultsGet[size].add(measurement);
        this.numberOfResultsGet[size]++;
        if (this.resultsGet[size].compareTo(measurement) < 0) throw new IllegalStateException("Overflow");
    }

    public synchronized void printResults(){
        try {
            PrintWriter writer = new PrintWriter(String.format("put%s.txt", this.name), "UTF-8");
            for (int i = 0; i < resultsPut.length; i++) {
                if (numberOfResultsPut[i] != 0) {
                    BigDecimal divisor = new BigDecimal(numberOfResultsPut[i]);
                    writer.println(resultsPut[i].divideToIntegralValue(divisor));
                }
                else {
                    writer.println(0);
                }
            }
            writer.close();
            writer = new PrintWriter(String.format("get%s.txt", this.name), "UTF-8");
            for (int i = 0; i < resultsGet.length; i++) {
                if (numberOfResultsGet[i] != 0) {
                    BigDecimal divisor = new BigDecimal(numberOfResultsGet[i]);
                    writer.println(resultsGet[i].divideToIntegralValue(divisor));
                }
                else {
                    writer.println(0);
                }
            }
            writer.close();
            System.out.println("Wypisalem wyniki");
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
