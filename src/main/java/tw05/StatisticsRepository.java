package tw05;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class StatisticsRepository {
    List<Statistics> statisticsList = new LinkedList<>();
    private static final String PATH = "/home/gjasinski/git/tw/src/main/java/tw05/";

    void addStatistics(Statistics statistics) {
        statisticsList.add(statistics);
    }

    void printStatistcs() {
        statisticsList.forEach(v -> System.out.println(v.toString()));
        try (FileWriter fw = new FileWriter(PATH + "statistcs.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            statisticsList.forEach(v -> out.println(v.toString()));
        } catch (IOException e) {
        }

    }
}
