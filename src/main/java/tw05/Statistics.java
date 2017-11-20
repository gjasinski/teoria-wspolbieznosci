package tw05;

import java.util.ArrayList;
import java.util.List;

class Statistics {
    private String title;
    List<Long> measurements;
    String fileInfo;

    Statistics(String title) {
        fileInfo =this.title = title;
        this.measurements = new ArrayList<>();
    }

    void addMeasurement(long measurement) {
        this.measurements.add(measurement);
    }

    private double calcMean() {
        long sum = this.measurements.stream().reduce(0L, (v, m) -> v + m);
        return sum / this.measurements.size();
    }

    private long calcStDeviation(double mean) {
        long sumToPowerTwo = 0;
        for (long m: measurements
             ) {
            sumToPowerTwo += Math.pow(m - mean, 2.0);
        }
        //this.measurements.stream().reduce(0L, (sum, value) -> (sum + (long) Math.pow(value - mean, 2.0)));
        return (long) (Math.sqrt(sumToPowerTwo / this.measurements.size()));
    }

    @Override
    public String toString() {
        double mean = calcMean();
        long stDeviation = calcStDeviation(mean);
        return String.format("%s: \nMean: %f\n standard deviation: %d\n", title, mean, stDeviation);
    }
}
