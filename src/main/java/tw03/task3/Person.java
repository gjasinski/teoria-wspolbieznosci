package tw03.task3;

import java.util.Random;

public class Person implements Runnable {
    private final Waiter waiter;
    private final int pairId;
    private Random generator = new Random();

    Person(Waiter waiter, int pairId) {
        this.waiter = waiter;
        this.pairId = pairId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                do_sth();
                boolean iGotTable = waiter.giveMeTable(this.pairId);
                if (iGotTable) {
                    eat();
                    waiter.releaseTable();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void do_sth() throws InterruptedException {
        Thread.sleep(Math.abs(generator.nextInt()) % 1000);
    }

    void eat() throws InterruptedException {
        System.out.println(String.format("Jem %d %s", pairId, this.toString()));
        Thread.sleep(Math.abs(generator.nextInt()) % 1000);
    }
}
