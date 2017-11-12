package tw04.task2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class TimeMeasurementRepository {
    String name;
    BigDecimal[] resultsPut;
    BigDecimal[] resultsGet;
    int[] numberOfResultsPut;
    int[] numberOfResultsGet;

    public TimeMeasurementRepository(int capacity) {
        this.resultsPut = new BigDecimal[capacity];
        this.resultsGet = new BigDecimal[capacity];
        this.numberOfResultsPut = new int[capacity];
        this.numberOfResultsGet = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            this.resultsPut[i] = new BigDecimal(0);
            this.resultsGet[i] = new BigDecimal(0);
        }
    }

    public void addMeasurementPut(int size, BigDecimal measurement) {
        this.resultsPut[size] = this.resultsPut[size].add(measurement);
        this.numberOfResultsPut[size]++;
        if (this.resultsPut[size].compareTo(measurement) < 0) throw new IllegalStateException("Overflow");
    }

    public void addMeasurementGet(int size, BigDecimal measurement) {
        this.resultsGet[size] = this.resultsGet[size].add(measurement);
        this.numberOfResultsGet[size]++;
        if (this.resultsGet[size].compareTo(measurement) < 0) throw new IllegalStateException("Overflow");
    }

    public void printResults(){
        try {
            PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
            writer.println("put");
            for (int i = 0; i < resultsPut.length; i++) {
                if (numberOfResultsGet[i] != 0) {
                    BigDecimal divisor = new BigDecimal(numberOfResultsPut[i]);
                    writer.println(resultsPut[i].divideToIntegralValue(divisor));
                }
            }

            writer.println("get");
            for (int i = 0; i < resultsPut.length; i++) {
                if (numberOfResultsGet[i] != 0) {
                    BigDecimal divisor = new BigDecimal(numberOfResultsGet[i]);
                    writer.println(resultsGet[i].divideToIntegralValue(divisor));
                }
            }
            writer.close();
            System.out.println("Wypisalem wyniki");
        }catch (Exception ex){}
    }
}
