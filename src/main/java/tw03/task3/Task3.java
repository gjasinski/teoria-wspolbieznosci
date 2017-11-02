package tw03.task3;


public class Task3 {
    private static Waiter waiter;

    public static void main(String[] args) {
        waiter = new Waiter();
        for (int i = 0; i < 10; i++) {
            Person task = new Person(waiter, i / 2);
            Thread t = new Thread(task);
            t.start();
        }
    }


}
